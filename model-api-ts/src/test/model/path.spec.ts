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

import { deepStrictEqual, ok, strictEqual } from "node:assert/strict";

import { ModelPath } from "../../main/model/index.js";

describe("ModelPath", () => {
	const groupAPath: ModelPath = [{ elementName: "GroupA" }];
	const field1InGroupAPath: ModelPath = [{ elementName: "GroupA" }, { elementName: "Field1" }];
	const field2InGroupAPath: ModelPath = [{ elementName: "GroupA" }, { elementName: "Field2" }];

	describe("equal", () => {
		it("returns true, if both paths are equal", () => {
			const path1: ModelPath = [{ elementName: "GroupA" }, { elementName: "Field1" }];
			const path2: ModelPath = [{ elementName: "GroupA" }, { elementName: "Field1" }];
			ok(ModelPath.equal(path1, path2));
		});

		it("returns true, if both paths empty", () => {
			const path1: ModelPath = [];
			const path2: ModelPath = [];
			ok(ModelPath.equal(path1, path2));
		});

		it("returns false, if both paths are not equal", () => {
			const path1: ModelPath = [{ elementName: "GroupA" }, { elementName: "Field1" }];
			const path2: ModelPath = [{ elementName: "GroupA" }, { elementName: "Field2" }];
			strictEqual(ModelPath.equal(path1, path2), false);
		});
	});

	describe("fromString", () => {
		it("returns the path object for a single element path string", () => {
			deepStrictEqual(ModelPath.fromString("GroupA"), groupAPath);
			deepStrictEqual(ModelPath.fromString("/GroupA"), groupAPath);
		});

		it("returns the path object for a multi element path string", () => {
			deepStrictEqual(ModelPath.fromString("GroupA/Field1"), field1InGroupAPath);
			deepStrictEqual(ModelPath.fromString("/GroupA/Field1"), field1InGroupAPath);
		});

		it("returns an empty path for an empty string", () => {
			deepStrictEqual(ModelPath.fromString(""), []);
		});
	});

	describe("toString", () => {
		it("returns the path string for a single element path object", () => {
			deepStrictEqual(ModelPath.toString(groupAPath), "/GroupA");
		});

		it("returns the path string for a multi element path object", () => {
			deepStrictEqual(ModelPath.toString(field1InGroupAPath), "/GroupA/Field1");
		});

		it('returns "/" for an empty path object', () => {
			deepStrictEqual(ModelPath.toString([]), "/");
		});
	});

	describe("parentPath", () => {
		it("returns the path to the direct parent of the model element", () => {
			deepStrictEqual(ModelPath.parentPath(field1InGroupAPath), groupAPath);
		});

		it("returns an empty path for an empty path", () => {
			deepStrictEqual(ModelPath.parentPath([]), []);
		});

		it("returns an empty path for a single element path", () => {
			deepStrictEqual(ModelPath.parentPath(groupAPath), []);
		});
	});

	describe("contains", () => {
		it("returns true, if the first element is contained in the second element", () => {
			ok(ModelPath.contains(field1InGroupAPath, groupAPath));
		});

		it("returns true, if both paths are empty", () => {
			ok(ModelPath.contains([], []));
		});

		it("returns true, if both element paths are equal", () => {
			ok(ModelPath.contains(groupAPath, groupAPath));
		});

		it("returns false, if the first element is not contained in the second element", () => {
			strictEqual(ModelPath.contains(groupAPath, field1InGroupAPath), false);
			strictEqual(ModelPath.contains(field2InGroupAPath, field1InGroupAPath), false);

			strictEqual(
				ModelPath.contains(
					[{ elementName: "GroupA" }, { elementName: "Field1" }, { elementName: "Prop1" }],
					field2InGroupAPath
				),
				false
			);
		});
	});
});
