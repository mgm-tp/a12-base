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
package com.mgmtp.a12.model.data.document.consistency;

import com.mgmtp.a12.model.Model;
import com.mgmtp.a12.model.consistency.ConsistencyProblem;
import com.mgmtp.a12.model.consistency.Problem;
import com.mgmtp.a12.model.consistency.impl.InvalidModelResolverException;
import com.mgmtp.a12.model.consistency.rules.ModelConsistencyRule;
import com.mgmtp.a12.model.consistency.rules.RuleExecutor;
import com.mgmtp.a12.model.data.document.ModelResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This rule passes a model to the consistency validator that is referenced by the model that is currently checked.
 * Thus, by using this rule the consistency check will also ensure the consistency of transitive models.
 *
 * @param <M> the concrete type of {@link Model} that is being validated
 */
public abstract class CheckReferencedModelRule<M extends Model> implements ModelConsistencyRule<M> {

	private RuleExecutor ruleExecutor;
	private ModelResolver modelResolver;

	public abstract String getReferencedModelName(final M model);

	@Override
	public List<Problem> executeRule(final M model) {
		final String modelId = model.getHeader().getId();
		final String referencedModelName = getReferencedModelName(model);
		final List<Problem> problems = new ArrayList<>();
		try {
			final Optional<Model> resolvedModel = modelResolver.getModel(referencedModelName);
			if (resolvedModel.isPresent()) {
				final List<Problem> documentModelProblems = ruleExecutor.validateModel(resolvedModel.get());
				problems.addAll(documentModelProblems);
			} else {
				problems.add(
					new ConsistencyProblem(
						modelId,
						DocumentModelCategory.MODEL_COULD_NOT_BE_RESOLVED,
						modelId,
						referencedModelName
					));
			}
		} catch (final InvalidModelResolverException e) {
			problems.add(
				new ConsistencyProblem(
					modelId,
					DocumentModelCategory.INVALID_MODEL_RESOLVER,
					modelId,
					referencedModelName
				));
		}
		return problems;
	}

	public void setRuleExecutor(final RuleExecutor ruleExecutor) {
		this.ruleExecutor = ruleExecutor;
	}

	public void setModelResolver(final ModelResolver modelResolver) {
		Objects.requireNonNull(modelResolver, "The model resolver may not be null");
		this.modelResolver = modelResolver;
	}

}
