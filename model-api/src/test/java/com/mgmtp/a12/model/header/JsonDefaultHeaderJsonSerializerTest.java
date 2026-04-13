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
package com.mgmtp.a12.model.header;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

public class JsonDefaultHeaderJsonSerializerTest {

	private static final String EXPECTED_OUTPUT_MODEL = "SerializerTestModel.json";
	private static final String EXPECTED_OUTPUT_MODEL2 = "SerializerTestModelNoCollections.json";

	private String expectedOutput;
	private String expectedOutputEmptyCollections;
	private HeaderImpl header;

	@BeforeClass
	public void setup() throws IOException {
		expectedOutput = TestInputReader.readString(getClass().getResourceAsStream(EXPECTED_OUTPUT_MODEL));
		expectedOutputEmptyCollections =
			TestInputReader.readString(getClass().getResourceAsStream(EXPECTED_OUTPUT_MODEL2));
	}

	@BeforeMethod
	public void prepareTestInstance() {
		header = new HeaderImpl();
		header.setId("Test-Model-Id");
		header.setModelType("Test-Model-Type");
		header.setModelVersion("1.2.3");
	}

	@Test
	public void test() throws JSONException {
		header.setAnnotations(
			Arrays.asList(
				new AnnotationImpl(
					"annotation-name",
					"annotation-value"
				),
				new AnnotationImpl(
					"valueless-annotation-name",
					null
				)
			)
		);

		header.setLocales(
			Collections.singletonList(
				Locale.GERMANY
			)
		);

		header.setLabels(
			Arrays.asList(
				new LabelImpl(Locale.GERMANY, "Mein Label"),
				new LabelImpl(Locale.US, "")
			)
		);

		header.setDescription("Test model description");

		header.setModelReferences(
			Arrays.asList(
				new ModelReferenceImpl(
					"test-purpose",
					"test-type",
					"test-alias",
					"test-reference"
				),
				new ModelReferenceImpl(
					null,
					"no-purpose-no-alias-test-type",
					null,
					"no-purpose-no-alias-test-reference"
				)
			)
		);

		final String actualOutput = new DefaultHeaderJsonSerializer().toJsonString(header);
		JSONAssert.assertEquals(expectedOutput, actualOutput, true);
	}

	@Test
	public void testWithEmptyCollections() throws JSONException {
		header.setLabels(Collections.emptyList());
		header.setLocales(Collections.emptyList());
		header.setModelReferences(null);
		header.setAnnotations(null);
		final String actualOutput = new DefaultHeaderJsonSerializer().toJsonString(header);
		JSONAssert.assertEquals(expectedOutputEmptyCollections, actualOutput, true);
	}

}

