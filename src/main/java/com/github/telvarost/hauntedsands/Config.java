package com.github.telvarost.hauntedsands;

import net.glasslauncher.mods.gcapi3.api.*;

public class Config {

    @ConfigRoot(value = "config", visibleName = "HauntedSands")
    public static ConfigFields config = new ConfigFields();

    public static class ConfigFields {

        @ConfigCategory(
                name = "Recipe Config",
                description = "Restart required for changes to take effect"
        )
        public RecipeConfig RECIPE_CONFIG = new RecipeConfig();

        @ConfigCategory(
                name = "Terrain Generation Config"
        )
        public TerrainGenerationConfig TERRAIN_GENERATION_CONFIG = new TerrainGenerationConfig();

        @ConfigEntry(
                name = "Chance On Break Soul Sand Spawns Lost Soul",
                description = "Float value between 0.0 (0%) and 1.0 (100%)",
                multiplayerSynced = true,
                maxLength = 1
        )
        public Float chanceBreakingSoulSandSpawnsLostSoul = 0.01F;

        @ConfigEntry(
                name = "Player Death Creates A Lost Soul Enemy",
                description = "Steals and wears the dead player's armor",
                multiplayerSynced = true
        )
        public Boolean playerDeathCreatesLostSoulEnemy = true;

        @ConfigEntry(
                name = "Player Death Creates Main Inventory Grave",
                description = "Excludes armor slots and modded slots",
                multiplayerSynced = true
        )
        public Boolean playerDeathCreatesMainInventoryGrave = true;

        @ConfigEntry(
                name = "Pigs Eat Sugar & Seek Brown Mushrooms",
                description = "Only for items; Does not work for blocks",
                multiplayerSynced = true
        )
        public Boolean pigsEatSugarAndSeekBrownMushrooms = true;
    }

    public static class RecipeConfig {

        @ConfigEntry(
                name = "Enable Grave Crafting Recipe",
                description = "Soul sand, chest, and a vanilla slab to craft",
                multiplayerSynced = true
        )
        public Boolean enableGraveCraftingRecipe = true;

        @ConfigEntry(
                name = "Enable Redstone Sand Crafting Recipe",
                description = "4 redstone in a square to craft",
                multiplayerSynced = true
        )
        public Boolean enableRedstoneSandCraftingRecipe = true;

        @ConfigEntry(
                name = "Enable Redstone Sand De-crafting Recipe",
                description = "Redstone Sand crafts back to 4 redstone",
                multiplayerSynced = true
        )
        public Boolean enableRedstoneSandDecraftingRecipe = true;

        @ConfigEntry(
                name = "Enable Sugar Cube Crafting Recipe",
                description = "4 sugar in a square to craft",
                multiplayerSynced = true
        )
        public Boolean enableSugarCubeCraftingRecipe = true;

        @ConfigEntry(
                name = "Enable Sugar Cube De-crafting Recipe",
                description = "Sugar Cube crafts back to 4 sugar",
                multiplayerSynced = true
        )
        public Boolean enableSugarCubeDecraftingRecipe = true;

        @ConfigEntry(
                name = "Enable Gunpowder Cube Crafting Recipe",
                description = "4 gunpowder in a square to craft",
                multiplayerSynced = true
        )
        public Boolean enableGunpowderCubeCraftingRecipe = true;

        @ConfigEntry(
                name = "Enable Gunpowder Cube De-crafting Recipe",
                description = "Gunpowder Cube crafts back to 4 gunpowder",
                multiplayerSynced = true
        )
        public Boolean enableGunpowderCubeDecraftingRecipe = true;

        @ConfigEntry(
                name = "Replace Glowstone Recipe With Glowstone Sand",
                description = "Smelt glowstone sand for glowstone",
                multiplayerSynced = true
        )
        public Boolean replaceGlowstoneRecipeWithGlowstoneSand = true;

        @ConfigEntry(
                name = "Set Glowstone Sand De-crafting Recipe",
                description = "Glowstone Sand crafts back to 4 dust",
                multiplayerSynced = true
        )
        public Boolean enableGlowstoneSandDecraftingRecipe = true;
    }

    public static class TerrainGenerationConfig {

        @ConfigEntry(
                name = "Enable Overworld Gunpowder Generation",
                description = "Some biomes generate gunpowder in caves",
                multiplayerSynced = true
        )
        public Boolean enableCaveGunpowderGeneration = true;

        @ConfigEntry(
                name = "Enable Nether Redstone Sand Generation",
                description = "Generates sand stripes below Y level 80",
                multiplayerSynced = true
        )
        public Boolean enableNetherRedstoneSandGeneration = true;

        @ConfigEntry(
                name = "Enable Nether Glowstone Sand Generation",
                description = "Generates a bit of sand at all Y levels",
                multiplayerSynced = true
        )
        public Boolean enableNetherGlowstoneSandGeneration = true;
    }
}
