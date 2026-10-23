# OpenCDMP repository deposit base

**repository-deposit-base** is the Maven package that defines the contract between [OpenCDMP](https://github.com/OpenCDMP/OpenCDMP) and its deposit services. A deposit service is a separate Spring Boot service that publishes OpenCDMP plans to an external repository, such as Zenodo or a DSpace instance, and returns the persistent identifier the repository assigns, typically a DOI, which OpenCDMP stores with the plan. OpenCDMP calls it over HTTP with the models of the [common-models](https://github.com/OpenCDMP/common-models) package.

The package holds the Java interfaces, configuration classes and helpers that every deposit service implements or uses. It contains no repository-specific code.

**Documentation**: [Deposit plugin guide](https://opencdmp.github.io/developers/plugins/deposit/)

## Requirements

- Java 21 or later
- Maven
- Spring Boot 4 for the deposit service, as in the reference implementations
- [common-models](https://github.com/OpenCDMP/common-models), which this package brings in as a dependency

## What the package contains

| Class | Purpose |
| --- | --- |
| `DepositClient` | The service-side contract: deposit a plan, build the metadata preview, search a controlled vocabulary, exchange an OAuth 2.0 code for a token, name the account a deposit used, and return the configuration and logo. |
| `DepositController` | The REST endpoints OpenCDMP calls under `/api/deposit` on the service's URL. Implement it in a `@RestController` mapped there. |
| `PlanDepositModel`, `AuthInfo` | The deposit request: the plan, the user's credentials, and the metadata edits made in the review. |
| `DepositConfiguration` | What the service reports to OpenCDMP: its repository id, how users can authenticate, which files it uploads, whether it supports the metadata review, the settings it offers to organisations and users, and the semantics it reads. |
| `DepositPluginSettings` | Reads the plugin's settings for one deposit from the plan: the defaults the plugin declares, overridden by the values the organisation set. |
| `DepositPreviewMergeHelper` | Merges metadata edits a user saved earlier into a fresh preview. |
| `DepositAccount` | The repository account a deposit used: an identifier and a name to show users. |
| `CustomFieldProperties`, enums | Supporting types: `DepositType`, `DepositAuthMethod`, `DepositFileKind` and `DataType`. |

### Endpoints

| Method | Path | Request body | Response |
| --- | --- | --- | --- |
| `POST` | `/api/deposit` | `PlanDepositModel` | the persistent identifier |
| `POST` | `/api/deposit/preview` | `PlanDepositModel` | `DepositPreviewModel` |
| `GET` | `/api/deposit/vocab/{fieldCode}?like=<term>` | - | list of `PreviewFieldOption` |
| `POST` | `/api/deposit/account` | `PlanDepositModel` without the plan's files | `DepositAccount`, or empty when the plugin can't tell (optional) |
| `GET` | `/api/deposit/authenticate?authToken=<code>` | - | the access token |
| `GET` | `/api/deposit/configuration` | - | `DepositConfiguration` |
| `GET` | `/api/deposit/logo` | - | the logo image, Base64-encoded |

The [plugin guide](https://opencdmp.github.io/developers/plugins/deposit/#key-interfaces-and-classes) describes each class, and [Deposit preview and metadata review](https://opencdmp.github.io/developers/plugins/deposit-preview/) the review contract.

## Use it in a deposit service

Add the dependency to your Spring Boot project, with the latest release from [Maven Central](https://central.sonatype.com/artifact/org.opencdmp/repository-deposit-base), and set the `opencdmp.plugin-base.version` property:

```xml
<dependency>
    <groupId>org.opencdmp</groupId>
    <artifactId>repository-deposit-base</artifactId>
    <version>${opencdmp.plugin-base.version}</version>
</dependency>
```

Then:

1. Implement `DepositClient` with the calls to your repository, and `DepositController` in a `@RestController` mapped at `/api/deposit`. Describe the service in a `DepositConfiguration`: its repository id, authentication methods, uploaded files and settings. See [How to create a custom deposit service](https://opencdmp.github.io/developers/plugins/deposit/#how-to-create-a-custom-deposit-service).
2. Accept only tokens of the installation's Keycloak realm that carry the `plugins` audience. See [Step 5: configure security and deploy](https://opencdmp.github.io/developers/plugins/deposit/#step-5--configure-security-and-deploy).
3. Try the service against a local installation. See [Run and test a plugin locally](https://opencdmp.github.io/developers/plugins/run-locally/).
4. Register it for the whole installation or for one organisation. See [Registering plugin services](https://opencdmp.github.io/deployment/configuration/plugin-services/).

### Reference implementations

| Repository | Repository it deposits to |
| --- | --- |
| [repository-deposit-zenodo](https://github.com/OpenCDMP/repository-deposit-zenodo) | Zenodo and other InvenioRDM instances |
| [repository-deposit-dataverse](https://github.com/OpenCDMP/repository-deposit-dataverse) | Dataverse |
| [repository-deposit-dspace](https://github.com/OpenCDMP/repository-deposit-dspace) | DSpace |
| [repository-deposit-ckan](https://github.com/OpenCDMP/repository-deposit-ckan) | CKAN |
| [repository-deposit-fedora](https://github.com/OpenCDMP/repository-deposit-fedora) | Fedora |

## Build from source

```bash
mvn clean install
```

This installs version `1.0.0-SNAPSHOT` in your local Maven repository, where a deposit service built on the same machine finds it. To install another version, set it first with `mvn versions:set -DnewVersion=<version>`.

## Related resources

- [Deposit plugin guide](https://opencdmp.github.io/developers/plugins/deposit/)
- [Deposit preview and metadata review](https://opencdmp.github.io/developers/plugins/deposit-preview/)
- [Common models](https://opencdmp.github.io/developers/plugins/common-models/) and the [common-models repository](https://github.com/OpenCDMP/common-models)
- [Registering plugin services](https://opencdmp.github.io/deployment/configuration/plugin-services/)
- [Deposit services overview](https://opencdmp.github.io/optional-services/deposit-services/)
- [OpenCDMP](https://github.com/OpenCDMP/OpenCDMP)

## Contributing

Report bugs and suggest changes in [GitHub Issues](https://github.com/OpenCDMP/repository-deposit-base/issues). See [Contributing](https://opencdmp.github.io/developers/contributing/) for how to propose a change.

## License

repository-deposit-base is licensed under the [European Union Public Licence (EUPL) v1.2](LICENSE).

## Contact

Questions: email opencdmp at cite.gr.
