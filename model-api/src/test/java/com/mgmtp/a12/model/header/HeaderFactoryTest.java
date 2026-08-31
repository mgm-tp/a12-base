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

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.Locale;

import static org.testng.Assert.assertEquals;

public class HeaderFactoryTest {

	private static final String ALIAS = "dm";
	private static final String ANNOTATION = "test-annotion";
	private static final String ANNOTATION_VALUE = "test-value";
	private static final String ID = "test";
	private static final String LABEL = "test-label";
	private static final String DESCRIPTION = "test-description";
	private static final String MODEL_TYPE = "document";
	private static final String MODEL_VERSION = "22.6.0";
	private static final String PURPOSE = "test-purpose";
	private static final String REFERENCE = "picus.json";

	@Test
	void testMinimalHeader() {
		final Header testHeader = preconfigureBuilder().build();
		Assert.assertNotNull(testHeader);
		assertEquals(testHeader.getId(), ID);
		assertEquals(testHeader.getModelType(), MODEL_TYPE);
		assertEquals(testHeader.getModelVersion(), MODEL_VERSION);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	void testEnsureMinimalHeader() {
		new HeaderFactory.Builder().build();
		Assert.fail();
	}

	@Test
	void testFullHeaderWithArrays() {
		final Header testHeader =
			preconfigureBuilder()
				.withAnnotations(
					HeaderFactory.createAnnotation(ANNOTATION, ANNOTATION_VALUE)
				)
				.withLocales(Locale.ENGLISH)
				.withLabels(
					HeaderFactory.createLabel(Locale.ENGLISH, LABEL)
				)
				.withDescription(DESCRIPTION)
				.withModelReferences(HeaderFactory.createModelReference(
					PURPOSE,
					MODEL_TYPE,
					ALIAS,
					REFERENCE
				))
				.build();

		assertCorrectHeader(testHeader);
	}

	@Test
	void testFullHeaderWithLists() {
		final Header testHeader =
			preconfigureBuilder()
				.withAnnotations(
					Collections.singletonList(HeaderFactory.createAnnotation(ANNOTATION, ANNOTATION_VALUE))
				)
				.withLocales(Collections.singletonList(Locale.ENGLISH))
				.withLabels(
					Collections.singletonList(HeaderFactory.createLabel(Locale.ENGLISH, LABEL))
				)
				.withDescription(DESCRIPTION)
				.withModelReferences(
					Collections.singletonList(
						HeaderFactory.createModelReference(
							PURPOSE,
							MODEL_TYPE,
							ALIAS,
							REFERENCE
						)
					)
				)
				.build();

		assertCorrectHeader(testHeader);
	}

	private HeaderFactory.Builder preconfigureBuilder() {
		return new HeaderFactory.Builder()
			.withId(ID)
			.withModelType(MODEL_TYPE)
			.withModelVersion(MODEL_VERSION);
	}

	private void assertCorrectHeader(final Header header) {
		Assert.assertNotNull(header);

		assertEquals(header.getId(), ID);
		assertEquals(header.getModelType(), MODEL_TYPE);
		assertEquals(header.getModelVersion(), MODEL_VERSION);
		assertEquals(header.getLocales().getFirst(), Locale.ENGLISH);
		assertEquals(header.getLabels().getFirst().getText(), LABEL);
		assertEquals(header.getDescription(), DESCRIPTION);
		assertEquals(header.getAnnotations().getFirst().getName(), ANNOTATION);
		assertEquals(header.getAnnotations().getFirst().getValue(), ANNOTATION_VALUE);
		assertEquals(header.getModelReferences().getFirst(), createTestModelReference());
	}

	private ModelReference createTestModelReference() {
		final ModelReferenceImpl modelRef = new ModelReferenceImpl();
		modelRef.setAlias(ALIAS);
		modelRef.setModelType(MODEL_TYPE);
		modelRef.setPurpose(PURPOSE);
		modelRef.setReference(REFERENCE);
		return modelRef;
	}
}
