package com.github.telvarost.hauntedsands.events.init;

import com.github.telvarost.hauntedsands.Config;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.CraftingRecipeManager;
import net.minecraft.recipe.ShapedRecipe;
import net.modificationstation.stationapi.api.event.recipe.RecipeRegisterEvent;
import net.modificationstation.stationapi.api.recipe.CraftingRegistry;
import net.modificationstation.stationapi.api.recipe.SmeltingRegistry;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.List;

import static com.github.telvarost.hauntedsands.events.init.BlockListener.*;

public class RecipeListener {

    @EventListener
    public void registerRecipes(RecipeRegisterEvent event) {
        Identifier type = event.recipeId;

        if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPED.type()) {
            List<CraftingRecipe> recipes = CraftingRecipeManager.getInstance().getRecipes();

            for (int i = 0; i < recipes.size(); i++) {
                CraftingRecipe recipe = recipes.get(i);
                int recipeItemId = recipe.getOutput().itemId;

                if (recipeItemId == Block.GLOWSTONE.asItem().id) {
                    if (Config.config.RECIPE_CONFIG.replaceGlowstoneRecipeWithGlowstoneSand) {
                        ItemStack[] inputArray = new ItemStack[4];
                        inputArray[0] = new ItemStack(Item.GLOWSTONE_DUST, 1);
                        inputArray[1] = new ItemStack(Item.GLOWSTONE_DUST, 1);
                        inputArray[2] = new ItemStack(Item.GLOWSTONE_DUST, 1);
                        inputArray[3] = new ItemStack(Item.GLOWSTONE_DUST, 1);
                        recipes.set(i, new ShapedRecipe(2, 2, inputArray, new ItemStack(GLOWSTONE_SAND.asItem(), 1)));
                    }
                }
            }

            if (Config.config.RECIPE_CONFIG.enableRedstoneSandCraftingRecipe) {
                CraftingRegistry.addShapedRecipe(new ItemStack(REDSTONE_SAND.asItem(), 1), "XX", "XX", 'X', Item.REDSTONE);
            }

            if (Config.config.RECIPE_CONFIG.enableSugarCubeCraftingRecipe) {
                CraftingRegistry.addShapedRecipe(new ItemStack(SUGAR_CUBE.asItem(), 1), "XX", "XX", 'X', Item.SUGAR);
            }

            if (Config.config.RECIPE_CONFIG.enableGunpowderCubeCraftingRecipe) {
                CraftingRegistry.addShapedRecipe(new ItemStack(GUNPOWDER_CUBE.asItem(), 1), "XX", "XX", 'X', Item.GUNPOWDER);
            }
        }

        if (type == RecipeRegisterEvent.Vanilla.SMELTING.type()) {
            if (Config.config.RECIPE_CONFIG.replaceGlowstoneRecipeWithGlowstoneSand) {
                SmeltingRegistry.addSmeltingRecipe(new ItemStack(GLOWSTONE_SAND.asItem(), 1), new ItemStack(Block.GLOWSTONE.asItem(), 1));
            }
        }

        if (type == RecipeRegisterEvent.Vanilla.CRAFTING_SHAPELESS.type()) {
            if (Config.config.RECIPE_CONFIG.enableGlowstoneSandDecraftingRecipe) {
                CraftingRegistry.addShapelessRecipe(new ItemStack(Item.GLOWSTONE_DUST, 4), GLOWSTONE_SAND.asItem());
            }

            if (Config.config.RECIPE_CONFIG.enableRedstoneSandDecraftingRecipe) {
                CraftingRegistry.addShapelessRecipe(new ItemStack(Item.REDSTONE, 4), REDSTONE_SAND.asItem());
            }

            if (Config.config.RECIPE_CONFIG.enableSugarCubeDecraftingRecipe) {
                CraftingRegistry.addShapelessRecipe(new ItemStack(Item.SUGAR, 4), SUGAR_CUBE.asItem());
            }

            if (Config.config.RECIPE_CONFIG.enableGunpowderCubeDecraftingRecipe) {
                CraftingRegistry.addShapelessRecipe(new ItemStack(Item.GUNPOWDER, 4), GUNPOWDER_CUBE.asItem());
            }
        }
    }
}
