package org.opencdmp.depositbase.repository;

import org.opencdmp.commonmodels.models.plan.PlanModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The RepositoryDeposit interface represents the mechanism of depositing a plan to any
 * repository which mints a persistent digital object identifier (DOI) for each submission,
 * which makes the stored plans easily citeable.
 */
public interface DepositController {

    /**
     * Returns a string representing the persistent digital object identifier (DOI) which
     * was created.
     *
     * @param planDepositModel plan structure which is to be deposited
     * @param authToken access token needed for the authentication to the repository
     *                              if this is null, another authentication mechanism is used for
     *                              that repository e.g. api token which will be fetched from the
     *                              repository's configuration
     * @return a string representing the persistent digital object identifier (DOI)
     * @throws Exception if an error occurs while trying to deposit the plan
     */
    @PostMapping()
    String deposit(@RequestBody PlanModel planDepositModel, @RequestParam("authToken")String authToken) throws Exception;

    /**
     * Returns the access token from the oauth2 flow.
     *
     * @param code oauth2 authorization code
     * @return the access token or null if no oauth2 protocol is not supported for that repository
     */
    @GetMapping("/authenticate")
    String authenticate(@RequestParam("authToken") String code);

    /**
     * Returns the repository's configuration.
     *
     * @return List of structure representing the configuration of the repository <br>
     *         which includes the following fields: <br>
     *         depositType - an integer representing how the plan user can deposit in the repository,
     *         0 stands for system deposition meaning the plan is deposited using OpenCDMP credentials to the
     *         repository, 1 stands for user deposition in which the OpenCDMP user specifies his/her own credentials
     *         to the repository, 2 stands for both ways deposition if the repository allows the deposits of plans
     *         to be made from both OpenCDMP and users accounts<br>
     *         repositoryId - unique identifier for the repository<br>
     *         accessToken - access token provided for the system type deposits<br>
     *         repositoryUrl - repository url<br>
     *         repositoryAuthorizationUrl - repository's authorization url<br>
     *         repositoryRecordUrl - repository's record url, this url is used to index plans that are created<br>
     *         repositoryAccessTokenUrl - repository's access token url<br>
     *         repositoryClientId - repository's client id<br>
     *         repositoryClientSecret - repository's client secret<br>
     *         redirectUri - redirect uri to OpenCDMP after the oauth2 flow from the repository<br>
     */
    @GetMapping("/configuration")
    DepositConfiguration getConfiguration();

    /**
     * Returns the repository's logo if exists.
     *
     * @return the repository's logo in base64 form
     * */
    @GetMapping("/logo")
    String getLogo();

}
