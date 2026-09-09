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
package com.mgmtp.a12.model.migration;

import java.util.Optional;

/**
 * Representation of the result of migrating a model.
 */
public class MigrationResult {
	public final String model;
	public final String modelId;
	public final Status status;
	public final Optional<String> errorMessage;

	public enum Status {SUCCESS, ERROR, SKIPPED}

	public MigrationResult(String model, String modelId, Status status, Optional<String> errorMessage) {
		this.model = model;
		this.modelId = modelId;
		this.status = status;
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the migrated model as string, if {@link #getStatus()} is {@link Status#SUCCESS}. Otherwise, the
	 * 	unmigrated model string.
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @return the id of the migrated model. Is never {@code null} or blank.
	 */
	public String getModelId() {
		return modelId;
	}

	/**
	 * @return the result type of migration:
	 * 	<ul>
	 * 		<li>{@link Status#SUCCESS}, if there were no errors during migration.</li>
	 * 		<li>{@link Status#SKIPPED}, if the model is already up-to-date and does not need not be migrated</li>
	 * 		<li>{@link Status#ERROR}, if at least one error occurred during migration.
	 * 		See {{@link #getErrorMessage()} for more details.</li>
	 * 	</ul>
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @return {@link Optional#empty()} if no error occurred during migration (i.e. {@link #getStatus()} is not
	 *    {@link Status#ERROR}). Otherwise, the corresponding error message.
	 */
	public Optional<String> getErrorMessage() {
		return errorMessage;
	}
}
