package org.opencdmp.depositbase.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.opencdmp.commonmodels.enums.EnumUtils;
import org.opencdmp.commonmodels.enums.EnumValueProvider;

import java.util.Map;

public enum DepositType implements EnumValueProvider<Short> {
	SystemDeposit((short) 0), UserDeposit((short) 1), BothWaysDeposit((short) 2);

	private final Short value;

	DepositType(Short value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public Short getValue() {
		return value;
	}

	private static final Map<Short, DepositType> map = EnumUtils.getEnumValueMap(DepositType.class);

	public static DepositType of(Short i) {
		return map.get(i);
	}
}

