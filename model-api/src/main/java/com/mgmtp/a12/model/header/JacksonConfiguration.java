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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class JacksonConfiguration {

	public static ObjectMapper configureMixins(final ObjectMapper objectMapper) {
		Objects.requireNonNull(objectMapper);
		objectMapper.addMixIn(Header.class, HeaderMixin.class)
			.addMixIn(Annotation.class, AnnotationMixin.class)
			.addMixIn(Label.class, LabelMixin.class)
			.addMixIn(ModelReference.class, ModelReferenceMixin.class)
			.addMixIn(Locale.class, LocaleMixin.class);
		return objectMapper;
	}

	@JsonDeserialize(as = HeaderImpl.class)
	interface HeaderMixin {

		@JsonDeserialize(as = ArrayList.class, contentAs = Locale.class)
		@JsonInclude(JsonInclude.Include.NON_NULL)
		List<Locale> getLocales();

		@JsonDeserialize(as = ArrayList.class, contentAs = Label.class)
		@JsonInclude(JsonInclude.Include.NON_NULL)
		List<Label> getLabels();

		/**
		 * @return the description of the model, can be null
		 */
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String getDescription();

		/**
		 * @return key value pairs of annotation name & value, can be null
		 */
		@JsonDeserialize(as = ArrayList.class, contentAs = Annotation.class)
		@JsonInclude(JsonInclude.Include.NON_NULL)
		List<Annotation> getAnnotations();

		/**
		 * @return all references to other models, can be null
		 */
		@JsonDeserialize(as = ArrayList.class, contentAs = ModelReference.class)
		@JsonInclude(JsonInclude.Include.NON_NULL)
		List<ModelReference> getModelReferences();
	}

	@JsonDeserialize(as = AnnotationImpl.class)
	interface AnnotationMixin {
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String getValue();
	}

	@JsonDeserialize(converter = JsonToLocaleConverter.class)
	@JsonSerialize(converter = LocaleToJsonConverter.class)
	interface LocaleMixin {
	}

	@JsonDeserialize(as = LabelImpl.class)
	interface LabelMixin {

		@JsonSerialize(converter = LocaleToStringConverter.class)
		Locale getLocale();

		@JsonInclude(JsonInclude.Include.NON_NULL)
		String getText();
	}

	@JsonDeserialize(as = ModelReferenceImpl.class)
	interface ModelReferenceMixin {
		@JsonInclude(JsonInclude.Include.NON_NULL)
		String getPurpose();

		@JsonInclude(JsonInclude.Include.NON_NULL)
		String getAlias();
	}
}
