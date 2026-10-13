package org.opencdmp.depositbase.repository;

import org.opencdmp.commonmodels.models.ConfigurationField;
import org.opencdmp.commonmodels.models.semantics.SemanticsManifest;
import org.opencdmp.depositbase.enums.DepositAuthMethod;
import org.opencdmp.depositbase.enums.DepositFileKind;
import org.opencdmp.depositbase.enums.DepositType;

import java.util.List;

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
    private boolean supportsPreview;
    private List<ConfigurationField> configurationFields;
    private List<ConfigurationField> userConfigurationFields;
    /** The semantic keys the plugin reads, for editors to show what it can do with a template or blueprint; optional. */
    private SemanticsManifest semantics;
    private List<DepositAuthMethod> authMethods;
    /** The files the plugin uploads; the platform builds only these. Empty means all of them. */
    private List<DepositFileKind> uploadedFiles;
    /** The scope the platform asks for when users sign in to the repository (oAuth2Flow). */
    private String oauthScope;

    public List<DepositFileKind> getUploadedFiles() {
        return uploadedFiles;
    }
    public void setUploadedFiles(List<DepositFileKind> uploadedFiles) {
        this.uploadedFiles = uploadedFiles;
    }

    public String getOauthScope() {
        return oauthScope;
    }
    public void setOauthScope(String oauthScope) {
        this.oauthScope = oauthScope;
    }

    public DepositType getDepositType() {
        return depositType;
    }
    public void setDepositType(DepositType depositType) {
        this.depositType = depositType;
    }

    public boolean isSupportsPreview() {
        return supportsPreview;
    }
    public void setSupportsPreview(boolean supportsPreview) {
        this.supportsPreview = supportsPreview;
    }

    public String getRepositoryId() {
        return repositoryId;
    }
    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }
    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getRepositoryAuthorizationUrl() {
        return repositoryAuthorizationUrl;
    }
    public void setRepositoryAuthorizationUrl(String repositoryAuthorizationUrl) {
        this.repositoryAuthorizationUrl = repositoryAuthorizationUrl;
    }

    public String getRepositoryRecordUrl() {
        return repositoryRecordUrl;
    }
    public void setRepositoryRecordUrl(String repositoryRecordUrl) {
        this.repositoryRecordUrl = repositoryRecordUrl;
    }

    public String getRepositoryAccessTokenUrl() {
        return repositoryAccessTokenUrl;
    }
    public void setRepositoryAccessTokenUrl(String repositoryAccessTokenUrl) {
        this.repositoryAccessTokenUrl = repositoryAccessTokenUrl;
    }

    public String getRepositoryClientId() {
        return repositoryClientId;
    }
    public void setRepositoryClientId(String repositoryClientId) {
        this.repositoryClientId = repositoryClientId;
    }

    public String getRepositoryClientSecret() {
        return repositoryClientSecret;
    }
    public void setRepositoryClientSecret(String repositoryClientSecret) {
        this.repositoryClientSecret = repositoryClientSecret;
    }

    public String getRedirectUri() {
        return redirectUri;
    }
    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public boolean isHasLogo() {
        return hasLogo;
    }
    public void setHasLogo(boolean hasLogo) {
        this.hasLogo = hasLogo;
    }

    public boolean isUseSharedStorage() {
        return useSharedStorage;
    }

    public void setUseSharedStorage(boolean useSharedStorage) {
        this.useSharedStorage = useSharedStorage;
    }

    public List<ConfigurationField> getConfigurationFields() {
        return configurationFields;
    }

    public void setConfigurationFields(List<ConfigurationField> configurationFields) {
        this.configurationFields = configurationFields;
    }

    public List<ConfigurationField> getUserConfigurationFields() {
        return userConfigurationFields;
    }

    public void setUserConfigurationFields(List<ConfigurationField> userConfigurationFields) {
        this.userConfigurationFields = userConfigurationFields;
    }

    public List<DepositAuthMethod> getAuthMethods() {
        return authMethods;
    }

    public void setAuthMethods(List<DepositAuthMethod> authMethods) {
        this.authMethods = authMethods;
    }

    public SemanticsManifest getSemantics() {
        return semantics;
    }

    public void setSemantics(SemanticsManifest semantics) {
        this.semantics = semantics;
    }
}
