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
 * 1. Open-Source License - EUPL v1.2
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
 * THIS SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTY OF ANY KIND,
 * WHETHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NON-INFRINGEMENT, EXCEPT WHERE SUCH DISCLAIMERS ARE HELD TO BE
 * LEGALLY INVALID. SEE THE RESPECTIVE LICENSE TEXT FOR DETAILS.
 */
package com.mgmtp.a12.model.repository;

import com.mgmtp.a12.model.Model;
import com.mgmtp.a12.model.serialization.JsonSerializer;
import com.mgmtp.a12.model.serialization.ModelSerializationException;
import com.mgmtp.a12.model.serialization.ModelTypeIdentifier;
import com.mgmtp.a12.model.serialization.SerializerFactory;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

public class DefaultJsonFsModelRepositoryTest {

	private static final String FILE_EXTENSION = "json";

	private Path tempDir;
	private FsModelRepository fsModelRepository;

	@BeforeMethod()
	public void setUp() throws Exception {
		tempDir = Files.createTempDirectory("JsonFsModelRepositoryTest" + new Date().getTime());
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
		final TestModel testModel = new TestModel("test-model-1");
		fsModelRepository.create(testModel);
		Assert.assertTrue(
			Files.isRegularFile(tempDir.resolve(testModel.getId() + "." + FILE_EXTENSION)));
	}

	private static class TestModelTypeIdentifier implements ModelTypeIdentifier {

		@Override
		public Class<? extends Model> identifyModelType(final String text) throws ModelSerializationException {
			if (text.contains("{ \"name\": \"")) {
				return TestModel.class;
			} else {
				throw new ModelSerializationException("Cannot determine model type from model representation: " +
					text);
			}
		}
	}

	private static class TestSerializerFactory implements SerializerFactory<JsonSerializer> {

		@Override
		public JsonSerializer create(final Class<? extends Model> clazz) {
			if (TestModel.class.isAssignableFrom(clazz)) {
				return new TestModelJsonSerializer();
			} else {
				throw new UnsupportedOperationException("Critical test failure.");
			}
		}
	}

	private static class TestModelJsonSerializer implements JsonSerializer<TestModel> {

		private static final String PREFIX = "{ \"name\": \"";
		private static final String SUFFIX = "\"}";

		@Override
		public String serialize(final TestModel model) throws ModelSerializationException {
			return PREFIX + model.getId() + SUFFIX;
		}

		@Override
		public TestModel deserialize(final String text) throws ModelSerializationException {
			return new TestModel(extractNameFromJson(text));
		}

		private String extractNameFromJson(final String text) {
			return text.substring(PREFIX.length(), text.length() - SUFFIX.length());
		}
	}
}
