package org.opencdmp.depositbase.repository;

import org.opencdmp.commonmodels.models.deposit.DepositPreviewModel;
import org.opencdmp.commonmodels.models.deposit.PreviewFieldOption;
import org.opencdmp.commonmodels.models.plan.PlanModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
     * @param planDepositModel contains plan model which is to be deposited and auth info structure needed for the authentication to the repository.
     *                              Auth info contains access token and plugin user fields.
     *                              if access token is null, another authentication mechanism is used for
     *                              that repository e.g. api token which will be fetched from the
     *                              repository's configuration. If repository don't support access token,
     *                              could use plugin user fields that contains configuration for authentication
     * @return a string representing the persistent digital object identifier (DOI)
     * @throws Exception if an error occurs while trying to deposit the plan
     */
    @PostMapping()
    String deposit(@RequestBody PlanDepositModel planDepositModel);

    /**
     * Returns a preview descriptor of the metadata that would be deposited for the given
     * plan: a form description of the target repository's deposit fields with current
     * values prefilled by the same mapping the deposit uses, without submitting anything
     * to the repository. Only offered by the platform when the repository's configuration
     * declares supportsPreview.
     *
     * @param planDepositModel contains the plan model to preview; auth info is not required for previewing
     * @return the preview descriptor with field groups, field types and prefilled values
     */
    @PostMapping("/preview")
    default DepositPreviewModel preview(@RequestBody PlanDepositModel planDepositModel) {
        throw new UnsupportedOperationException("preview not supported");
    }

    /**
     * Searches the target repository's controlled vocabulary backing a preview
     * field (e.g. licenses, languages, communities). Only called for fields whose
     * descriptor declares vocabSearch.
     *
     * @param fieldCode the preview field code whose vocabulary to search
     * @param like optional user-typed filter term; null or blank returns top entries
     * @return matching options (label + value), ordered by relevance
     */
    @GetMapping("/vocab/{fieldCode}")
    default List<PreviewFieldOption> searchVocabulary(@PathVariable("fieldCode") String fieldCode, @RequestParam(value = "like", required = false) String like) {
        throw new UnsupportedOperationException("vocabulary search not supported");
    }

    /**
     * Returns the repository account that the given credentials belong to, so the platform can tell
     * users which account published a plan: a new version is usually accepted only from that account.
     * Called after a successful deposit with the same auth info and plan settings, without the plan's
     * files. Plugins that cannot tell may return null.
     *
     * @param planDepositModel the auth info and the plan's plugin settings the deposit used
     * @return the account's identifier and a name to show users, or null when unknown
     */
    @PostMapping("/account")
    default DepositAccount account(@RequestBody PlanDepositModel planDepositModel) {
        return null;
    }

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
