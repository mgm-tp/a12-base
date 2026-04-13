/*
 * SPDX-License-Identifier: EUPL-1.2 OR LicenseRef-commercial
 *
 * Copyright (c) 2012-2026 mgm technology partners GmbH
 *
 * Dual License
 * ------------
 * This source file is part of the mgm A12 Platform and available under
 * a choice of two different licenses:
 *
 * 1. Open-Source License – EUPL v1.2
 *    You may redistribute and/or modify this file under the terms of the
 *    European Union Public License, version 1.2 - see https://eupl.eu/.
 *
 * 2. Commercial License
 *    Alternatively, you may obtain a commercial license from
 *    mgm technology partners GmbH, that permits use of this software
 *    under different terms (including support and maintenance services).
 *
 *    Please contact a12-license@mgm-tp.com for more information.
 *
 * You must select and comply with exactly one of the above license options.
 *
 * Warranty Disclaimer (applies to either option)
 * ----------------------------------------------
 * THIS SOFTWARE IS PROVIDED “AS IS” AND WITHOUT WARRANTY OF ANY KIND,
 * WHETHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NON-INFRINGEMENT, EXCEPT WHERE SUCH DISCLAIMERS ARE HELD TO BE
 * LEGALLY INVALID. SEE THE RESPECTIVE LICENSE TEXT FOR DETAILS.
 */
package com.mgmtp.a12.model.repository;

import com.mgmtp.a12.model.Model;
import com.mgmtp.a12.model.serialization.ModelSerializationException;
import com.mgmtp.a12.model.serialization.ModelTypeIdentifier;
import com.mgmtp.a12.model.serialization.SerializerFactory;
import com.mgmtp.a12.model.serialization.StreamSerializer;
import com.mgmtp.a12.model.serialization.StringSerializer;
import com.mgmtp.a12.model.serialization.XmlSerializer;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultXmlFsModelRepositoryTest {

	private static final String FIRST_FAKE = "DocModel2";
	private static final String SECOND_FAKE = "DocModel22";
	private static final String DOCUMENT_MODEL = "DocModel";
	private static final String FORM_MODEL = "SampleForm";
	private static final String OVERVIEW_MODEL = "SampleOverview";
	private static final String EXTRA_DOCUMENT_MODEL = "DocModel3.xml";
	private static final String FILE_EXTENSION = "xml";

	private static final List<String> SAMPLE_MODELS = new ArrayList<String>() {
		{
			add(DOCUMENT_MODEL);
			add(FORM_MODEL);
			add(OVERVIEW_MODEL);
			add(FIRST_FAKE);
			add(SECOND_FAKE);
		}
	};

	private Path tempDir;
	private FsModelRepository fsModelRepository;

	@BeforeMethod()
	public void setUp() throws Exception {
		tempDir = Files.createTempDirectory("XmlFsModelRepositoryTest" + new Date().getTime());
		FsModelRepositoryTestUtil.createInitialRepository(SAMPLE_MODELS, FILE_EXTENSION, tempDir);
		fsModelRepository = new FsModelRepository(
			tempDir,
			FILE_EXTENSION,
			new TestSerializerFactory(),
			new TestModelTypeIdentifier());
	}

	@AfterMethod
	public void tearDown() throws Exception {
		FsModelRepositoryTestUtil.emptyDirectory(tempDir);
	}

	@Test
	public void testCreate() throws Exception {
		final String testModelName = "my-test-model";
		final InputStream inputStream = getClass().getResourceAsStream("DocModel3.xml");
		if (inputStream != null) {
			final TestModel testModel = new TestModel(testModelName);
			fsModelRepository.create(testModel);
			Assert.assertTrue(
				Files.isRegularFile(
					tempDir.resolve(testModelName + "." + FILE_EXTENSION)));
			inputStream.close();
		}
	}

	@Test
	public void testCreateWithExistingFilesAndNameCollision() {
		final TestModel testModel = new TestModelXmlStreamSerializer().deserialize(
			getClass().getResourceAsStream(EXTRA_DOCUMENT_MODEL));
		fsModelRepository.create(testModel);

		Assert.assertTrue(Files.exists(tempDir.resolve(testModel.getId() + "3." + FILE_EXTENSION)));
	}

	@Test(
		expectedExceptions = FaultException.class,
		expectedExceptionsMessageRegExp = "Cannot create file for model \'.+\'\\. The model file already "
			+ "exists\\.")
	public void testCreateFailing() throws Exception {
		final TestModel testModel = new TestModelXmlStreamSerializer().deserialize(
			getClass().getResourceAsStream(DOCUMENT_MODEL + "." + FILE_EXTENSION));
		fsModelRepository.create(testModel);
	}

	@Test
	public void testFind() throws Exception {
		final Model findResult = fsModelRepository.findById(FORM_MODEL);
		Assert.assertTrue(findResult instanceof TestModel);
		Assert.assertEquals(findResult.getHeader().getId(), FORM_MODEL);
	}

	@Test(
		expectedExceptions = ResourceAccessException.class,
		expectedExceptionsMessageRegExp = "SampleForms: A model named \'SampleForms\' does not exist\\.")
	public void testFindFailing() throws Exception {
		fsModelRepository.findById("SampleForms");
	}

	@Test
	public void testFindAll() throws Exception {
		final Collection<Model> results = fsModelRepository.findAll();
		Assert.assertFalse(results.isEmpty());
		for (final Model model : results) {
			Assert.assertTrue(SAMPLE_MODELS.contains(model.getHeader().getId()));
		}
		Assert.assertEquals(results.size(), SAMPLE_MODELS.size() - 2); // exclude two files that do not contain models
	}

	@Test
	public void testSave() throws Exception {
		final TestModel testModel = (TestModel) fsModelRepository.findById(FORM_MODEL);
		final String testContent = "abcdefg";
		testModel.setContentText(testContent);

		fsModelRepository.save(testModel);

		try (final InputStream inputStream =
			Files.newInputStream(
				tempDir.resolve(FORM_MODEL + "." + FILE_EXTENSION),
				StandardOpenOption.READ)) {

			final TestModel sameModel = new TestModelXmlStreamSerializer().deserialize(inputStream);

			Assert.assertEquals(sameModel.getContentText(), testContent);

		} catch (IllegalArgumentException | SecurityException | UnsupportedOperationException exc) {
			Assert.fail();
		}
	}

	@Test
	public void testDelete() throws Exception {
		final Model findResult = fsModelRepository.findById(OVERVIEW_MODEL);
		fsModelRepository.delete(findResult.getHeader().getId());
		Assert.assertFalse(Files.exists(
			tempDir.resolve(OVERVIEW_MODEL + "." + FILE_EXTENSION)));
	}

	@Test(
		expectedExceptions = FaultException.class,
		expectedExceptionsMessageRegExp = "Error trying to delete model \'.+\'\\. The model file does not "
			+ "exist\\.")
	public void testDeleteFailing() throws Exception {
		fsModelRepository.delete("abc");
	}

	private static class TestModelTypeIdentifier implements ModelTypeIdentifier {

		@Override
		public Class<? extends Model> identifyModelType(final String text) throws ModelSerializationException {
			if (text.contains("<TestModel name=\"")) {
				return TestModel.class;
			} else {
				throw new ModelSerializationException("Cannot determine model type from model representation: " +
					text);
			}
		}
	}

	private static class TestSerializerFactory implements SerializerFactory<XmlSerializer> {

		@Override
		public XmlSerializer create(final Class<? extends Model> clazz) {
			if (TestModel.class.isAssignableFrom(clazz)) {
				return new TestModelXmlSerializer();
			} else {
				throw new UnsupportedOperationException("Critical test failure.");
			}
		}
	}

	private static class TestModelXmlSerializer implements XmlSerializer<TestModel> {

		private static final String PREFIX = "<TestModel name=\"";
		private static final String MIDDLE_PART = "\" content=\"";
		private static final String SUFFIX = "\"/>";

		private static final Pattern NAME = Pattern.compile(".*name=\"(.*)\".*");
		private static final Pattern CONTENT = Pattern.compile(".*content=\"(.*)\".*");

		@Override
		public String serialize(final TestModel model) throws ModelSerializationException {
			final StringBuilder resultBuilder = new StringBuilder(PREFIX).append(model.getId());
			if (StringUtils.isNotBlank(model.getContentText())) {
				resultBuilder.append(MIDDLE_PART).append(model.getContentText());
			}
			return resultBuilder.append(SUFFIX).toString();
		}

		@Override
		public TestModel deserialize(final String text) throws ModelSerializationException {
			final String content = extractContent(text);
			final String name = extractName(text);
			return new TestModel(name, content);
		}

		private String extractName(final String text) {
			final Matcher nameMatcher = NAME.matcher(text);
			if (nameMatcher.matches()) {
				return nameMatcher.group(1);
			} else {
				return null;
			}
		}

		private String extractContent(final String text) {
			final Matcher contentMatcher = CONTENT.matcher(text);
			if (contentMatcher.matches()) {
				return contentMatcher.group(1);
			} else {
				return null;
			}
		}
	}

	private static class TestModelXmlStreamSerializer extends StreamSerializer<TestModel> {

		@Override
		public StringSerializer<TestModel> getStringSerializer() {
			return new TestModelXmlSerializer();
		}
	}
}
