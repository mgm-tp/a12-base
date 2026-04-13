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
package com.mgmtp.a12.model.ident;

import com.mgmtp.a12.model.Model;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.Validate;

import java.io.Serializable;

/**
 * Provides identity functionality for {@link Model}s.
 */
public final class ModelIdentifier implements Serializable {

	private String persistedId;
	private String id;

	/**
	 * Creates a new {@link ModelIdentifier} instance from the given string
	 *
	 * @param id the string containing the identifier. May not be null or empty.
	 */
	public ModelIdentifier(final String id) {
		Validate.notBlank(id);
		this.id = id;
		this.persistedId = id;
	}

	/**
	 * @param modelIdentifier the {@link ModelIdentifier} to compare to.
	 * @return true if both {@link ModelIdentifier}s identify the same {@link Model}. False otherwise.
	 */
	public boolean equals(final ModelIdentifier modelIdentifier) {
		return Strings.CS.equals(id, modelIdentifier.getIdentifier());
	}

	public String getIdentifier() {
		return id;
	}

	/**
	 * Sets the identifier to the given string.
	 *
	 * @param newId the string containing the identifier. May not be null or empty.
	 */
	public void setIdentifier(final String newId) {
		Validate.notBlank(newId);
		this.id = newId;
	}

	/**
	 * @return the persisted identifier, i.e. the identifier that was used during the last persistence operation
	 */
	public String getPersistedIdentifier() {
		return persistedId;
	}

	/**
	 * <p>
	 * Aligns the persisted identifier with the latest state of the identifier.
	 * </p>
	 * <p>
	 * <b>Note:This method should only be called by a component that can ensure uniqueness of {@link
	 * ModelIdentifier}s within the context of the application.</b>
	 * </p>
	 */
	public void persist() {
		persistedId = id;
	}

}
