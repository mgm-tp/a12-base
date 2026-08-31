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
import com.mgmtp.a12.model.data.document.ModelResolver;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Base class for performing consistency checks for all kinds of A12 models. These checks can be executed
 * on single models as well as on a set of models to make sure that no models have references to models outside this
 * set.
 *
 * @param <M> the specific model type extending {@link Model}
 */
public abstract class ConsistencyValidator<M extends Model> {

	private final ModelResolver modelResolver;

	/**
	 * Constructor for initializing the consistency validator with a {@link ModelResolver}
	 *
	 * @param modelResolver instance of {@link ModelResolver}, may not be null
	 */
	protected ConsistencyValidator(final ModelResolver modelResolver) {
		this.modelResolver = Objects.requireNonNull(modelResolver, "ModelResolver may not be null");
	}

	/**
	 * Provides access to the {@link ModelResolver} for subclasses to resolve models during validation.
	 *
	 * @return the {@link ModelResolver} instance
	 */
	protected ModelResolver getModelResolver() {
		return modelResolver;
	}

	/**
	 * Performs the consistency check for a single model.
	 *
	 * @param model the {@link Model} to check, may not be null
	 * @return list of {@link Problem}s, empty if valid
	 */
	public abstract List<Problem> validate(M model);

	/**
	 * Performs the consistency check for a collection of models.
	 *
	 * @param models collection of {@link Model}s which may not be null or empty
	 * @return list of {@link Problem}s for all models, empty if all are valid
	 */
	public abstract List<Problem> validateSet(Collection<M> models);
}
