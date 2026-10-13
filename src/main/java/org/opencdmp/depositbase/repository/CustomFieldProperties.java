package org.opencdmp.depositbase.repository;

import org.opencdmp.depositbase.enums.DataType;

import java.util.List;

public class CustomFieldProperties {

    private List<CustomField> customFields;

    public List<CustomField> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }

    public static class CustomField {

        private String code;

        private String jPath;

        private DataType dataType;

        private boolean isArray;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getjPath() {
            return jPath;
        }

        public void setjPath(String jPath) {
            this.jPath = jPath;
        }

        public DataType getDataType() {
            return dataType;
        }

        public void setDataType(DataType dataType) {
            this.dataType = dataType;
        }

        public boolean getIsArray() {
            return isArray;
        }

        public void setIsArray(boolean isArray) {
            this.isArray = isArray;
        }
    }

}
