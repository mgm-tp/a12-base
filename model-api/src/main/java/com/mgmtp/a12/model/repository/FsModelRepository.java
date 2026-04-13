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
package com.mgmtp.a12.model.repository;

import com.mgmtp.a12.model.Model;
import com.mgmtp.a12.model.serialization.ModelSerializationException;
import com.mgmtp.a12.model.serialization.ModelTypeIdentifier;
import com.mgmtp.a12.model.serialization.SerializerFactory;
import org.apache.commons.lang3.Validate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * Default implementation of {@link ModelRepository} for file system use cases.
 * </p>
 * <p>
 * This implementation assumes that the model name is also used as a file name.
 * </p>
 */
public class FsModelRepository implements ModelRepository {

	private final Path repositoryRoot;
	private final String fileExtension;
	private final SerializerFactory serializerFactory;
	private final ModelTypeIdentifier modelTypeIdentifier;
	private final Map<String, Path> modelToFileNameMap = new HashMap<>();

	public FsModelRepository(
		final Path root,
		final String fileExtension,
		final SerializerFactory serializerFactory,
		final ModelTypeIdentifier modelTypeIdentifier) {

		Objects.requireNonNull(root);
		Validate.notBlank(fileExtension);
		Objects.requireNonNull(serializerFactory);
		Objects.requireNonNull(modelTypeIdentifier);

		this.repositoryRoot = root;
		this.fileExtension = fileExtension;
		this.serializerFactory = serializerFactory;
		this.modelTypeIdentifier = modelTypeIdentifier;
	}

	@Override
	public void create(final Model item) throws FaultException {
		Objects.requireNonNull(item);

		String modelId = item.getHeader().getId();
		try {
			findById(modelId);
			throw new FaultException(
				String.format(
					"Cannot create file for model '%s'. The model file already exists.",
					modelId));

		} catch (final ResourceAccessException rae) {
			// the exception is expected because the model should not be persisted yet when calling create
			final Path filePath = getUniqueFilePath(modelId);
			storeModelToPath(item, filePath, true);
		}
	}

	@Override
	public Model findById(final String id) throws ResourceAccessException, ModelSerializationException {
		Validate.notBlank(id);

		final Model result;
		if (modelToFileNameMap.containsKey(id)) {
			result = retrieveModelFromFile(id, modelToFileNameMap.get(id));

		} else {
			result = getModelFromRepositoryCollection(id);
		}
		return result;
	}

	@Override
	public Collection<Model> findAll() {

		final ArrayList<Model> result = new ArrayList<>();

		readAllFilesFromRoot(result);

		return result;
	}

	@Override
	public void save(final Model item) throws FaultException {
		Objects.requireNonNull(item);

		String modelId = item.getHeader().getId();
		final Path modelFilePath = getPathForFileAction(
			modelId,
			String.format("Error trying to save model '%s'. The model must be created first.", modelId));

		storeModelToPath(item, modelFilePath, false);
	}

	@Override
	public void delete(final String id) throws FaultException {
		Validate.notBlank(id);

		final Path modelFilePath =
			getPathForFileAction(
				id,
				String.format("Error trying to delete model '%s'. The model file does not exist.", id));

		deleteModelFileFromPath(id, modelFilePath);
	}

	private Path getUniqueFilePath(final String modelId) {
		final String initialModelFileName = getModelFileName(modelId, fileExtension);
		Path filePath = repositoryRoot.resolve(initialModelFileName);
		int counter = 2;

		while (Files.exists(filePath)) {
			filePath = repositoryRoot.resolve(getModelFileName(modelId + counter++, fileExtension));
		}
		return filePath;
	}

	static String getModelFileName(final String id, final String fileExtension) {
		return String.format("%s.%s", FileNameUtil.getFileNameForModel(id), fileExtension);
	}

	private void storeModelToPath(final Model item, final Path modelFilePath, final boolean create) {
		final StandardOpenOption[] openOptions = create
			? new StandardOpenOption[] { StandardOpenOption.CREATE_NEW }
			: new StandardOpenOption[] { StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING };

		try (final BufferedWriter bufferedWriter =
			Files.newBufferedWriter(modelFilePath, StandardCharsets.UTF_8, openOptions)) {

			bufferedWriter.write(serializerFactory.create(item.getClass()).serialize(item));

			if (create) {
				modelToFileNameMap.put(item.getHeader().getId(), modelFilePath);
			}
		} catch (final IOException e) {
			throw new FaultException(
				String.format("Error trying to create file '%s'.", modelFilePath.toString()), e);
		}
	}

	private Model retrieveModelFromFile(final String modelName, final Path modelFilePath) {
		final Model result;
		try (final BufferedReader bufferedReader = Files.newBufferedReader(modelFilePath, StandardCharsets.UTF_8)) {

			final String fileContent = readWholeContent(bufferedReader);
			final Class<? extends Model> modelType = modelTypeIdentifier.identifyModelType(fileContent);

			result = getModelFromFileContent(fileContent, modelType);

		} catch (final IOException e) {
			throw new ResourceAccessException(
				modelName,
				String.format("Could not read file '%s'", modelFilePath.toString()),
				e);
		}
		return result;
	}

	private Model getModelFromFileContent(final String fileContent, final Class<? extends Model> modelType) {
		return serializerFactory.create(modelType).deserialize(fileContent);
	}

	private Model getModelFromRepositoryCollection(final String id) {
		return findAll().stream()
			.filter(model -> model.getHeader().getId().equals(id))
			.findAny()
			.orElseThrow(
				() -> new ResourceAccessException(id, String.format("A model named '%s' does not exist.", id)));
	}

	private void readAllFilesFromRoot(final ArrayList<Model> result) {
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(repositoryRoot)) {
			for (final Path path : directoryStream) {
				if (Files.isRegularFile(path) && path.toString().endsWith(fileExtension)) {
					try (final BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

						final String fileContent = readWholeContent(bufferedReader);
						final Class<? extends Model> modelType;
						try {
							modelType = modelTypeIdentifier.identifyModelType(fileContent);

						} catch (final ModelSerializationException me) {
							// ignore this file, it could be something totally unrelated
							continue;
						}

						final Model model = getModelFromFileContent(fileContent, modelType);
						result.add(model);
						modelToFileNameMap.put(model.getHeader().getId(), path);

					} catch (final IOException ioe) {
						throw new FaultException(
							String.format("Error trying to read file '%s'", path.toString()),
							ioe);
					}
				}
			}
		} catch (final IOException ioe) {
			throw new FaultException(
				String.format("Error trying to access list of files in '%s'", repositoryRoot.toString()),
				ioe);
		}
	}

	private String readWholeContent(final BufferedReader reader) throws IOException {
		final StringBuilder result = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}

	private Path getPathForFileAction(final String id, final String errorMessage) {
		final Path modelFilePath;
		if (modelToFileNameMap.containsKey(id)) {
			modelFilePath = modelToFileNameMap.get(id);
		} else {
			// find all models and update modelToFileNameMap, then look again
			findAll();
			if (modelToFileNameMap.containsKey(id)) {
				modelFilePath = modelToFileNameMap.get(id);
			} else {
				throw new FaultException(errorMessage);
			}
		}
		return modelFilePath;
	}

	private void deleteModelFileFromPath(final String id, final Path modelFilePath) {
		if (Files.exists(modelFilePath)) {
			try {
				Files.delete(modelFilePath);
			} catch (final IOException ioe) {
				throw new FaultException(
					String.format("Error trying to delete model file '%s'.", modelFilePath.toString()), ioe);
			}
		} else {
			throw new FaultException(
				String.format(
					"Cannot delete model '%s'. The file '%s' does not exist.",
					id,
					modelFilePath.toString()));
		}
	}
}
