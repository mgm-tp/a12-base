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

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Base class for model specific {@link BatchConverter}s.
 */
public abstract class BatchConverter<T extends Model> {

	private final StreamSerializer<T> deserializer;
	private final StreamSerializer<T> serializer;

	protected BatchConverter(final StreamSerializer<T> deserializer, final StreamSerializer<T> serializer) {
		Objects.requireNonNull(deserializer);
		Objects.requireNonNull(serializer);

		this.deserializer = deserializer;
		this.serializer = serializer;
	}

	protected void process(final String[] args) throws IOException {
		if (args.length == 0 || args.length % 2 != 0) {
			throw new IllegalArgumentException("Usage: " + getClass().getSimpleName() + " {<infile> <outfile>}+");
		}

		System.out.println("starting serialization of " + (args.length / 2) + " models");
		final long startTime = System.currentTimeMillis();

		for (int i = 0; i < args.length; i += 2) {
			final T model = deserializer.deserialize(Files.newInputStream(Path.of(args[i])));
			final Path outPath = Path.of(args[i + 1]).normalize();
			if (outPath != null) {
				final Path parent = outPath.getParent();
				if (parent == null) {
					throw new IllegalArgumentException("Path '" + outPath.toString() + "' should have a parent.");
				} else {
					Files.createDirectories(parent);
				}
				try (final OutputStream out = Files.newOutputStream(outPath)) {
					System.out.println("writing " + args[i] + " to " + outPath);
					serializer.serialize(model, out);
				}
			} else {
				throw new IllegalArgumentException("Path for argument '" + args[i + 1] + "' is null");
			}
		}

		System.out.println("generation took " + (System.currentTimeMillis() - startTime) + "ms");

	}
}
