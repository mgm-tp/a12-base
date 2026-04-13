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

import { join } from "node:path";
import { it } from "node:test";

import { Project } from "ts-morph";

import type { Recipe } from "@com.mgmtp.a12.devtools/codemod";

const TESTDATA_DIR = join(import.meta.dirname, "..", "..", "src", "testData");

/**
 * Tests a codemod recipe against a test project defined by the given tsconfig file.
 *
 * Assumes that the test project is located in the `testData` directory.
 *
 * Changed files are checked against snapshots.
 */
export function testRecipe(recipe: Recipe, tsConfigPath: string): void {
	const project = new Project({
		tsConfigFilePath: join(TESTDATA_DIR, tsConfigPath)
	});
	recipe.execute(project);

	for (const migratedFile of project.getSourceFiles()) {
		it(`should migrate ${migratedFile.getBaseName()} correctly`, ({ assert }) => {
			assert.snapshot(migratedFile.print());
		});
	}
}
