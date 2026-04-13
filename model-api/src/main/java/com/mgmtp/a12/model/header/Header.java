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

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

/**
 * Contains common header information for all types of A12 models.
 */
public interface Header extends Serializable {

	/**
	 * @return the unique identifier of the model
	 */
	String getId();

	/**
	 * @return the name of the meta model, e.g. application, document, form, overview, relationship, tree
	 */
	String getModelType();

	/**
	 * @return the version of the meta model
	 */
	String getModelVersion();

	/**
	 * @return all supported locales of the model, can be null
	 */
	List<Locale> getLocales();

	/**
	 * @return all labels of the model, can be null
	 */
	List<Label> getLabels();

	/**
	 * @return the description of the model, can be null
	 */
	String getDescription();

	/**
	 * @return key value pairs of annotation name & value, can be null
	 */
	List<Annotation> getAnnotations();

	/**
	 * @return all references to other models, can be null
	 */
	List<ModelReference> getModelReferences();
}
