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
package com.mgmtp.a12.model.consistency.rules;

import com.mgmtp.a12.model.Model;
import com.mgmtp.a12.model.consistency.ConsistencyProblem;
import com.mgmtp.a12.model.consistency.ConsistencyValidator;
import com.mgmtp.a12.model.consistency.Problem;
import com.mgmtp.a12.model.data.document.ModelResolver;
import com.mgmtp.a12.model.data.document.consistency.AbstractRuleWithModelResolver;
import com.mgmtp.a12.model.data.document.consistency.CheckReferencedModelRule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

/**
 * Execute rules which implement {@link ModelConsistencyRule}.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class RuleExecutor {

	private final ModelResolver modelResolver;
	private final List<ConsistencyValidator.CustomConsistencyRule> customRules;

	public RuleExecutor(
		final ModelResolver modelResolver,
		final List<ConsistencyValidator.CustomConsistencyRule> customRules
	) {
		this.modelResolver = modelResolver;
		this.customRules = customRules;
	}

	public List<Problem> validateModel(final Model model) {
		final List<ModelConsistencyRule> modelRules = findModelRules(model);
		return executeRules(modelRules, model);
	}

	private List<Problem> executeRules(final Collection<ModelConsistencyRule> modelRules, final Model model) {

		final List<Problem> modelProblems = new ArrayList<>();
		if (modelRules == null || modelRules.isEmpty()) {
			String modelId = model.getHeader().getId();
			modelProblems.add(new ConsistencyProblem(
				modelId,
				ModelConsistencyCategory.MODEL_CHECKER_NOT_FOUND,
				modelId,
				modelId
			));
		} else {
			for (final ModelConsistencyRule modelConsistencyRule : modelRules) {
				try {
					if (modelConsistencyRule instanceof AbstractRuleWithModelResolver) {
						((AbstractRuleWithModelResolver<? extends Model>) modelConsistencyRule).setModelResolver(
							modelResolver);
					}
					if (modelConsistencyRule instanceof CheckReferencedModelRule) {
						final CheckReferencedModelRule<? extends Model> checkReferencedModelRule =
							(CheckReferencedModelRule<? extends Model>) modelConsistencyRule;
						checkReferencedModelRule.setModelResolver(modelResolver);
						checkReferencedModelRule.setRuleExecutor(this);
					}
					modelProblems.addAll(modelConsistencyRule.executeRule(model));
				} catch (final FatalRuleProblemException e) {
					final List<Problem> problems = e.getProblems();
					if (problems != null) {
						modelProblems.addAll(problems);
					}
				}
			}
		}
		return modelProblems;
	}

	private List<ModelConsistencyRule> findModelRules(final Model model) {
		final List<ModelConsistencyRule> result = new ArrayList<>();

		for (final ModelConsistencyRulesProvider provider : ServiceLoader.load(ModelConsistencyRulesProvider.class)) {
			if (provider.supports(model.getClass())) {
				result.addAll(provider.getRules());
				result.addAll(filterCustomRules(provider.modelType()));
			}
		}

		return result;
	}

	private Collection<? extends ModelConsistencyRule> filterCustomRules(final Class<? extends Model> modelType) {
		final List<ModelConsistencyRule> result = new ArrayList<>();
		for (final ConsistencyValidator.CustomConsistencyRule customRule : customRules) {
			if (modelType.isAssignableFrom(customRule.getModelType())) {
				result.add(customRule.getRule());
			}
		}
		return result;
	}

}
