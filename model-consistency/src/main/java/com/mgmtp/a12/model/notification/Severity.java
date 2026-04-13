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
package com.mgmtp.a12.model.notification;

/**
 * Severity level which is used to rank a notification, see also {@link RankedNotification}.
 */
public enum Severity {
	// The order of the severities in this enum is important since ordinal() is used to compare two of them,
	// see method #isAtLeastAsSevereAs(Severity).
	// This means that these severities need to be ordered from less severe to more severe.
	INFO,
	WARNING,
	ERROR;

	/**
	 * @param severity reference to which {@code this} should be compared. Must not be null.
	 * @return {@code true} if {@code this} is equal or more severe than the given reference ({@code severity}),
	 * 	otherwise {@code false}.
	 */
	public boolean isAtLeastAsSevereAs(Severity severity) {
		return ordinal() >= severity.ordinal();
	}
}
