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

import type { LocalizedModelText } from "@com.mgmtp.a12.utils/utils-localization/lib/main/index.js";

/**
 * Contains common header information for all types of A12 models.
 */
export interface Header {
	/**
	 * The unique identifier of the model
	 */
	readonly id: string;

	/**
	 * The name of the meta model, e.g. application, document, form, overview, relationship, tree
	 */
	readonly modelType: ModelType;

	/**
	 * The version of the meta model
	 */
	readonly modelVersion: string;

	/**
	 * All supported locales of the model
	 */
	readonly locales?: { readonly code: string }[];

	/**
	 * All labels of the model
	 *
	 * A label of a model is a text that can be used to display the model to users for a given locale
	 */
	readonly labels?: LocalizedModelText;

	/**
	 * Optional human-readable description of the model
	 *
	 * Can be used to provide additional information about the model,
	 * e.g. its usage, content, purpose, change history, etc.
	 */
	readonly description?: string;

	/**
	 * All annotations of the model
	 *
	 * An annotation is a key value pair of strings which can contain any project specific extension.
	 */
	readonly annotations?: Annotation[];

	/**
	 * All references to other models
	 */
	readonly modelReferences?: ModelReference[];
}

export type ModelType =
	| "application"
	| "additive-document"
	| "combination"
	| "content"
	| "document"
	| "form"
	| "mapping"
	| "module-masterdetail"
	| "overview"
	| "print"
	| "relationship"
	| "structuralmapping"
	| "tree"
	| (string & {});

/**
 * A model reference contains information about references of a model to other models.
 */
export interface ModelReference {
	/**
	 * A string containing what the references is used for
	 */
	readonly purpose?: string;

	/**
	 * A string containing what (meta) model type is referenced
	 * e.g. document, form, overview, application, tree, relationship
	 */
	readonly modelType: ModelType;

	/**
	 * A string which can be used inside of the model to refer to this model reference
	 */
	readonly alias?: string;

	/**
	 * The actual reference to the other model
	 */
	readonly reference: string;
}

/**
 * Additional (project-specific) information attached to a model for customization purposes
 */
export interface Annotation {
	/**
	 * The identifier of the annotation
	 */
	readonly name: string;

	/**
	 * Optional value of the annotation
	 */
	readonly value?: string;
}
