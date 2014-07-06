package modtweaker.mariculture;

import static modtweaker.util.Helper.FluidStack;
import static modtweaker.util.Helper.ItemStack;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeVat;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import minetweaker.api.liquid.ILiquidStack;
import modtweaker.util.BaseListAddition;
import modtweaker.util.BaseListRemoval;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mariculture.Vat")
public class Vat {
    //Adding a Mariculture Vat recipe
    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, ILiquidStack outputFluid, int time) {
        addRecipe(fluid1, fluid2, null, outputFluid, null, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, IItemStack outputItem, int time) {
        addRecipe(fluid1, fluid2, null, null, outputItem, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, ILiquidStack outputFluid, IItemStack outputItem, int time) {
        addRecipe(fluid1, fluid2, null, outputFluid, outputItem, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, IItemStack input, ILiquidStack outputFluid, int time) {
        addRecipe(fluid1, null, input, outputFluid, null, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, IItemStack input, IItemStack outputItem, int time) {
        addRecipe(fluid1, null, input, null, outputItem, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, IItemStack input, ILiquidStack outputFluid, IItemStack outputItem, int time) {
        addRecipe(fluid1, null, input, outputFluid, outputItem, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, IItemStack input, ILiquidStack outputFluid, int time) {
        addRecipe(fluid1, fluid2, input, outputFluid, null, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, IItemStack input, IItemStack outputItem, int time) {
        addRecipe(fluid1, fluid2, input, null, outputItem, time);
    }

    @ZenMethod
    public static void addRecipe(ILiquidStack fluid1, ILiquidStack fluid2, IItemStack input, ILiquidStack outputFluid, IItemStack outputItem, int time) {
        MineTweakerAPI.tweaker.apply(new AddRecipe(new RecipeVat(ItemStack(input), FluidStack(fluid1), FluidStack(fluid2), FluidStack(outputFluid), ItemStack(outputItem), time)));
    }

    //Passes the list to the base list implementation, and adds the recipe
    private static class AddRecipe extends BaseListAddition {
        public AddRecipe(RecipeVat recipe) {
            super("Mariculture Vat", MaricultureHandlers.vat.getRecipes(), recipe);
        }

        @Override
        public String getRecipeInfo() {
            if (((RecipeVat) recipe).outputItem != null) return ((RecipeVat) recipe).outputItem.getDisplayName();
            else return ((RecipeVat) recipe).outputFluid.getFluid().getLocalizedName();
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //Removing a Mariculture Vat recipe
    @ZenMethod
    public static void removeRecipe(IItemStack outputItem) {
        MineTweakerAPI.tweaker.apply(new RemoveRecipe(ItemStack(outputItem), null));
    }

    @ZenMethod
    public static void removeRecipe(ILiquidStack outputFluid) {
        MineTweakerAPI.tweaker.apply(new RemoveRecipe(null, FluidStack(outputFluid)));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack outputItem, ILiquidStack outputFluid) {
        MineTweakerAPI.tweaker.apply(new RemoveRecipe(ItemStack(outputItem), FluidStack(outputFluid)));
    }

    //Removes a recipe, apply is never the same for anything, so will always need to override it
    private static class RemoveRecipe extends BaseListRemoval {
        private final FluidStack fluid;

        public RemoveRecipe(ItemStack stack, FluidStack fluid) {
            super("Mariculture Vat", MaricultureHandlers.vat.getRecipes(), stack);
            this.fluid = fluid;
        }

        //Loops through the registry, to find the item that matches, saves that recipe then removes it
        @Override
        public void apply() {
            for (RecipeVat r : MaricultureHandlers.vat.getRecipes()) {
                if (r.outputItem != null && stack != null && r.outputItem.isItemEqual(stack)) {
                    if (r.outputFluid == null || (fluid != null && r.outputFluid.isFluidStackIdentical(fluid))) {
                        recipe = r;
                        break;
                    }
                }

                if ((r.outputFluid != null && fluid != null && r.outputFluid.isFluidStackIdentical(fluid))) {
                    recipe = r;
                    break;
                }
            }

            MaricultureHandlers.vat.getRecipes().remove(recipe);
        }

        @Override
        public String getRecipeInfo() {
            return stack != null ? stack.getDisplayName() : fluid.getFluid().getLocalizedName();
        }
    }
}