package org.opencdmp.depositbase.repository;

import org.opencdmp.commonmodels.enums.PlanUserRole;
import org.opencdmp.commonmodels.enums.PluginType;
import org.opencdmp.commonmodels.models.PlanUserModel;
import org.opencdmp.commonmodels.models.ConfigurationField;
import org.opencdmp.commonmodels.models.plan.PlanModel;
import org.opencdmp.commonmodels.models.plugin.PluginFieldModel;
import org.opencdmp.commonmodels.models.plugin.PluginModel;
import org.opencdmp.depositbase.enums.DepositFileKind;

import java.util.*;

/**
 * A deposit plugin's settings for one deposit: the defaults it declares in its configuration fields, overridden by the
 * values an organisation (tenant) or a blueprint set. The platform merges the tenant and blueprint values into the plan
 * blueprint's plugins before it calls the plugin. Blank values never override.
 */
public class DepositPluginSettings {

	/** Who users can publish under: {@link #PUBLISH_WAYS_BOTH}, {@link #PUBLISH_WAYS_ORGANISATION} or {@link #PUBLISH_WAYS_OWN}. Read by the platform. */
	public static final String PUBLISH_WAYS = "publish-ways";
	public static final String PUBLISH_WAYS_BOTH = "both";
	public static final String PUBLISH_WAYS_ORGANISATION = "organisation";
	public static final String PUBLISH_WAYS_OWN = "own";
	/** The choice selected when users may choose: {@link #PUBLISH_WAYS_OWN} or {@link #PUBLISH_WAYS_ORGANISATION}. Read by the platform. */
	public static final String PUBLISH_PRESELECTED = "publish-preselected";
	/** What users see as the organisation's account. Read by the platform. */
	public static final String ORGANISATION_ACCOUNT_NAME = "organisation-account-name";
	/** Review sections (preview group codes) never shown to users. */
	public static final String REVIEW_HIDDEN_SECTIONS = "review-hidden-sections";
	/** Settings (codes) whose value users cannot change in the review. */
	public static final String REVIEW_LOCKED = "review-locked";
	/** Which plan members are credited as authors/creators: {@link #AUTHORS_ALL}, {@link #AUTHORS_CONTRIBUTORS}, {@link #AUTHORS_OWNERS} or {@link #AUTHORS_NONE}. */
	public static final String AUTHORS_FROM_MEMBERS = "authors-from-members";
	public static final String AUTHORS_ALL = "all";
	public static final String AUTHORS_CONTRIBUTORS = "contributors";
	public static final String AUTHORS_OWNERS = "owners";
	public static final String AUTHORS_NONE = "none";
	/** Whether a public plan's OpenCDMP page is added to the record: "true" or "false". */
	public static final String LINK_PUBLIC_PLAN = "link-public-plan";
	/** The files to deposit (DepositFileKind names), a subset of what the plugin uploads. Read by the platform. */
	public static final String DEPOSIT_FILES = "deposit-files";

	private final Map<String, String> values = new HashMap<>();

	private DepositPluginSettings() {
	}

	public static DepositPluginSettings of(PlanModel planModel, DepositConfiguration configuration) {
		return of(planModel != null && planModel.getPlanBlueprint() != null && planModel.getPlanBlueprint().getDefinition() != null ? planModel.getPlanBlueprint().getDefinition().getPlugins() : null, configuration);
	}

	/** For callers that hold the organisation's plugin values themselves, such as the platform. */
	public static DepositPluginSettings of(List<PluginModel> plugins, DepositConfiguration configuration) {
		DepositPluginSettings settings = new DepositPluginSettings();
		if (configuration == null) return settings;
		if (configuration.getConfigurationFields() != null) {
			for (ConfigurationField field : configuration.getConfigurationFields()) {
				if (field.getCode() != null && !isBlank(field.getDefaultValue())) settings.values.put(field.getCode(), field.getDefaultValue());
			}
		}
		if (plugins != null) {
			plugins.stream()
					.filter(x -> Objects.equals(configuration.getRepositoryId(), x.getCode()) && PluginType.Deposit.equals(x.getType()) && x.getFields() != null)
					.findFirst()
					.ifPresent(plugin -> {
						for (PluginFieldModel field : plugin.getFields()) {
							if (field.getCode() != null && !isBlank(field.getTextValue())) settings.values.put(field.getCode(), field.getTextValue().trim());
						}
					});
		}
		return settings;
	}

	public static DepositPluginSettings of(PlanDepositModel model, DepositConfiguration configuration) {
		return of(model != null ? model.getPlanModel() : null, configuration);
	}

	/** The value, or null when neither the organisation nor the plugin's defaults set one. */
	public String text(String code) {
		return this.values.get(code);
	}

	public String text(String code, String fallback) {
		String value = this.values.get(code);
		return value != null ? value : fallback;
	}

	/** A list setting ("a, b=Renamed, c"): the codes, without their renames. */
	public List<String> list(String code) {
		String value = this.values.get(code);
		if (value == null) return List.of();
		List<String> items = new ArrayList<>();
		for (String part : value.split(",")) {
			String item = part.split("=", 2)[0].trim();
			if (!item.isEmpty()) items.add(item);
		}
		return items;
	}

	public boolean bool(String code, boolean fallback) {
		String value = this.values.get(code);
		return value == null ? fallback : Boolean.parseBoolean(value);
	}

	/** The plan members to credit, each once, as the organisation chose (all of them by default). */
	public List<PlanUserModel> authors(List<PlanUserModel> users) {
		if (users == null) return List.of();
		String choice = this.text(AUTHORS_FROM_MEMBERS, AUTHORS_ALL);
		if (AUTHORS_NONE.equals(choice)) return List.of();
		Set<Object> seen = new HashSet<>();
		List<PlanUserModel> authors = new ArrayList<>();
		for (PlanUserModel user : users) {
			if (user == null || user.getUser() == null) continue;
			if (AUTHORS_OWNERS.equals(choice) && !PlanUserRole.Owner.equals(user.getRole())) continue;
			if (AUTHORS_CONTRIBUTORS.equals(choice) && !PlanUserRole.Owner.equals(user.getRole()) && !PlanUserRole.DescriptionContributor.equals(user.getRole())) continue;
			Object key = user.getUser().getId() != null ? user.getUser().getId() : user.getUser();
			if (seen.add(key)) authors.add(user);
		}
		return authors;
	}

	public boolean linkPublicPlan() {
		return this.bool(LINK_PUBLIC_PLAN, true);
	}

	/** The files to deposit: the organisation's choice within what the plugin uploads, else all of those. */
	public List<DepositFileKind> depositFiles(List<DepositFileKind> uploaded) {
		List<DepositFileKind> declared = uploaded == null || uploaded.isEmpty() ? List.of(DepositFileKind.values()) : uploaded;
		List<String> chosen = this.list(DEPOSIT_FILES);
		if (chosen.isEmpty()) return declared;
		return declared.stream().filter(x -> chosen.contains(x.name())).toList();
	}

	public boolean isHidden(String sectionCode) {
		return this.list(REVIEW_HIDDEN_SECTIONS).contains(sectionCode);
	}

	public boolean isLocked(String settingCode) {
		return this.list(REVIEW_LOCKED).contains(settingCode);
	}

	private static boolean isBlank(String value) {
		return value == null || value.isBlank();
	}
}
