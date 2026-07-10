<picture>
  <source media="(prefers-color-scheme: dark)" srcset="https://www.mgm-tp.com/global-content/cd/logos/a12/app-icons/dark/A12-Dark.svg" />
  <img src="https://www.mgm-tp.com/global-content/cd/logos/a12/app-icons/light/A12-Light.svg" height="200" alt="A12 logo" />
</picture>

# Base

This repository contains basic A12 libraries with APIs and low-level functionality.

Refer to https://geta12.com/#/docs to get started with A12 development

---

## License

Parts of the A12 platform are made available under a **dual license**.
Please check the [LICENSE](./LICENSE) file for details.

---

## Packages Documentation

### Java

- base-bom - BOM dependency for all Java base modules
- fs-model-repository - default filesystem implementation for the model repository API
- model-api - generic model interfaces
- model-consistency - model consistency check API
- model-marshalling - model marshalling
- model-utils - dependency-less utils for A12 modules

### TS

- model-api-ts - generic model interfaces

### Other

- documentation - developer documentation and TypeDoc

## Getting Started

### How to Use It

To install the latest Java dependency, add them to you `dependencies` section in your build.gradle.

```groovy
dependencies {
	implementation 'com.mgmtp.a12.base:base-model-api'
}
```

To install the latest npm package, use npm/pnpm:

```sh
npm install @com.mgmtp.a12.base/model-api-ts
```

### How to Build and Run

This repository uses [Gradle] as the leading build tool. The following tasks
can be executed in the repository root or in individual folders to build specific
sub-projects. In the repository root, gradle tasks include TypeScript projects.

#### Prerequisites

The following tools are required in order to build this repository.

| Tool     | Version |
| -------- | ------- |
| [JDK]    | `^25`   |
| [Gradle] | `^9`    |
| [Node]   | `^24`   |
| [pnpm]   | `^10.6` |

#### How to Build

```sh
gradle assemble
```

#### How to Test

```sh
gradle check
```

#### Cleaning Build Artifacts

```sh
gradle clean
```

#### Code Quality

```sh
gradle verify
```

#### How to Run

There are no applications or showcases to execute.

---

### Documentation

- Full technical documentation is available at [GetA12.com](https://GetA12.com).
- The website also provides access to the **A12 Discourse Community Forum**.

---

**The mgm A12 Team**

[mgm technology partners GmbH](https://www.mgm-tp.com) • [Imprint](https://www.mgm-tp.com/imprint.html)

<!-- References -->

[JDK]: https://www.oracle.com/technetwork/java/javase/overview/index.html
[Gradle]: https://docs.gradle.org/
[Node]: https://nodejs.org/en/docs/
[pnpm]: https://pnpm.io/motivation
