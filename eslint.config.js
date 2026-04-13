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

import { readFile } from "node:fs/promises";
import { EOL } from "node:os";

import mocha from "eslint-plugin-mocha";
import notice from "eslint-plugin-notice";
import workspaces from "eslint-plugin-workspaces";
import { defineConfig } from "eslint/config";

import { strict } from "@com.mgmtp.a12.devtools/eslint-config";

const licenseHeaderTemplate = await readFile(`${import.meta.dirname}/license_header.txt`, "utf-8");
const licenseHeaderWithInterpreterLine = `#!/usr/bin/env node${EOL}${licenseHeaderTemplate}`;

const ignores = [
	"**/build",
	"**/lib",
	"**/node_modules",
	"**/target",
	"codemod/src/testData",
	"**/assets/typedoc"
];

export default defineConfig(
	...strict,
	{
		name: `root/ignores`,
		ignores
	},
	{
		name: `root/base`,
		plugins: {
			notice,
			workspaces
		},
		languageOptions: {
			parserOptions: {
				projectService: true
			}
		},
		rules: {
			"@typescript-eslint/consistent-type-exports": "error",
			"@typescript-eslint/consistent-type-imports": "error",
			"@typescript-eslint/no-deprecated": "error",
			"@typescript-eslint/no-empty-object-type": "warn",
			"no-console": "error",
			"workspaces/no-absolute-imports": "error",
			"workspaces/no-cross-imports": "error",
			"workspaces/no-relative-imports": "error",
			"workspaces/require-dependency": "error",
			eqeqeq: ["error", "smart"],
			"notice/notice": [
				"error",
				{
					template: licenseHeaderTemplate,
					onNonMatchingHeader: "replace",
					chars: licenseHeaderTemplate.length
				}
			]
		}
	},
	{
		name: "files-with-license-header-and-interpreter-line",
		files: ["**/cli.ts"],
		rules: {
			"notice/notice": [
				"error",
				{
					template: licenseHeaderWithInterpreterLine,
					onNonMatchingHeader: "replace",
					chars: licenseHeaderWithInterpreterLine.length,
					messages: {
						whenFailedToMatch: "Missing or incorrect restrictive licence header"
					}
				}
			]
		}
	},
	{
		name: "base/testOverrides",
		files: ["./model-api-ts/src/test/**/*"],
		plugins: {
			mocha
		},
		rules: {
			"mocha/no-setup-in-describe": "error"
		}
	}
);
