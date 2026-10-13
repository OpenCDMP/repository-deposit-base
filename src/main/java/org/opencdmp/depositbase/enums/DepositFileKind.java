package org.opencdmp.depositbase.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.opencdmp.commonmodels.enums.EnumUtils;
import org.opencdmp.commonmodels.enums.EnumValueProvider;

import java.util.Map;

public enum DepositFileKind implements EnumValueProvider<Short> {
	PlanDocument((short) 0), RdaJson((short) 1), SupportingFiles((short) 2);

	private final Short value;

	DepositFileKind(Short value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public Short getValue() {
		return value;
	}

	private static final Map<Short, DepositFileKind> map = EnumUtils.getEnumValueMap(DepositFileKind.class);

	public static DepositFileKind of(Short i) {
		return map.get(i);
	}
}
