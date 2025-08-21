package org.opencdmp.depositbase.repository;


import org.opencdmp.commonmodels.models.plan.PlanModel;
import org.opencdmp.commonmodels.models.plugin.PluginUserFieldModel;

import java.util.List;

public class PlanDepositModel {

    private PlanModel planModel;
    private AuthInfo authInfo;

    public PlanModel getPlanModel() {
        return planModel;
    }

    public void setPlanModel(PlanModel planModel) {
        this.planModel = planModel;
    }

    public AuthInfo getAuthInfo() {
        return authInfo;
    }

    public void setAuthInfo(AuthInfo authInfo) {
        this.authInfo = authInfo;
    }
}
