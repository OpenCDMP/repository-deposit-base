package org.opencdmp.depositbase.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import org.opencdmp.commonmodels.enums.EnumUtils;
import org.opencdmp.commonmodels.enums.EnumValueProvider;

import java.util.Map;

public enum DepositAuthMethod implements EnumValueProvider<Short> {
	PluginDefault((short) 0), AuthInfoFromUserProfile((short) 1), oAuth2Flow((short) 2);

	private final Short value;

	DepositAuthMethod(Short value) {
		this.value = value;
	}

	@Override
	@JsonValue
	public Short getValue() {
		return value;
	}

	private static final Map<Short, DepositAuthMethod> map = EnumUtils.getEnumValueMap(DepositAuthMethod.class);

	public static DepositAuthMethod of(Short i) {
		return map.get(i);
	}
}

