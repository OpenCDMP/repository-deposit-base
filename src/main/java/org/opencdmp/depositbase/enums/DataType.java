package org.opencdmp.depositbase.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.opencdmp.commonmodels.enums.EnumUtils;
import org.opencdmp.commonmodels.enums.EnumValueProvider;

import java.util.Map;

public enum DataType implements EnumValueProvider<Short> {
    String((short) 0),
    Boolean((short) 1),
    Integer((short) 2),
    Long((short) 3),
    Double((short) 4);

    private final Short value;
    private static final Map<Short, DataType> map = EnumUtils.getEnumValueMap(DataType.class);

    private DataType(Short value) {
        this.value = value;
    }

    @JsonValue
    public Short getValue() {
        return this.value;
    }

    public static DataType of(Short i) {
        return (DataType)map.get(i);
    }
}
