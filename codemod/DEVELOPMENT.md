# Base Codemod

## Dev

0. Install dependencies if necessary

1. Start watcher

```bash
node --run watch
```

2. Link binary (see https://pnpm.io/cli/link for linking/unlinking)

```bash
pnpm link -g
```

3. Run it (the name of the binary is defined by the `name` property, see https://docs.npmjs.com/cli/v11/configuring-npm/package-json#bin)

```bash
base-codemod --help
```

4. When changing source files, there is no need to re-link! The binary points to the compiled JS, which will stay up-to-date because of the watcher.

## Structure

- `cli.ts` is the entry point
- `recipes/` directory includes one file per recipe
- `test/` directory contains test files and snapshots
- `testData/` directory contains input data for tests (note that this directory is not compiled)

### Add new recipes

1. Add new file under `recipes/`
2. Export a single constant
3. Add that constant to the array in `cli.ts`

For implementing the logic:

1. Open AST Explorer: https://ts-ast-viewer.com
2. Enter the statements you want to migrate and the expected results
3. Look at the Nodes on the right to identify what to "use"

## Tests

Tests are written as snapshot tests and executed with the builtin Node Test Runner.

Testing works by setting up a `Project` that uses a very small tsconfig that "looks" at specific input files only.
Then the action of the recipe is called with that project.
Instead of calling `.save()` after (which would overwrite the input files), `file.print()` is then used to perform snapshot tests (comparing the file contents as strings.)

### Add tests for new recipe

1. Create a new file under `src/test`, e.g. `myNewRecipe.test.ts`
2. Add necessary test data files under `src/testData`, including at least one `tsconfig.json` that "sees" at least one file.
3. Create a new snapshot file by running `node --run test:updateSnapshots`
4. IMPORTANT: Review the created file to make sure it matches your expectations!
5. Finally, running `node --run test` should succeed.

## Debugging

`launch.json` contains a convenience configuration to debug test data. To make it work, following assumptions are made:

1. For each recipe, there exists a directory under `src/testData` with the same name
2. This directory includes at least one `tsconfig.json` file

When starting, VSCode will prompt for

* the specific recipe to execute (can be selected from a list)
* the name of the specific `tsconfig.json` file to use (can be entered as string)

With this setup, breakpoints can be set in the TS sources of the recipe as usual.

Note: Since the codemod is executed as a node script, you can always add `--inspect-brk` manually, e.g. like this: 
`node --inspect-brk ./lib/cli.js <recipe> <any tsconfig>` (make sure to run the watcher before).


