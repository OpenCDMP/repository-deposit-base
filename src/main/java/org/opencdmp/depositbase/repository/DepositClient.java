package org.opencdmp.depositbase.repository;


import org.opencdmp.commonmodels.models.deposit.DepositPreviewModel;
import org.opencdmp.commonmodels.models.deposit.PreviewFieldOption;
import org.opencdmp.commonmodels.models.plan.PlanModel;

import java.util.List;

/**
 * The RepositoryDeposit interface represents the mechanism of depositing a plan to any
 * repository which mints a persistent digital object identifier (DOI) for each submission,
 * which makes the stored plans easily citeable.
 */
public interface DepositClient {

    /**
     * Returns a string representing the persistent digital object identifier (DOI) which
     * was created.
     *
     * @param planDepositModel contains plan model which is to be deposited and auth info structure needed for the authentication to the repository.
     *                              Auth info contains access token and plugin user fields.
     *                              if access token is null, another authentication mechanism is used for
     *                              that repository e.g. api token which will be fetched from the
     *                              repository's configuration. If repository don't support access token,
     *                              could use plugin user fields that contains configuration for authentication.
     *                              If edited fields are present, the plugin applies them on top of its own
     *                              mapping before submitting, so the deposited metadata reflects the user's
     *                              edits from the preview form
     * @return a string representing the persistent digital object identifier (DOI)
     */
    String deposit(PlanDepositModel planDepositModel);

    /**
     * Returns a preview descriptor of the metadata that would be deposited for the given
     * plan: a form description of the target repository's deposit fields with current
     * values prefilled by the same mapping the deposit uses, without submitting anything
     * to the repository. Only offered by the platform when the repository's configuration
     * declares supportsPreview; plugins that do not support previewing may throw
     * UnsupportedOperationException.
     *
     * @param planDepositModel contains the plan model to preview; auth info is not required for previewing
     * @return the preview descriptor with field groups, field types and prefilled values
     */
    default DepositPreviewModel preview(PlanDepositModel planDepositModel) {
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
    default List<PreviewFieldOption> searchVocabulary(String fieldCode, String like) {
        throw new UnsupportedOperationException("vocabulary search not supported");
    }

    /**
     * Returns the access token from the oauth2 flow.
     *
     * @param code oauth2 authorization code
     * @return the access token or null if no oauth2 protocol is not supported for that repository
     */
    String authenticate(String code);

    /**
     * Returns the repository's configuration.
     *
     * @return the configuration of the repository, which includes the following fields: <br>
     *         repositoryId - unique identifier for the repository<br>
     *         authMethods - how a deposit can sign in to the repository: with the plugin's own account (PluginDefault),
     *         with the credentials the user saved in their profile (AuthInfoFromUserProfile), or through the
     *         repository's OAuth2 sign-in (oAuth2Flow)<br>
     *         configurationFields, userConfigurationFields - the settings an organisation, or a user in their profile, can fill in<br>
     *         semantics - the semantic keys the plugin reads; optional<br>
     *         uploadedFiles - the files the plugin uploads; empty means all of them<br>
     *         oauthScope - the scope asked for in the OAuth2 sign-in<br>
     *         hasLogo, supportsPreview, useSharedStorage - optional capabilities<br>
     *         depositType and accessToken are older fields: authMethods replaces depositType, and the platform does not read accessToken<br>
     *         repositoryUrl - repository url<br>
     *         repositoryAuthorizationUrl - repository's authorization url<br>
     *         repositoryRecordUrl - repository's record url, this url is used to index plans that are created<br>
     *         repositoryAccessTokenUrl - repository's access token url<br>
     *         repositoryClientId - repository's client id<br>
     *         repositoryClientSecret - repository's client secret<br>
     *         redirectUri - redirect uri to OpenCDMP after the oauth2 flow from the repository<br>
     */
    DepositConfiguration getConfiguration();

    /**
     * Returns the repository's logo if exists.
     *
     * @return the repository's logo in base64 form
     * */
    String getLogo();

}
