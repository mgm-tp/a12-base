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

import { defineConfig } from "oxlint";

/**
 * Every rule is listed explicitly and all categories are off, so an oxlint upgrade cannot change
 * what we enforce.
 *
 * Formatting is oxfmt's job, including import order (`sortImports` in oxfmt.config.ts).
 * `jsdoc/no-undefined-types` has no oxlint equivalent and nothing replaces it.
 */
export default defineConfig({
	plugins: ["typescript", "import", "unicorn"],
	jsPlugins: [
		{ name: "header", specifier: "@tony.ganchev/eslint-plugin-header" },
		{ name: "workspaces", specifier: "eslint-plugin-workspaces" }
	],
	categories: {
		correctness: "off",
		suspicious: "off",
		pedantic: "off",
		perf: "off",
		style: "off",
		restriction: "off",
		nursery: "off"
	},
	env: { builtin: true },
	options: {
		typeAware: true,
		reportUnusedDisableDirectives: "error"
	},
	ignorePatterns: [
		"build",
		"lib",
		"node_modules",
		"target",
		"testData",
		"worktrees",
		"licenses",
		"THIRD_PARTY_NOTICES",
		"documentation/src/assets/typedoc"
	],
	rules: {
		"curly": "error",
		"eqeqeq": ["error", "smart"],
		"for-direction": "error",
		"no-array-constructor": "error",
		"no-async-promise-executor": "error",
		"no-caller": "error",
		"no-case-declarations": "error",
		"no-compare-neg-zero": "error",
		"no-cond-assign": "error",
		"no-console": "error",
		"no-constant-binary-expression": "error",
		"no-constant-condition": "error",
		"no-control-regex": "error",
		"no-debugger": "error",
		"no-delete-var": "error",
		"no-dupe-else-if": "error",
		"no-duplicate-case": "error",
		"no-empty": "error",
		"no-empty-character-class": "error",
		"no-empty-pattern": "error",
		"no-empty-static-block": "error",
		"no-eval": "error",
		"no-ex-assign": "error",
		"no-extra-boolean-cast": "error",
		"no-fallthrough": "error",
		"no-global-assign": "error",
		"no-invalid-regexp": "error",
		"no-irregular-whitespace": "error",
		"no-iterator": "error",
		"no-loss-of-precision": "error",
		"no-misleading-character-class": "error",
		"no-nonoctal-decimal-escape": "error",
		"no-prototype-builtins": "error",
		"no-regex-spaces": "error",
		"no-restricted-imports": [
			"error",
			{
				"patterns": [
					{ "regex": "node:assert$", "message": "Use 'node:assert/strict' instead." },
					{
						"regex": "node:*",
						"importNames": ["*", "default"],
						"message": "Use the specific named exports instead."
					}
				]
			}
		],
		"no-self-assign": "error",
		"no-shadow-restricted-names": "error",
		"no-sparse-arrays": "error",
		"no-unassigned-vars": "error",
		"no-unexpected-multiline": "error",
		"no-unreachable": "error",
		"no-unsafe-finally": "error",
		"no-unsafe-optional-chaining": "error",
		"no-unused-expressions": "error",
		"no-unused-labels": "error",
		"no-unused-private-class-members": "error",
		"no-unused-vars": [
			"error",
			{
				"args": "all",
				"argsIgnorePattern": "^_",
				"caughtErrors": "all",
				"caughtErrorsIgnorePattern": "^_",
				"destructuredArrayIgnorePattern": "^_",
				"varsIgnorePattern": "^_",
				"ignoreRestSiblings": true
			}
		],
		"no-useless-assignment": "error",
		"no-useless-backreference": "error",
		"no-useless-catch": "error",
		"no-useless-constructor": "error",
		"no-useless-escape": "error",
		"no-useless-rename": "error",
		"no-var": "error",
		"prefer-const": "error",
		"prefer-rest-params": "error",
		"prefer-spread": "error",
		"preserve-caught-error": "error",
		"require-yield": "error",
		"use-isnan": "error",
		"valid-typeof": "error",
		"import/default": "error",
		"import/namespace": "error",
		"import/newline-after-import": ["error", { "count": 1 }],
		"import/no-self-import": "error",
		"oxc/bad-array-method-on-arguments": "error",
		"oxc/bad-char-at-comparison": "error",
		"oxc/bad-comparison-sequence": "error",
		"oxc/bad-match-all-arg": "error",
		"oxc/bad-min-max-func": "error",
		"oxc/bad-object-literal-comparison": "error",
		"oxc/bad-replace-all-arg": "error",
		"oxc/const-comparisons": "error",
		"oxc/double-comparisons": "error",
		"oxc/erasing-op": "error",
		"oxc/misrefactored-assign-op": "error",
		"oxc/missing-throw": "error",
		"oxc/no-accumulating-spread": "error",
		"oxc/number-arg-out-of-range": "error",
		"oxc/only-used-in-recursion": "error",
		"oxc/uninvoked-array-callback": "error",
		"typescript/await-thenable": "error",
		"typescript/ban-ts-comment": ["error", { "minimumDescriptionLength": 10 }],
		"typescript/consistent-type-exports": "error",
		"typescript/consistent-type-imports": "error",
		"typescript/no-array-delete": "error",
		"typescript/no-base-to-string": "error",
		"typescript/no-confusing-void-expression": ["error", { "ignoreArrowShorthand": true }],
		"typescript/no-deprecated": "error",
		"typescript/no-duplicate-enum-values": "error",
		"typescript/no-duplicate-type-constituents": "error",
		"typescript/no-dynamic-delete": "error",
		"typescript/no-empty-object-type": "warn",
		"typescript/no-explicit-any": "error",
		"typescript/no-extra-non-null-assertion": "error",
		"typescript/no-extraneous-class": "error",
		// node:test's suite functions return a promise the runner itself awaits
		"typescript/no-floating-promises": [
			"error",
			{
				"allowForKnownSafeCalls": [
					{
						"from": "package",
						"package": "node:test",
						"name": ["describe", "it", "test", "suite"]
					}
				]
			}
		],
		"typescript/no-for-in-array": "error",
		"typescript/no-implied-eval": "error",
		"typescript/no-invalid-void-type": "error",
		"typescript/no-meaningless-void-operator": "error",
		"typescript/no-misused-new": "error",
		"typescript/no-misused-promises": "error",
		"typescript/no-misused-spread": "error",
		"typescript/no-mixed-enums": "error",
		"typescript/no-non-null-asserted-nullish-coalescing": "error",
		"typescript/no-non-null-asserted-optional-chain": "error",
		"typescript/no-non-null-assertion": "error",
		"typescript/no-redundant-type-constituents": "error",
		"typescript/no-require-imports": "error",
		"typescript/no-this-alias": "error",
		"typescript/no-unnecessary-boolean-literal-compare": "error",
		"typescript/no-unnecessary-condition": "error",
		"typescript/no-unnecessary-parameter-property-assignment": "error",
		"typescript/no-unnecessary-template-expression": "error",
		"typescript/no-unnecessary-type-arguments": "error",
		"typescript/no-unnecessary-type-assertion": "error",
		"typescript/no-unnecessary-type-constraint": "error",
		"typescript/no-unnecessary-type-conversion": "error",
		"typescript/no-unnecessary-type-parameters": "error",
		"typescript/no-unsafe-argument": "error",
		"typescript/no-unsafe-assignment": "error",
		"typescript/no-unsafe-call": "error",
		"typescript/no-unsafe-declaration-merging": "error",
		"typescript/no-unsafe-enum-comparison": "error",
		"typescript/no-unsafe-function-type": "error",
		"typescript/no-unsafe-member-access": "error",
		"typescript/no-unsafe-return": "error",
		"typescript/no-unsafe-unary-minus": "error",
		"typescript/no-useless-default-assignment": "error",
		"typescript/no-useless-empty-export": "error",
		"typescript/no-wrapper-object-types": "error",
		"typescript/only-throw-error": "error",
		"typescript/prefer-as-const": "error",
		"typescript/prefer-literal-enum-member": "error",
		"typescript/prefer-namespace-keyword": "error",
		"typescript/prefer-promise-reject-errors": "error",
		"typescript/prefer-reduce-type-parameter": "error",
		"typescript/prefer-return-this-type": "error",
		"typescript/related-getter-setter-pairs": "error",
		"typescript/require-array-sort-compare": "error",
		"typescript/require-await": "error",
		"typescript/restrict-plus-operands": [
			"error",
			{
				"allowAny": false,
				"allowBoolean": false,
				"allowNullish": false,
				"allowNumberAndString": false,
				"allowRegExp": false
			}
		],
		"typescript/restrict-template-expressions": [
			"error",
			{
				"allowAny": false,
				"allowBoolean": false,
				"allowNever": false,
				"allowNullish": false,
				"allowNumber": true,
				"allowRegExp": false
			}
		],
		"typescript/return-await": ["error", "error-handling-correctness-only"],
		"typescript/switch-exhaustiveness-check": "error",
		"typescript/triple-slash-reference": "error",
		"typescript/unbound-method": "error",
		"typescript/unified-signatures": "error",
		"typescript/use-unknown-in-catch-callback-variable": "error",
		"unicorn/no-await-in-promise-methods": "error",
		"unicorn/no-empty-file": "error",
		"unicorn/no-invalid-fetch-options": "error",
		"unicorn/no-invalid-remove-event-listener": "error",
		"unicorn/no-new-array": "error",
		"unicorn/no-single-promise-in-promise-methods": "error",
		"unicorn/no-thenable": "error",
		"unicorn/no-unnecessary-await": "error",
		"unicorn/no-useless-fallback-in-spread": "error",
		"unicorn/no-useless-length-check": "error",
		"unicorn/no-useless-spread": "error",
		"unicorn/prefer-set-size": "error",
		"unicorn/prefer-string-starts-ends-with": "error",
		"header/header": ["error", { "header": { "file": "./license_header.txt" } }],
		"workspaces/no-absolute-imports": "error",
		"workspaces/no-cross-imports": "error",
		"workspaces/no-relative-imports": "error",
		"workspaces/require-dependency": "error"
	}
});
