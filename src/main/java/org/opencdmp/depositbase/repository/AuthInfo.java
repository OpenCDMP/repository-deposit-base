package org.opencdmp.depositbase.repository;


import org.opencdmp.commonmodels.models.plugin.PluginUserFieldModel;

import java.util.List;

public class AuthInfo {

    private String authToken;
    private List<PluginUserFieldModel> authFields;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public List<PluginUserFieldModel> getAuthFields() {
        return authFields;
    }

    public void setAuthFields(List<PluginUserFieldModel> authFields) {
        this.authFields = authFields;
    }
}
