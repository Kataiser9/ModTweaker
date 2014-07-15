package modtweaker.mods.mekanism.handlers;

import static modtweaker.helpers.InputHelper.toStack;
import static modtweaker.mods.mekanism.MekanismHelper.toGas;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IItemStack;
import modtweaker.mods.mekanism.gas.IGasStack;
import modtweaker.mods.mekanism.util.AddMekanismRecipe;
import modtweaker.mods.mekanism.util.RemoveMekanismRecipe;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.mekanism.chemical.Crystallizer")
public class ChemicalCrystallizer {
    @ZenMethod
    public static void addRecipe(IGasStack input, IItemStack output) {
        MineTweakerAPI.tweaker.apply(new AddMekanismRecipe("CHEMICAL_CRYSTALLIZER", toGas(input), toStack(output)));
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output) {
        MineTweakerAPI.tweaker.apply(new RemoveMekanismRecipe("CHEMICAL_CRYSTALLIZER", toStack(output)));
    }
}