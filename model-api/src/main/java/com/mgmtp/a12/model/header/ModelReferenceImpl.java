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
package com.mgmtp.a12.model.header;

import java.io.Serializable;
import java.util.Objects;

public class ModelReferenceImpl implements ModelReference, Serializable {

	ModelReferenceImpl() {
	}

	ModelReferenceImpl(final String purpose, final String modelType, final String alias, final String reference) {
		this.purpose = purpose;
		this.modelType = modelType;
		this.alias = alias;
		this.reference = reference;
	}

	private String alias;
	private String modelType;
	private String purpose;
	private String reference;

	@Override
	public String getPurpose() {
		return purpose;
	}

	@Override
	public String getModelType() {
		return modelType;
	}

	@Override
	public String getAlias() {
		return alias;
	}

	@Override
	public String getReference() {
		return reference;
	}

	void setPurpose(final String purpose) {
		this.purpose = purpose;
	}

	void setModelType(final String modelType) {
		this.modelType = modelType;
	}

	void setAlias(final String alias) {
		this.alias = alias;
	}

	void setReference(final String reference) {
		this.reference = reference;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof ModelReferenceImpl that)) {
			return false;
		}
		return Objects.equals(getPurpose(), that.getPurpose()) &&
			Objects.equals(getModelType(), that.getModelType()) &&
			Objects.equals(getAlias(), that.getAlias()) &&
			Objects.equals(getReference(), that.getReference());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPurpose(), getModelType(), getAlias(), getReference());
	}
}
