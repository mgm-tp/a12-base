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
package com.mgmtp.a12.model.consistency;


import com.mgmtp.a12.model.Model;
import com.mgmtp.a12.model.consistency.rules.ModelConsistencyRule;
import com.mgmtp.a12.model.consistency.rules.RuleExecutor;
import com.mgmtp.a12.model.data.document.ModelResolver;

import org.apache.commons.lang3.Validate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Provides the entry point for performing consistency checks for all kinds of A12 models. These checks can be executed
 * on single models as well as on a set of models to make sure that no models have references to models outside this
 * set.
 */
@SuppressWarnings("rawtypes")
public class ConsistencyValidator {

	private final ModelResolver modelResolver;
	private final List<CustomConsistencyRule> customRules = new ArrayList<>();

	/**
	 * Constructor for initializing the consistency validator with a {@link ModelResolver}
	 *
	 * @param modelResolver instance of {@link ModelResolver}, may not be null
	 */
	public ConsistencyValidator(final ModelResolver modelResolver) {
		Objects.requireNonNull(modelResolver);
		this.modelResolver = modelResolver;
	}

	/**
	 * Performs the consistency check for a single model.
	 *
	 * @param model the {@link Model} to check, may not be null
	 * @return {@link ConsistencyStatus} containing a list of all {@link Problem}s
	 */
	public ConsistencyStatus validate(final Model model) {
		Objects.requireNonNull(model, "The model to be validated may not be null.");

		final RuleExecutor ruleExecutor = initializeRuleExecutor();
		return generateValidationStatus(validateSingleModel(ruleExecutor, model));
	}

	/**
	 * Performs the consistency check for a collection of models.
	 *
	 * @param models collection of {@link Model}s which may not be null or empty
	 * @return {@link ConsistencyStatus} containing a list of all {@link Problem}s for all models
	 */
	public ConsistencyStatus validateSet(final Collection<Model> models) {
		Validate.notEmpty(models, "The collection of models may not be empty");

		final RuleExecutor ruleExecutor = initializeRuleExecutor();

		final List<Problem> problems = new ArrayList<>();
		for (final Model model : models) {
			problems.addAll(validateSingleModel(ruleExecutor, model));
		}
		return generateValidationStatus(problems);
	}

	public void addCustomRule(final CustomConsistencyRule<? extends Model> customRule) {
		Objects.requireNonNull(customRule);
		this.customRules.add(customRule);
	}

	public void addCustomRules(final Collection<CustomConsistencyRule> customRules) {
		Objects.requireNonNull(customRules);
		this.customRules.addAll(customRules);
	}

	private RuleExecutor initializeRuleExecutor() {
		return new RuleExecutor(modelResolver, customRules);
	}

	private List<Problem> validateSingleModel(final RuleExecutor ruleExecutor, final Model model) {
		return ruleExecutor.validateModel(model);
	}

	private static ConsistencyStatus generateValidationStatus(final List<Problem> problems) {
		return problems.isEmpty()
			? new ConsistencyStatus(true, problems)
			: new ConsistencyStatus(false, problems);
	}

	public interface CustomConsistencyRule<T extends Model> {

		<T extends Model> Class<T> getModelType();

		ModelConsistencyRule<T> getRule();
	}
}
