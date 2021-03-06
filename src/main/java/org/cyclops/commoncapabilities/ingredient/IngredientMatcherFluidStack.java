package org.cyclops.commoncapabilities.ingredient;

import com.google.common.collect.Maps;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.cyclops.commoncapabilities.api.capability.fluidhandler.FluidMatch;
import org.cyclops.commoncapabilities.api.ingredient.IIngredientMatcher;
import org.cyclops.cyclopscore.helper.Helpers;

import java.util.Map;

/**
 * Matcher for FluidStacks.
 * @author rubensworks
 */
public class IngredientMatcherFluidStack implements IIngredientMatcher<FluidStack, Integer> {

    private static final Map<Fluid, Integer> FLUID_ID_CACHE = Maps.newIdentityHashMap();

    @Override
    public boolean isInstance(Object object) {
        return object == null || object instanceof FluidStack;
    }

    @Override
    public Integer getAnyMatchCondition() {
        return FluidMatch.ANY;
    }

    @Override
    public Integer getExactMatchCondition() {
        return FluidMatch.EXACT;
    }

    @Override
    public Integer getExactMatchNoQuantityCondition() {
        return FluidMatch.FLUID | FluidMatch.NBT;
    }

    @Override
    public Integer withCondition(Integer matchCondition, Integer with) {
        return matchCondition | with;
    }

    @Override
    public Integer withoutCondition(Integer matchCondition, Integer without) {
        return matchCondition & ~without;
    }

    @Override
    public boolean hasCondition(Integer matchCondition, Integer searchCondition) {
        return (matchCondition & searchCondition) > 0;
    }

    @Override
    public boolean matches(FluidStack a, FluidStack b, Integer matchCondition) {
        return FluidMatch.areFluidStacksEqual(a, b, matchCondition);
    }

    @Override
    public FluidStack getEmptyInstance() {
        return null;
    }

    @Override
    public int hash(FluidStack instance) {
        if (instance == null) {
            return 0;
        }
        return instance.hashCode();
    }

    @Override
    public FluidStack copy(FluidStack instance) {
        if (instance == null) {
            return null;
        }
        return instance.copy();
    }

    @Override
    public long getQuantity(FluidStack instance) {
        if (instance == null) {
            return 0;
        }
        return instance.amount;
    }

    @Override
    public FluidStack withQuantity(FluidStack instance, long quantity) {
        if (instance == null) {
            return new FluidStack(FluidRegistry.WATER, Helpers.castSafe(quantity));
        }
        if (instance.amount == quantity) {
            return instance;
        }
        FluidStack copy = instance.copy();
        copy.amount = Helpers.castSafe(quantity);
        return copy;
    }

    @Override
    public long getMaximumQuantity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int conditionCompare(Integer a, Integer b) {
        return Integer.compare(a, b);
    }

    @Override
    public String localize(FluidStack instance) {
        return instance.getLocalizedName();
    }

    @Override
    public int compare(FluidStack o1, FluidStack o2) {
        if (o1 == null) {
            if (o2 == null) {
                return 0;
            } else {
                return -1;
            }
        } else if (o2 == null) {
            return 1;
        } else if (o1.getFluid() == o2.getFluid()) {
            if (o1.amount == o2.amount) {
                return IngredientHelpers.compareTags(o1.tag, o2.tag);
            }
            return o1.amount - o2.amount;
        }
        return getFluidId(o1.getFluid()) - getFluidId(o2.getFluid());
    }

    @SuppressWarnings("deprecation")
    public static int getFluidId(Fluid fluid) {
        // Lazy map initialization
        if (FLUID_ID_CACHE.isEmpty()) {
            FLUID_ID_CACHE.putAll(FluidRegistry.getRegisteredFluidIDs());
        }

        return FLUID_ID_CACHE.getOrDefault(fluid, -1);
    }
}
