package org.cyclops.commoncapabilities.ingredient;

import org.cyclops.commoncapabilities.api.ingredient.IIngredientMatcher;
import org.cyclops.cyclopscore.helper.Helpers;
import org.cyclops.cyclopscore.helper.L10NHelpers;

/**
 * Exact matcher for a void match condition.
 * @author rubensworks
 */
public class IngredientMatcherEnergy implements IIngredientMatcher<Integer, Boolean> {
    @Override
    public boolean isInstance(Object object) {
        return object instanceof Integer;
    }

    @Override
    public Boolean getAnyMatchCondition() {
        return false;
    }

    @Override
    public Boolean getExactMatchCondition() {
        return true;
    }

    @Override
    public Boolean getExactMatchNoQuantityCondition() {
        return false;
    }

    @Override
    public Boolean withCondition(Boolean matchCondition, Boolean with) {
        return matchCondition || with;
    }

    @Override
    public Boolean withoutCondition(Boolean matchCondition, Boolean without) {
        return matchCondition == without ? false : matchCondition;
    }

    @Override
    public boolean hasCondition(Boolean matchCondition, Boolean searchCondition) {
        return matchCondition == searchCondition;
    }

    @Override
    public boolean matches(Integer a, Integer b, Boolean matchCondition) {
        return !matchCondition || a.intValue() == b.intValue();
    }

    @Override
    public Integer getEmptyInstance() {
        return 0;
    }

    @Override
    public boolean isEmpty(Integer instance) {
        return instance == 0;
    }

    @Override
    public int hash(Integer instance) {
        return instance;
    }

    @Override
    public Integer copy(Integer instance) {
        return instance;
    }

    @Override
    public long getQuantity(Integer instance) {
        return instance;
    }

    @Override
    public Integer withQuantity(Integer instance, long quantity) {
        return Helpers.castSafe(quantity);
    }

    @Override
    public long getMaximumQuantity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int conditionCompare(Boolean a, Boolean b) {
        return (a ? 1 : 0) - (b ? 1 : 0);
    }

    @Override
    public String localize(Integer instance) {
        return L10NHelpers.localize("recipecomponent.minecraft.energy");
    }

    @Override
    public int compare(Integer o1, Integer o2) {
        return o1 - o2;
    }
}
