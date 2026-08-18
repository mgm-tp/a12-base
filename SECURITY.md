# Security Policy
Thank you for your interest in the security of the A12 Platform, maintained by [mgm technology partners GmbH](https://www.mgm-tp.com/).

The security of our products and your data is of utmost importance to us. This SECURITY.md` describes our **policies, procedures, and contact points regarding security issues**.

## Security Patches
We regularly scan our code for potential vulnerabilities and we also actively monitor the 3rd party libraries used in A12 for known vulnerabilities and apply patches if required. Please note, a vulnerability labeled as critical does not necessarily pose a critical risk for A12 or A12-based projects, i.e. we assess the issues and determine their severity in our context. For highly critical issues, we provide patches as soon as possible. For others, we provide patches in fixed cycles.

A12 Platform [patch releases](https://geta12.com/#/releases/policies) require coordination across our components to ensure that changes are applied consistently, e.g. updates of 3rd party libraries, and to allow for end-to-end tests. Therefore, we have established a rhythm of security patch rounds every second month resulting in a complete platform release, which is covered by automated end-to-end tests, and which includes a corresponding update of the Modeling Environment provided via the Installer. For details check our [Releases Overview](https://geta12.com/#/releases/releases-overview).

The two-month releases are named by the release line and the suffix _-ext_, followed by a incremental number, e.g. 2025.06-ext5. They sometimes fall together with other bug fixes or minor feature implementation.
**It is strongly recommended to update to every _-ext_ release quickly and continuously!**
Only this way, you can benefit from the security maintenance provided by the A12 team.

## Reporting a Vulnerability
If you have discovered a potential security vulnerability in this project, **please report it as soon as possible**. We strongly encourage responsible disclosure and pledge to respond promptly.

### How to report:
-   Please do not open a public GitHub issue for security vulnerabilities. Instead, submit all security reports via our ticket system at [support.mgm-tp.com](https://support.mgm-tp.com). Further instructions are available on [GetA12.com](https://geta12.com/) or in [SUPPORT.md](SUPPORT.md).
-   Include as much information as possible to help us understand and reproduce the issue (component name, affected versions, a description, reproduction steps, and your contact details).
-   Please do **not** publicly disclose vulnerabilities before we have had a chance to review and address them.

### Our Commitment
Upon receiving your report, we will:
- Acknowledge the receipt of your report within two business days.
- Provide updates on the progress of our investigation.
- Strive for a swift and responsible resolution.
- Credit you for your discovery, should you desire, in the relevant release notes (subject to coordination and agreement).
- Aim to resolve and disclose confirmed vulnerabilities within 90 days of the initial report.

### Non-Issues
We do not accept security reports regarding the following security issues:
- Missing or wrongly configured security headers
- Vulnerabilities in dependencies without a clear, comprehensible proof of exploitation in the context of the A12 Platform
- Any vulnerabilities related to development-only or demonstration-only functionality (e.g. the local authentication)
- Clear-text credentials in test setups
- Sensitive data in log messages at levels more verbose than `INFO`
- Log injection (default behavior in logback)

**We also do not accept automatically generated reports, either by security scanners or AI tools.**

## Security Notifications
Security advisories and updates will be published in the [documentation](https://geta12.com/) and relevant project release notes.

## security.txt
For automated security contact discovery, please refer to our [security.txt](https://geta12.com/.well-known/security.txt), which mirrors the information provided in this document according to the latest [RFC 9116](https://www.rfc-editor.org/rfc/rfc9116.html) standards.

## Further Information
- General project documentation, including further information on secure configuration and operation, is available at https://geta12.com/#/docs/2025.06/ext5/overall/security.
- Contact: [mgm technology partners GmbH](https://www.mgm-tp.com/) | [Imprint](https://www.mgm-tp.com/imprint.html)
