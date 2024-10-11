# Repository Deposit Base for OpenCDMP

**repository-deposit-base** is a Maven package that provides the base interfaces and configuration classes required to implement repository deposit services for the **OpenCDMP** platform. Each repository deposit service is developed as a separate microservice and can be registered with OpenCDMP to support the deposition of **OpenCDMP Plans** into external repositories for **DOI assignment**.

## Overview

OpenCDMP supports the deposition of plans into external repositories to mint a persistent **Digital Object Identifier (DOI)** for each submission, making the plans easily citeable. This functionality is built using a flexible and extensible mechanism that allows developers to add new repository deposit services based on specific repository requirements.

The **repository-deposit-base** package provides the necessary Java interfaces and configuration classes to define and develop these repository deposit services.

## Key Interfaces and Classes

### 1. `DepositClient.java`

This interface defines the core operations that any repository deposit service must implement.

```java
public interface DepositClient {

    String deposit(PlanModel planDepositModel, String repositoryAccessToken) throws Exception;

    String authenticate(String code);

    DepositConfiguration getConfiguration();

    String getLogo();
}
```

- **deposit()**: Deposits a plan to the repository and returns the DOI.
- **authenticate()**: Authenticates with the repository using OAuth2 and returns the access token.
- **getConfiguration()**: Returns the configuration details of the repository.
- **getLogo()**: Returns the repository’s logo in base64 format, if available.

### 2. `DepositController.java`

This interface defines the API endpoints that the repository deposit service must implement to communicate with the OpenCDMP platform.

```java
public interface DepositController {

    @PostMapping()
    String deposit(@RequestBody PlanModel planDepositModel, @RequestParam("authToken") String authToken) throws Exception;

    @GetMapping("/authenticate")
    String authenticate(@RequestParam("authToken") String code);

    @GetMapping("/configuration")
    DepositConfiguration getConfiguration();

    @GetMapping("/logo")
    String getLogo();
}
```

- **deposit()**: Deposits a plan to the repository and returns the DOI.
- **authenticate()**: Authenticates with the repository using OAuth2 and returns the access token.
- **getConfiguration()**: Returns the repository's configuration details.
- **getLogo()**: Returns the repository’s logo if available.

### 3. `DepositConfiguration.java`

This class contains the configuration details for each repository deposit service, which the OpenCDMP platform reads and registers.

```java
public class DepositConfiguration {

    private DepositType depositType;
    private String repositoryId;
    private String accessToken;
    private String repositoryUrl;
    private String repositoryAuthorizationUrl;
    private String repositoryRecordUrl;
    private String repositoryAccessTokenUrl;
    private String repositoryClientId;
    private String repositoryClientSecret;
    private String redirectUri;
    private boolean useSharedStorage;
    private boolean hasLogo;
}
```

### Fields:
- **depositType**: Defines how the plan is deposited (system credentials, user credentials, or both).
- **repositoryId**: Unique identifier for the repository.
- **accessToken**: Token used for repository deposits (if applicable).
- **repositoryUrl**: URL of the repository.
- **repositoryAuthorizationUrl**: Authorization URL for OAuth2.
- **repositoryRecordUrl**: URL for the deposited plan records.
- **repositoryAccessTokenUrl**: Access token URL for OAuth2 flow.
- **repositoryClientId**: Client ID for OAuth2 flow.
- **repositoryClientSecret**: Client secret for OAuth2 flow.
- **redirectUri**: URI to redirect back to OpenCDMP after OAuth2 flow.
- **useSharedStorage**: Indicates if shared storage is used.
- **hasLogo**: Indicates if the repository has a logo.

## How to Create a Custom Repository Deposit Service

To implement a custom repository deposit service for OpenCDMP:

1. **Create a New Spring Boot Project**:
   - Use **Spring Boot** to create a microservice that implements the `DepositClient` and `DepositController` interfaces.

2. **Define Your Repository Configuration**:
   - Define the repository-specific configuration in the `DepositConfiguration` class.

3. **Use Existing Implementations as Examples**:
   - You can refer to the `repository-deposit-zenodo` project that are part of the OpenCDMP platform as examples.

5. **Register the Service**:
   - Once your service is implemented and running, register it with the OpenCDMP platform. It will then be available as a repository deposit option for plans.

## Example

An example of an implementation is available in the `repository-deposit-zenodo` repository, which handles the deposition of DMPs into the **Zenodo** repository for DOI assignment.

## License

This package is licensed under the [EUPL 1.2 License](LICENSE).

## Contact

For questions or support regarding the implementation of repository deposit services, please contact:

- **Email**: opencdmp at cite.gr
