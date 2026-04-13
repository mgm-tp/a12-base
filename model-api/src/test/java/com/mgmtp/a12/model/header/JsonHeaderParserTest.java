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

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Locale;

public class JsonHeaderParserTest {

	private static final String TEST_MODEL = "ParserTestModel.json";
	private static final String INVALID_TEST_MODEL = "InvalidParserTestModel.json";

	@Test
	public void testValidJson() throws IOException, HeaderParseException {
		final String inputModel = TestInputReader.readString(getClass().getResourceAsStream(TEST_MODEL));

		final Header parsedHeader = new DefaultHeaderParser().parseJson(inputModel);

		Assert.assertEquals(parsedHeader.getId(), "sample-app-model");
		Assert.assertEquals(parsedHeader.getModelType(), "application");
		Assert.assertEquals(parsedHeader.getModelVersion(), "1.0.0");
		Assert.assertEquals(parsedHeader.getLocales().size(), 2);
		Assert.assertTrue(parsedHeader.getLocales().contains(Locale.ENGLISH));
		Assert.assertEquals(parsedHeader.getLabels().size(), 2);
		Assert.assertEquals(parsedHeader.getDescription(), "Test model description");
		Assert.assertEquals(parsedHeader.getAnnotations().size(), 2);
		Assert.assertEquals(parsedHeader.getModelReferences().size(), 3);
	}

	@Test(expectedExceptions = HeaderParseException.class,
		expectedExceptionsMessageRegExp = "Could not find header property in model json")
	public void testInvalidJson() throws HeaderParseException, IOException {
		final String inputModel = TestInputReader.readString(getClass().getResourceAsStream(INVALID_TEST_MODEL));

		new DefaultHeaderParser().parseJson(inputModel);
		Assert.fail();
	}

}
