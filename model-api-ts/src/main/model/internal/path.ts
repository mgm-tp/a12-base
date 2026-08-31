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

/**
 * A path to an element in a model.
 * Each path segment contains the name of the element.
 *
 * Example:
 * A model contains the element GroupA on root level.
 * The model element GroupA contains the element Field1 as child.
 * The model path looks like [{elementName: "GroupA"}, {elementName: "Field1"}]
 */
export type ModelPath = {
	readonly elementName: string;
}[];

/**
 * Utility functions for the {@link ModelPath}.
 */
export const ModelPath = {
	/**
	 * Function to compare two {@link ModelPath}s.
	 * @returns true if the given paths are equal
	 *
	 * Example 1:
	 * The path [{elementName: "GroupA"}, {elementName: "Field1"}] is
	 * equal to [{elementName: "GroupA"}, {elementName: "Field1"}].
	 *
	 * Example 2:
	 * The path [{elementName: "GroupA"}, {elementName: "Field1"}] is not
	 * equal to [{elementName: "GroupA"}, {elementName: "GroupB"}].
	 */
	equal(p1: ModelPath, p2: ModelPath): boolean {
		return p1.length === p2.length && p1.every((p1e, i) => p1e.elementName === p2[i].elementName);
	},

	/**
	 * Function to create a {@link ModelPath} from string.
	 * @param str path as string. The path segments are separated with "/".
	 * The string may only end with "/", if it is the only character.
	 * @returns The created {@link ModelPath}
	 *
	 * Example:
	 * The path for the string "/GroupA/Field1" is [{elementName: "GroupA"}, {elementName: "Field1"}].
	 */
	fromString(str: string): ModelPath {
		const normalizedIdentifier = str.startsWith("/") ? str.slice(1) : str;
		return normalizedIdentifier.length > 0
			? normalizedIdentifier.split("/").map(elementName => {
					return { elementName };
				})
			: [];
	},

	/**
	 * Function to convert a {@link ModelPath} to a string.
	 * @returns The model path as string. If the model path is empty, "/" is returned.
	 *
	 * Example:
	 * The string representation for the path [{elementName: "GroupA"}, {elementName: "Field1"}] is "/GroupA/Field1".
	 */
	toString(path: ModelPath): string {
		return "/" + path.map(segment => segment.elementName).join("/");
	},

	/**
	 * @param path A path to a model element.
	 * @returns The path to the direct parent of the model element.
	 *
	 * Example:
	 * The parent path of [{elementName: "GroupA"}, {elementName: "Field1"}] is [{elementName: "GroupA"}].
	 */
	parentPath(path: ModelPath): ModelPath {
		return path.length > 0 ? path.slice(0, -1) : path;
	},

	/**
	 * Checks, if a model element is contained in another model element.
	 * @param p1 The path to the element which might be part of the second model element.
	 * @param p2 The path to the element which might contain the first model element.
	 *
	 * Example 1:
	 * p1 [{elementName: "Screen1"}, {elementName: "ControlGrid1"}]
	 * p2 [{elementName: "Screen1"}]
	 * Result: true - Screen1 contains ControlGrid1
	 *
	 * Example 2:
	 * p1 [{elementName: "Screen1"}, {elementName: "ControlGrid1"}, {elementName: "Control1"}]
	 * p2 [{elementName: "Screen1"}, {elementName: "ControlGrid2"}]
	 * Result: false - Control1 is a transitive child of Screen1, but not of ControlGrid2.
	 */
	contains(p1: ModelPath, p2: ModelPath): boolean {
		return p1.length >= p2.length && p2.every((p2e, i) => p2e.elementName === p1[i].elementName);
	}
};
