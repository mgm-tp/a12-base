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
package com.mgmtp.a12.model.consistency;

import com.mgmtp.a12.model.Model;
import com.mgmtp.a12.model.notification.Severity;

import java.util.Locale;

/**
 * Provides a data structure for collecting problems during the consistency check of {@link Model}s.
 */
public class ConsistencyProblem implements Problem {

	/**
	 * Unique model id
	 */
	private final String modelId;

	/**
	 * Problem category based on which a message is retrieved from localized properties file.
	 */
	private final ConsistencyCategory category;

	/**
	 * Object containing information about the source of the problem.
	 */
	private final Object source;

	/**
	 * The ranked severity of the problem. Can be info, warning or error.
	 */
	private final Severity severity;

	/**
	 * List of parameters to be added to the problem message, e.g. path to field, referenced model name, etc.
	 */
	private final Object[] parameters;

	/**
	 * Constructor that allows setting the severity
	 *
	 * @param modelId unique model name
	 * @param category error code
	 * @param source source of problem
	 * @param severity severity of the problem
	 * @param parameters list of parameters to be added to the problem message
	 */
	public ConsistencyProblem(
		final String modelId,
		final ConsistencyCategory category,
		final Object source,
		final Severity severity,
		final Object... parameters) {
		this.modelId = modelId;
		this.category = category;
		this.parameters = parameters;
		this.severity = severity;
		this.source = source;
	}

	/**
	 * Constructor in which the {@link Severity} INFO is used by default.
	 *
	 * @param modelId unique model name
	 * @param category error code
	 * @param source source of problem
	 * @param parameters list of string parameters to be added to the problem description
	 */
	public ConsistencyProblem(
		final String modelId,
		final ConsistencyCategory category,
		final Object source,
		final Object... parameters) {
		this(modelId, category, source, Severity.INFO, parameters);
	}

	@Override
	public String toString() {
		return "Problem [modelId=" + modelId + ", getMessage()=" + getMessage() + "]";
	}

	/**
	 * Get localized problem message for the given {@link Locale} object. If the locale object is not available it
	 * returns the default problem message in English.
	 *
	 * @param locale the locale to use for localization
	 * @return localized problem message as string
	 */
	@Override
	public String getLocalizedMessage(final Locale locale) {
		final String localizedMessage = category.getLocalizedMessage(locale, category.getKeyValue());
		return String.format(localizedMessage, parameters);
	}

	/**
	 * Get problem message in English.
	 *
	 * @return problem message as string
	 */
	@Override
	public String getMessage() {
		return getLocalizedMessage(Locale.ENGLISH);
	}

	/**
	 * Used to structure the final list of problems. Each problem belongs to a model identified by this model id.
	 */
	@Override
	public String getModelId() {
		return modelId;
	}

	@Override
	public ConsistencyCategory getCategory() {
		return category;
	}

	@Override
	public Object getSource() {
		return source;
	}

	@Override
	public Severity getSeverity() {
		return this.severity;
	}

}
