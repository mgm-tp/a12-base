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
package com.mgmtp.a12.model.serialization;


import com.mgmtp.a12.model.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * Provides creation of the default JSON {@link StringSerializer}s for different {@link Model} types.
 */
public class DefaultJsonSerializationFactory implements JsonSerializationFactory {

	private final Map<Class<? extends Model>, JsonSerializer> typeToSerializerMap = new HashMap<>();

	/**
	 * Can introduce or overwrite a {@link JsonSerializer} for its respective {@link Model} type.
	 *
	 * @param type - the type of {@link Model} to use the serializer for
	 * @param serializer - the {@link JsonSerializer} to use when a {@link Model} should be (de-)serialized.
	 */
	public void addSerializerForType(final Class<? extends Model> type, final JsonSerializer serializer) {
		Objects.requireNonNull(type);
		Objects.requireNonNull(serializer);

		typeToSerializerMap.put(type, serializer);
	}

	@Override
	public JsonSerializer create(final Class clazz) {
		JsonSerializer jsonSerializer = findDefaultSerializer(clazz);

		if (jsonSerializer == null) {
			jsonSerializer = findCustomSerializer(clazz);
		}

		if (jsonSerializer == null) {
			throw new UnsupportedOperationException(
				String.format(
					"JSON serialization is not yet supported by the factory for models of type'%s' yet.",
					clazz.getSimpleName()));
		}
		return jsonSerializer;
	}

	private JsonSerializer findDefaultSerializer(final Class clazz) {
		for (final ModelSerializationProvider provider : ServiceLoader.load(ModelSerializationProvider.class)) {
			if (provider.supports(clazz)) {
				return provider.createJsonSerializer();
			}
		}
		return null;
	}

	private JsonSerializer findCustomSerializer(final Class clazz) {
		if (typeToSerializerMap.containsKey(clazz)) {
			return typeToSerializerMap.get(clazz);
		}
		return null;
	}

}
