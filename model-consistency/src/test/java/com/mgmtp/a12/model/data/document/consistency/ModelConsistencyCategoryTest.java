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
package com.mgmtp.a12.model.data.document.consistency;

import com.mgmtp.a12.model.consistency.rules.ModelConsistencyCategory;

import org.testng.annotations.Test;

import java.util.Locale;

import static org.testng.Assert.assertEquals;

public class ModelConsistencyCategoryTest {

	@Test
	public void checkGetLocalizedMessage() throws Exception {
		String keyValue = ModelConsistencyCategory.MODEL_CHECKER_NOT_FOUND.getKeyValue();
		String germanMessage =
			ModelConsistencyCategory.MODEL_CHECKER_NOT_FOUND.getLocalizedMessage(Locale.GERMANY, keyValue);
		assertEquals(
			germanMessage,
			"Keine passende ModelChecker Implementierung für das zu überprüfende Modell [Name: %s] gefunden."
		);

		String englishMessage =
			ModelConsistencyCategory.MODEL_CHECKER_NOT_FOUND.getLocalizedMessage(Locale.ENGLISH, keyValue);
		assertEquals(
			englishMessage,
			"No suitable model checker implementation found for the given model [name: %s]."
		);
	}
}
