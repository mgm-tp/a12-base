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
package com.mgmtp.a12.model.ident;

import org.testng.Assert;
import org.testng.annotations.Test;

public class ModelIdentifierTest {

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testCreation() {
		new ModelIdentifier("");
	}

	@Test
	public void testEquals() throws Exception {
		final String testId = "abc";
		final String testId2 = "abcd";

		final ModelIdentifier id1 = new ModelIdentifier(testId);
		final ModelIdentifier id2 = new ModelIdentifier(testId);
		final ModelIdentifier id3 = new ModelIdentifier(testId2);

		Assert.assertTrue(id1.equals(id2));
		Assert.assertFalse(id1.equals(id3));
	}

	@Test
	public void testGetIdentifier() throws Exception {
		final String testId = "abc";
		final ModelIdentifier id1 = new ModelIdentifier(testId);
		Assert.assertEquals(id1.getIdentifier(), testId);
	}

	@Test
	public void testSetIdentifier() throws Exception {
		final String testId = "abc";
		final String testId2 = "abcd";
		final ModelIdentifier id1 = new ModelIdentifier(testId);
		id1.setIdentifier(testId2);
		Assert.assertEquals(id1.getIdentifier(), testId2);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	public void testSetIdentifierWithEmptyValue() throws Exception {
		final String testId = "abc";
		final ModelIdentifier id1 = new ModelIdentifier(testId);
		id1.setIdentifier("");
	}

	@Test
	public void testGetPersistedIdentifier() throws Exception {
		final String testId = "abc";
		final String testId2 = "abcd";
		final ModelIdentifier id1 = new ModelIdentifier(testId);
		id1.setIdentifier(testId2);
		Assert.assertNotEquals(id1.getPersistedIdentifier(), testId2);
		Assert.assertEquals(id1.getPersistedIdentifier(), testId);
	}

	@Test
	public void testPersist() throws Exception {
		final String testId = "abc";
		final String testId2 = "abcd";
		final ModelIdentifier id1 = new ModelIdentifier(testId);
		id1.setIdentifier(testId2);
		id1.persist();
		Assert.assertEquals(id1.getPersistedIdentifier(), testId2);
	}

}
