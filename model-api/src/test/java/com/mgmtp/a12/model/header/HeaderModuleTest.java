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
package com.mgmtp.a12.model.header;

import com.mgmtp.a12.model.header.testModel.TestModel;
import com.mgmtp.a12.model.serialization.A12DefaultJsonPrettyPrinter;
import org.testng.annotations.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import java.io.IOException;

import static com.mgmtp.a12.model.header.HeaderModule.HEADER_MODULE;
import static org.testng.Assert.assertEquals;

public class HeaderModuleTest {

	private static final String TEST_MODEL = "DeserializerSerializerModel.json";

	@Test
	public void testFullCircle() throws IOException {
		final String inputModel = TestInputReader.readString(getClass().getResourceAsStream(TEST_MODEL));
		final ObjectMapper objectMapper = JsonMapper.builder()
			.defaultPrettyPrinter(new A12DefaultJsonPrettyPrinter())
			.addModule(HEADER_MODULE)
			.build();

		final TestModel model = objectMapper.readValue(inputModel, TestModel.class);
		final String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(model);

		assertEquals(json, inputModel);
	}
}
