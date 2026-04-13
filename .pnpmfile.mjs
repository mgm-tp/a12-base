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

import { unlink } from "node:fs/promises";

/**
 * see https://pnpm.io/pnpmfile
 *
 * Switching branches might introduce new lint errors (e.g. due to new deprecations).
 * If eslint already ran, the cache file is not invalidated -> new errors are not visible.
 *
 * To fix this, we just remove the cache file every time the lockfile is resolved.
 */
export const hooks = {
	/**
	 * @param {any} lockfile
	 * @param {Context} context
	 */
	async afterAllResolved(lockfile, context) {
		try {
			context.log("Removing .eslintcache...");
			await unlink(".eslintcache");
		} finally {
			// There is a pnpm setting for this, but it does not apply for our registry
			for (const key in lockfile.packages) {
				if (lockfile.packages[key].resolution?.tarball) {
					delete lockfile.packages[key].resolution.tarball;
				}
			}

			// eslint-disable-next-line no-unsafe-finally
			return lockfile;
		}
	}
};

/**
 * @typedef {Object} Context
 * @property {(msg: string) => void} log
 */
