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
import { defineConfig } from "oxfmt";

export default defineConfig({
	useTabs: true,
	tabWidth: 2,
	trailingComma: "none",
	arrowParens: "avoid",
	printWidth: 100,
	endOfLine: "lf",
	insertFinalNewline: true,
	sortPackageJson: false,

	/**
	 * The `ancestors` group has to match `../../**`: oxfmt's globstar crosses `..`, so a custom
	 * `../**` group would match every parent import instead of only the direct-parent tier.
	 */
	sortImports: {
		groups: ["builtin", "external", "internal", "ancestors", "parent", "sibling", "index"],
		customGroups: [{ groupName: "ancestors", elementNamePattern: ["../../**"] }],
		newlinesBetween: true,
		internalPattern: ["@com.mgmtp.a12"]
	},

	/**
	 * gitignore semantics: a bare name matches at any depth, a pattern with a slash is anchored.
	 * `testData` holds codemod fixtures whose imports must keep the exact order the test expects,
	 * and `*.api.md` is generated (and the repo's only CRLF file).
	 */
	ignorePatterns: [
		"*.api.md",
		"THIRD_PARTY_NOTICES",
		"licenses",
		"build",
		"lib",
		"target",
		"testData",
		"worktrees",
		"documentation/src/assets/typedoc"
	]
});
