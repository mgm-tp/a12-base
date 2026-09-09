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

import { ok } from "node:assert/strict";

import type { Header } from "../../main/header/index.js";
import { isModelInstance } from "../../main/model/index.js";

describe("isModelInstance", () => {
	const header: Header = {
		id: "product-overview",
		modelType: "overview",
		modelVersion: "26.2.0",
		locales: [
			{
				code: "en"
			},
			{
				code: "de"
			}
		],
		labels: [
			{
				locale: "en",
				text: "List of all products"
			},
			{
				locale: "de",
				text: "Liste aller Produkte"
			}
		],
		description: "This overview contains all products of the company.",
		modelReferences: [
			{
				purpose: "document-model-for-overview",
				modelType: "document",
				alias: "DM",
				reference: "picus.xml"
			}
		],
		annotations: [
			{
				name: "sample-annotation",
				value: "sample annotation value"
			}
		]
	};

	const content = {
		element: {
			name: "Test"
		}
	};

	describe("given a model json containing model header information in a header property", () => {
		describe("and the header information is complete", () => {
			it("returns true", () => {
				ok(isModelInstance({ header, content }));
			});
		});

		describe("and the header information is incomplete", () => {
			const incompleteHeader: Partial<Header> = {
				modelType: "overview",
				modelVersion: "26.2.0"
			};

			it("returns true", () => {
				ok(isModelInstance({ header: incompleteHeader, content }));
			});
		});
	});

	describe("given a model json containing no header property", () => {
		it("returns false", () => {
			ok(!isModelInstance({ content }));
		});
	});

	describe("given a model json containing no content property", () => {
		it("returns false", () => {
			ok(!isModelInstance({ header }));
		});
	});
});
