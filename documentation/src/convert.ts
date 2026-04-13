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

import { cp } from "node:fs/promises";
import { join } from "node:path";
import { fileURLToPath } from "node:url";

import createAsciiDoctor from "asciidoctor";
import highlightJsExt from "asciidoctor-highlight.js";

import packageJson from "../package.json" with { type: "json" };

const srcDir = import.meta.dirname;
const buildDir = join(srcDir, "..", "build");

const THEME = "stackoverflow-light";

const doctor = createAsciiDoctor();
highlightJsExt.register(doctor.Extensions);

doctor.convertFile(join(srcDir, "index.adoc"), {
	to_dir: buildDir,
	mkdirs: true,
	safe: "unsafe",
	attributes: {
		doctype: "article",
		revnumber: packageJson.version,
		author: packageJson.author,
		encoding: "utf-8",
		lang: "en",
		icons: "font",
		"source-highlighter": "highlightjs-ext",
		"source-linenums-option": true,
		toc: "left",
		toclevels: 2,
		"toc-title": "Table of Contents",
		docinfo: "shared",
		docinfodir: srcDir,
		tabsize: 2,
		sectnums: true,
		sectanchors: true,
		sectlinks: true,
		experimental: true,
		sectids: true,
		fragment: true,
		xrefstyle: "short",
		standalone: true
	}
});

const themeStyle = import.meta.resolve(`highlight.js/styles/${THEME}.css`);

await Promise.all([
	cp(fileURLToPath(themeStyle), join(buildDir, "highlight-theme.css")),
	cp(join(srcDir, "assets"), join(buildDir, "assets"), { recursive: true })
]);
