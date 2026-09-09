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

import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Provides creation functionality for {@link Header}s and derived types {@link Label}, {@link Annotation} and
 * {@link ModelReference}.
 */
public class HeaderFactory {

	/**
	 * Provides a builder to create {@link Header} instances.
	 */
	public static class Builder {

		private String id;
		private String modelType;
		private String modelVersion;
		private List<Locale> locales;
		private List<Label> labels;
		private String description;
		private List<Annotation> annotations;
		private List<ModelReference> modelReferences;

		/**
		 * Adds the id
		 *
		 * @param id may not be null or empty
		 */
		public Builder withId(final String id) {
			Validate.notBlank(id);
			this.id = id;
			return this;
		}

		/**
		 * Adds the model type
		 *
		 * @param modelType may not be null or empty
		 */
		public Builder withModelType(final String modelType) {
			Validate.notBlank(modelType);
			this.modelType = modelType;
			return this;
		}

		/**
		 * Adds the model version
		 *
		 * @param modelVersion may not be null or empty
		 */
		public Builder withModelVersion(final String modelVersion) {
			Validate.notBlank(modelVersion);
			this.modelVersion = modelVersion;
			return this;
		}

		/**
		 * Adds {@link Locale}s
		 *
		 * @param locales may not be null
		 */
		public Builder withLocales(final List<Locale> locales) {
			Objects.requireNonNull(locales);
			this.locales = locales;
			return this;
		}

		/**
		 * Adds {@link Locale}s
		 *
		 * @param locales may not be null
		 */
		public Builder withLocales(final Locale... locales) {
			Objects.requireNonNull(locales);
			this.locales = Arrays.asList(locales);
			return this;
		}

		/**
		 * Adds {@link Label}s
		 *
		 * @param labels may not be null
		 */
		public Builder withLabels(final List<Label> labels) {
			Objects.requireNonNull(labels);
			this.labels = labels;
			return this;
		}

		/**
		 * Adds {@link Label}s
		 *
		 * @param labels may not be null
		 */
		public Builder withLabels(final Label... labels) {
			Objects.requireNonNull(labels);
			this.labels = Arrays.asList(labels);
			return this;
		}

		/**
		 * Adds a description
		 *
		 * @param description may not be null
		 */
		public Builder withDescription(final String description) {
			Objects.requireNonNull(description);
			this.description = description;
			return this;
		}

		/**
		 * Adds {@link Annotation}s
		 *
		 * @param annotations may not be null
		 */
		public Builder withAnnotations(final List<Annotation> annotations) {
			Objects.requireNonNull(annotations);
			this.annotations = annotations;
			return this;
		}

		/**
		 * Adds {@link Annotation}s
		 *
		 * @param annotations may not be null
		 */
		public Builder withAnnotations(final Annotation... annotations) {
			Objects.requireNonNull(annotations);
			this.annotations = Arrays.asList(annotations);
			return this;
		}

		/**
		 * Adds model references
		 *
		 * @param modelReferences may not be null
		 */
		public Builder withModelReferences(final List<ModelReference> modelReferences) {
			Objects.requireNonNull(modelReferences);
			this.modelReferences = modelReferences;
			return this;
		}

		/**
		 * Adds model references
		 *
		 * @param modelReferences may not be null
		 */
		public Builder withModelReferences(final ModelReference... modelReferences) {
			Objects.requireNonNull(modelReferences);
			this.modelReferences = Arrays.asList(modelReferences);
			return this;
		}

		/**
		 * @return the new {@link Header} instance
		 * @throws IllegalArgumentException if id, model type or model version were not specified
		 */
		public Header build() {
			if (id == null || modelType == null || modelVersion == null) {
				throw new IllegalArgumentException("The header must contain at least id, modelType and modelVersion!");
			}
			final HeaderImpl result = new HeaderImpl();
			result.setId(id);
			result.setModelType(modelType);
			result.setModelVersion(modelVersion);
			if (locales != null) {
				result.setLocales(locales);
			}
			if (labels != null) {
				result.setLabels(labels);
			}
			if (description != null) {
				result.setDescription(description);
			}
			if (annotations != null) {
				result.setAnnotations(annotations);
			}
			if (modelReferences != null) {
				result.setModelReferences(modelReferences);
			}
			return result;
		}
	}

	/**
	 * Creates a new {@link Annotation} instance
	 *
	 * @param name the name, may not be null or empty
	 * @param value the value
	 * @return the new {@link Annotation} instance
	 */
	public static Annotation createAnnotation(final String name, final String value) {
		Validate.notBlank(name);
		final AnnotationImpl annotation = new AnnotationImpl();
		annotation.setName(name);
		annotation.setValue(value);
		return annotation;
	}

	/**
	 * Creates a new {@link Label} instance
	 *
	 * @param locale the locale of the label, maybe not be null
	 * @param text the text of the label, may not be null or empty
	 * @return the new {@link Label} instance
	 */
	public static Label createLabel(final Locale locale, final String text) {
		Objects.requireNonNull(locale);
		Validate.notBlank(text);
		final LabelImpl label = new LabelImpl();
		label.setLocale(locale);
		label.setText(text);
		return label;
	}

	/**
	 * Creates a new {@link ModelReference} instance
	 *
	 * @param purpose the purpose of the model reference
	 * @param modelType the model type of the model reference, may not be null or empty
	 * @param alias the alias of the model reference
	 * @param reference the actual reference, may not be null or empty
	 * @return the new {@link ModelReference} instance
	 */
	public static ModelReference createModelReference(
		final String purpose,
		final String modelType,
		final String alias,
		final String reference) {
		Validate.notBlank(modelType);
		Validate.notBlank(reference);
		final ModelReferenceImpl modelRef = new ModelReferenceImpl();
		modelRef.setAlias(alias);
		modelRef.setModelType(modelType);
		modelRef.setPurpose(purpose);
		modelRef.setReference(reference);
		return modelRef;
	}

}
