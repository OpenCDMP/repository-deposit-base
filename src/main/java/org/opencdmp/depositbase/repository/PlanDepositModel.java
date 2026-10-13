package org.opencdmp.depositbase.repository;


import org.opencdmp.commonmodels.models.deposit.PreviewFieldValue;
import org.opencdmp.commonmodels.models.deposit.PreviewGroupValuesModel;
import org.opencdmp.commonmodels.models.deposit.StoredPreviewFieldModel;
import org.opencdmp.commonmodels.models.plan.PlanModel;
import org.opencdmp.commonmodels.models.plugin.PluginUserFieldModel;

import java.util.List;

public class PlanDepositModel {

    private PlanModel planModel;
    private AuthInfo authInfo;
    private List<PreviewFieldValue> editedFields;
    private List<PreviewGroupValuesModel> editedGroups;
    private List<StoredPreviewFieldModel> storedFields;
    private List<PreviewGroupValuesModel> storedGroups;

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

    public List<PreviewFieldValue> getEditedFields() {
        return editedFields;
    }

    public void setEditedFields(List<PreviewFieldValue> editedFields) {
        this.editedFields = editedFields;
    }

    /**
     * User-edited entries of repeatable preview groups, applied at deposit time
     * as whole-list replacements of the mapped arrays.
     */
    public List<PreviewGroupValuesModel> getEditedGroups() {
        return editedGroups;
    }

    public void setEditedGroups(List<PreviewGroupValuesModel> editedGroups) {
        this.editedGroups = editedGroups;
    }

    /**
     * Persisted overrides from an earlier deposit of the same plan line, supplied
     * on preview requests; plugins merge them via {@link DepositPreviewMergeHelper}.
     */
    public List<StoredPreviewFieldModel> getStoredFields() {
        return storedFields;
    }

    public void setStoredFields(List<StoredPreviewFieldModel> storedFields) {
        this.storedFields = storedFields;
    }

    public List<PreviewGroupValuesModel> getStoredGroups() {
        return storedGroups;
    }

    public void setStoredGroups(List<PreviewGroupValuesModel> storedGroups) {
        this.storedGroups = storedGroups;
    }
}
