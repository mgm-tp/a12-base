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
package com.mgmtp.a12.model.consistency;

import com.mgmtp.a12.model.notification.Severity;

import java.util.List;
import java.util.Objects;

/**
 * This class provides a status object of the consistency check of all models. It contains a boolean flag indicating if
 * the validation has been successful and a list of potential validation problems.
 *
 * @param isValid boolean flag
 * @param problems list of validation problems, may not be null
 */
public record ConsistencyStatus(boolean isValid, List<Problem> problems) {

	public ConsistencyStatus {
		Objects.requireNonNull(problems, "List of problems may not be null");
	}

	/**
	 * Provides status flag if validation has been un-successful.
	 *
	 * @return boolean flag if check has been un-successful
	 */
	public boolean isInvalid() {
		return !isValid;
	}

	/**
	 * Check all problems if there is any one with {@link Severity#ERROR}.
	 *
	 * @return true when there is an error.
	 */
	public boolean hasError() {
		for (final Problem problem : problems) {
			if (problem.getSeverity() == Severity.ERROR) {
				return true;
			}
		}
		return false;
	}

}
