# Repository Deposit Base for OpenCDMP

**repository-deposit-base** is a Maven package that provides the base interfaces and configuration classes required to implement repository deposit services for the **OpenCDMP** platform. Each repository deposit service is developed as a separate microservice and can be registered with OpenCDMP to support the deposition of **OpenCDMP Plans** into external repositories for **DOI assignment**.

---

## Overview

OpenCDMP supports the deposition of plans into external repositories to mint a persistent **Digital Object Identifier (DOI)** for each submission, making the plans easily citeable. This functionality is built using a flexible and extensible mechanism that allows developers to add new repository deposit services based on specific repository requirements.

The **repository-deposit-base** package provides the necessary Java interfaces and configuration classes to define and develop these repository deposit services.

**Documentation**: [Deposit Service Guide](https://opencdmp.github.io/developers/plugins/deposit/)

## Key Interfaces and Classes

For key interfaces check the documentation [here](https://opencdmp.github.io/developers/plugins/deposit/#key-interfaces-and-classes).

---

## How to Create a Custom Repository Deposit Service

To implement a custom repository deposit service for OpenCDMP:

[How to Create a Custom Repository Deposit Service](https://opencdmp.github.io/developers/plugins/deposit/#how-to-create-a-custom-repository-deposit-service)

1. **Create a New Spring Boot Project**:
   - Use **Spring Boot** to create a microservice that implements the `DepositClient` and `DepositController` interfaces.

2. **Define Your Repository Configuration**:
   - Define the repository-specific configuration in the `DepositConfiguration` class.

3. **Register the Service**:
   - Once your service is implemented and running, register it with the OpenCDMP platform. It will then be available as a repository deposit option for plans.

---

## Reference Implementations

You can refer to the [Zenodo](https://github.com/OpenCDMP/repository-deposit-zenodo), [Dataverse](https://github.com/OpenCDMP/repository-deposit-dataverse), [DSpace](https://github.com/OpenCDMP/repository-deposit-dspace), [CKAN](https://github.com/OpenCDMP/repository-deposit-ckan) and [Fedora](https://github.com/OpenCDMP/repository-deposit-fedora) deposit repositories that are part of the OpenCDMP platform as examples.

---

## Architecture Integration

Repository Deposit services integrate with OpenCDMP through:

1. **REST API**: Services expose endpoints defined by `DepositController`
2. **Service Registry**: OpenCDMP backend registers available deposit repositories via configuration
3. **Common Models**: Data exchange using shared models from `common-models` package
4. **Microservices**: Each deposit repository runs as an independent service

## Related Resources

- **Common Models**: [common-models repository](https://github.com/OpenCDMP/common-models)
- **Main Repository**: [OpenCDMP](https://github.com/OpenCDMP/OpenCDMP)
- **Deposits Overview**: https://opencdmp.github.io/optional-services/deposit-services
- **Developer Plugin Guide**: https://opencdmp.github.io/developers/plugins/deposit

---

## License

This package is licensed under the [EUPL 1.2 License](LICENSE).

---

## Contact

For questions or support regarding the implementation of repository deposit services, please contact:

- **Email**: opencdmp at cite.gr

---

*This package is part of the OpenCDMP ecosystem. For general OpenCDMP documentation, visit [opencdmp.github.io](https://opencdmp.github.io).*
