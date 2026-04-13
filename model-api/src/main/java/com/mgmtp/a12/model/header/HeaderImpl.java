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
package com.mgmtp.a12.model.header;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class HeaderImpl implements Header, Serializable {

	private String id;
	private String modelType;
	private String modelVersion;
	private List<Locale> locales;
	private List<Label> labels;
	private String description;
	private List<Annotation> annotations;
	private List<ModelReference> modelReferences;

	@JsonCreator
	public HeaderImpl() {
	}

	@Override
	public String getModelType() {
		return modelType;
	}

	@Override
	public String getModelVersion() {
		return modelVersion;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public List<Locale> getLocales() {
		return locales;
	}

	@Override
	public List<Label> getLabels() {
		return labels;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@Override
	public List<ModelReference> getModelReferences() {
		return modelReferences;
	}

	void setModelType(final String modelType) {
		this.modelType = modelType;
	}

	void setModelVersion(final String modelVersion) {
		this.modelVersion = modelVersion;
	}

	void setId(final String id) {
		this.id = id;
	}

	void setLocales(final List<Locale> locales) {
		this.locales = locales;
	}

	void setLabels(final List<Label> labels) {
		this.labels = labels;
	}

	void setDescription(final String description) {
		this.description = description;
	}

	void setAnnotations(final List<Annotation> annotations) {
		this.annotations = annotations;
	}

	void setModelReferences(final List<ModelReference> modelReferences) {
		this.modelReferences = modelReferences;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof HeaderImpl)) {
			return false;
		}
		final HeaderImpl meta = (HeaderImpl) o;
		return Objects.equals(getModelType(), meta.getModelType()) &&
			Objects.equals(getModelVersion(), meta.getModelVersion()) &&
			Objects.equals(getId(), meta.getId()) &&
			Objects.equals(getLocales(), meta.getLocales()) &&
			Objects.equals(getLabels(), meta.getLabels()) &&
			Objects.equals(getDescription(), meta.getDescription()) &&
			Objects.equals(getAnnotations(), meta.getAnnotations()) &&
			Objects.equals(getModelReferences(), meta.getModelReferences());
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			getModelType(),
			getModelVersion(),
			getId(),
			getLocales(),
			getLabels(),
			getDescription(),
			getAnnotations(),
			getModelReferences());
	}
}
