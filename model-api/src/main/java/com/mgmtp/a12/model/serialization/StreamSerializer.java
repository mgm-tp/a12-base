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
package com.mgmtp.a12.model.serialization;

import com.mgmtp.a12.model.Model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * Provides deserialization from {@link InputStream}s and serialization to {@link OutputStream}s.
 */
public abstract class StreamSerializer<T extends Model> {

	/**
	 * Serializes the given {@link Model} to the given {@link OutputStream}.
	 *
	 * @param model the {@link Model} to serialize, may not be null
	 * @param outputStream the {@link OutputStream} to use, may not be null
	 */
	public void serialize(final T model, final OutputStream outputStream) {
		try (final PrintWriter writer = new PrintWriter(outputStream, true, StandardCharsets.UTF_8)) {
			writer.write(getStringSerializer().serialize(model));
		}
	}

	/**
	 * Deserializes the content of the {@link InputStream} to create a {@link Model}.
	 *
	 * @param inputStream the source to read from, may not be null
	 * @return the {@link Model} that was read from the {@link InputStream}
	 * @throws ModelSerializationException in case of errors trying to create the {@link Model}
	 */
	public T deserialize(final InputStream inputStream) throws ModelSerializationException {
		try {
			return getStringSerializer().deserialize(readString(inputStream));
		} catch (final IOException e) {
			throw new ModelSerializationException("Could not read from stream", e);
		}
	}

	public abstract StringSerializer<T> getStringSerializer();

	private String readString(final InputStream inputStream) throws IOException {
		final ByteArrayOutputStream into = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		for (int n; 0 < (n = inputStream.read(buffer)); ) {
			into.write(buffer, 0, n);
		}
		into.close();
		return into.toString(StandardCharsets.UTF_8);
	}
}
