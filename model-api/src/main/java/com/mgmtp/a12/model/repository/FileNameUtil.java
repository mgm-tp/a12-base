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
package com.mgmtp.a12.model.repository;

import com.mgmtp.a12.model.Model;

import org.apache.commons.lang3.Validate;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * <p>
 * Provides utility methods for file name handling in {@link FsModelRepository}.
 * </p>
 * <p>
 * Since file names are derived from model names, they must be converted in a way that ensures compatibility with the
 * file systems encodings.
 * </p>
 */
class FileNameUtil {

	private static final String BASE_PATTERN = "a-zA-Z0-9_-";
	static final Pattern INVALID_CHARACTERS = Pattern.compile("[^" + BASE_PATTERN + "]");
	static final Pattern VALID_FILENAME = Pattern.compile("[" + BASE_PATTERN + "]+");

	/**
	 * <p>
	 * Converts a {@link Model} name to a file name that works with (most) file system character sets.
	 * </p>
	 * <p>
	 * The conversion will consider the {@link Model} name as a file name if it conforms to the list of accepted
	 * characters {@link FileNameUtil#INVALID_CHARACTERS}. If it does not, all characters that do not conform will be
	 * removed. If this leads to an empty string, a pseudo-randomly generated UUID will be used instead.
	 * </p>
	 *
	 * @param id the name of the model to convert to a file name
	 * @return the file name
	 */
	static String getFileNameForModel(final String id) {
		Validate.notBlank(id);

		String result = id;
		if (!VALID_FILENAME.matcher(id).matches()) {
			result = INVALID_CHARACTERS.matcher(id).replaceAll("");
		}

		if (result.isEmpty()) {
			result = UUID.randomUUID().toString();
		}

		return result;
	}

}
