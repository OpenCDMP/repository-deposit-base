package org.opencdmp.depositbase.repository;

import org.opencdmp.commonmodels.enums.PreviewFieldSource;
import org.opencdmp.commonmodels.models.deposit.DepositPreviewModel;
import org.opencdmp.commonmodels.models.deposit.PreviewField;
import org.opencdmp.commonmodels.models.deposit.PreviewFieldGroup;
import org.opencdmp.commonmodels.models.deposit.PreviewFieldValue;
import org.opencdmp.commonmodels.models.deposit.PreviewGroupItemModel;
import org.opencdmp.commonmodels.models.deposit.PreviewGroupValuesModel;
import org.opencdmp.commonmodels.models.deposit.StoredPreviewFieldModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Applies persisted preview overrides onto a freshly built preview descriptor.
 * Shared by all deposit plugins so the three-way merge semantics are identical
 * everywhere:
 * <ul>
 *     <li>Manual fields: the stored value always prefills — it has no plan source.</li>
 *     <li>Mapped fields, mapping output unchanged since the edit: the stored value
 *     is reapplied and the field is flagged storedValueApplied so clients resubmit it.</li>
 *     <li>Mapped fields, mapping output changed: the fresh mapped value wins and the
 *     stored value is surfaced as previousValue for a client-side hint.</li>
 *     <li>Repeatable groups: the stored items replace the mapped items only when the
 *     mapping still produces the stored base snapshot.</li>
 * </ul>
 * Plugins call this after building the descriptor, before returning a preview.
 */
public final class DepositPreviewMergeHelper {

    private DepositPreviewMergeHelper() {
    }

    public static void merge(DepositPreviewModel preview, List<StoredPreviewFieldModel> storedFields, List<PreviewGroupValuesModel> storedGroups) {
        if (preview == null || preview.getGroups() == null) return;

        Map<String, PreviewField> fieldIndex = new HashMap<>();
        Map<String, PreviewFieldGroup> groupIndex = new HashMap<>();
        indexGroups(preview.getGroups(), fieldIndex, groupIndex);

        if (storedFields != null) {
            for (StoredPreviewFieldModel stored : storedFields) {
                if (stored == null || stored.getValue() == null || stored.getValue().getCode() == null) continue;
                PreviewField field = fieldIndex.get(stored.getValue().getCode());
                if (field == null || field.getSource() == PreviewFieldSource.System) continue;

                if (field.getSource() == PreviewFieldSource.Manual) {
                    field.setValue(stored.getValue());
                    field.setStoredValueApplied(true);
                } else if (valuesEqual(field.getBaseValue(), stored.getBase())) {
                    field.setValue(stored.getValue());
                    field.setStoredValueApplied(true);
                } else {
                    field.setPreviousValue(stored.getValue());
                }
            }
        }

        if (storedGroups != null) {
            for (PreviewGroupValuesModel stored : storedGroups) {
                if (stored == null || stored.getGroupCode() == null) continue;
                PreviewFieldGroup group = groupIndex.get(stored.getGroupCode());
                if (group == null || !group.isRepeatable()) continue;

                if (itemsEqual(group.getBaseItems(), stored.getBaseItems())) {
                    group.setItems(stored.getItems());
                    group.setStoredItemsApplied(true);
                }
            }
        }
    }

    private static void indexGroups(List<PreviewFieldGroup> groups, Map<String, PreviewField> fieldIndex, Map<String, PreviewFieldGroup> groupIndex) {
        for (PreviewFieldGroup group : groups) {
            if (group == null) continue;
            if (group.getCode() != null) groupIndex.put(group.getCode(), group);
            if (group.getFields() != null && !group.isRepeatable()) {
                for (PreviewField field : group.getFields()) {
                    if (field != null && field.getCode() != null) fieldIndex.put(field.getCode(), field);
                }
            }
            if (group.getGroups() != null) indexGroups(group.getGroups(), fieldIndex, groupIndex);
        }
    }

    public static boolean valuesEqual(PreviewFieldValue left, PreviewFieldValue right) {
        if (left == null && right == null) return true;
        if (left == null || right == null) return isEmpty(left == null ? right : left);
        return Objects.equals(normalize(left.getTextValue()), normalize(right.getTextValue()))
                && Objects.equals(normalizeList(left.getTextListValue()), normalizeList(right.getTextListValue()))
                && Objects.equals(left.getBooleanValue(), right.getBooleanValue())
                && Objects.equals(left.getNumberValue(), right.getNumberValue())
                && Objects.equals(left.getDateValue(), right.getDateValue());
    }

    private static boolean itemsEqual(List<PreviewGroupItemModel> left, List<PreviewGroupItemModel> right) {
        List<PreviewGroupItemModel> l = left != null ? left : List.of();
        List<PreviewGroupItemModel> r = right != null ? right : List.of();
        if (l.size() != r.size()) return false;
        for (int i = 0; i < l.size(); i++) {
            if (!itemEqual(l.get(i), r.get(i))) return false;
        }
        return true;
    }

    private static boolean itemEqual(PreviewGroupItemModel left, PreviewGroupItemModel right) {
        List<PreviewFieldValue> l = left != null && left.getValues() != null ? left.getValues() : List.of();
        List<PreviewFieldValue> r = right != null && right.getValues() != null ? right.getValues() : List.of();

        Map<String, PreviewFieldValue> rightByCode = new HashMap<>();
        for (PreviewFieldValue value : r) {
            if (value != null && value.getCode() != null) rightByCode.put(value.getCode(), value);
        }
        List<PreviewFieldValue> nonEmptyLeft = new ArrayList<>();
        for (PreviewFieldValue value : l) {
            if (value != null && value.getCode() != null && !isEmpty(value)) nonEmptyLeft.add(value);
        }
        int nonEmptyRight = 0;
        for (PreviewFieldValue value : rightByCode.values()) {
            if (!isEmpty(value)) nonEmptyRight++;
        }
        if (nonEmptyLeft.size() != nonEmptyRight) return false;
        for (PreviewFieldValue value : nonEmptyLeft) {
            if (!valuesEqual(value, rightByCode.get(value.getCode()))) return false;
        }
        return true;
    }

    private static boolean isEmpty(PreviewFieldValue value) {
        if (value == null) return true;
        return normalize(value.getTextValue()) == null
                && normalizeList(value.getTextListValue()) == null
                && value.getBooleanValue() == null
                && value.getNumberValue() == null
                && value.getDateValue() == null;
    }

    private static String normalize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }

    private static List<String> normalizeList(List<String> values) {
        if (values == null) return null;
        List<String> normalized = new ArrayList<>();
        for (String value : values) {
            String item = normalize(value);
            if (item != null) normalized.add(item);
        }
        return normalized.isEmpty() ? null : normalized;
    }
}
