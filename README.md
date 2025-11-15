# HauntedSands StationAPI for Minecraft Beta 1.7.3

A StationAPI mod for Minecraft Beta 1.7.3 that adds new sand types, graves, and lost souls.
- Special thanks to DRAGON-STUDIO and HorrorSoundsAsWav over on Pixabay for the mob sounds!
- Credit to K1W1XD and Mimihanne for the skins used to make the lost soul entity.
- Mod works on Multiplayer with [GlassConfigAPI](https://modrinth.com/mod/glass-config-api) version 3.0+ used to sync configs!
  - Falling sand blocks are currently broken on multiplayer sadly as StationAPI does not support it yet.
- All features can be enabled/disabled through [GlassConfigAPI](https://modrinth.com/mod/glass-config-api) as well.

### Terrain Generation Features

* Allows overworld generation of rare pockets of gunpowder in caves
  * Generates between Y levels 4 and 52
  * Generates 0 to 23 gunpowder cubes
  * Does not generate in Forest, Plains, or Tundra biomes
  * Often will catch fire immediately from lava and explode on generation
    * Explosion is not strong enough to break stone
* Allows nether generation of stripes of redstone sand
  * Generates below Y level 80
  * Generates 0 to 63 redstone sand
* Allows nether generation of small flat stars of glowstone sand
  * Generates at all Y levels
  * Generates 0 to 13 glowstone sand

### Sand/Cube Features

* Glowstone Sand
  * Glows like glowstone and falls like sand!
  * When crafting is enabled glowstone sand is made like vanilla glowstone and glowstone is made by smelting the sand
  * Soil Properties:
    * Cactus and Sugar Cane can be planted and grow on it
    * Dead bushes can be planted on it (they will not grow)
* Redstone Sand
  * Emits a redstone signal when it lands
  * Soil Properties:
    * Dead bushes and small plants can be planted on it (they will not grow)
* Sugar Cube
  * Dissolves when it falls into water or lava
  * Saddled pigs can eat the sugar cube by walking on it
  * Soil Properties:
    * Sugar Cane can be planted and grow on it
    * Dead bushes and small plants can be planted on it (they will not grow)
* Gunpowder Cube
  * Explodes when it falls
  * Highly flammable
  * Soil Properties:
    * Cactus can be planted and grow on it
    * Dead bushes can be planted on it (they will not grow)

### Other Features

* Ability to create a lost soul on player death that is wearing the dead player's armor
  * Lost soul is a new flying mob that can no clip through blocks
  * The lost soul attacks by charging the player
    * Charge speed increases as the mob gets closer to their player target
    * Lost souls can charge a second time if they are in a solid opaque block
  * You can trap lost souls in translucent blocks like tree leaves, ice, glass, snow layers, tall grass, etc.
    * If you are having trouble with lost souls the surface is your friend!
  * Lost souls drop stolen armor on death along with a locked chest (used in the Grave crafting recipe)
  * No lost soul will be created if player inventory and armor slots are empty
* Ability to create a grave on player death that contains main inventory items (including hotbar)
  * The grave will not contain armor or modded slot items
  * Grave vanishes on use and items are dropped (a permanent version can be crafted, see recipe below)
  * Grave is blast resistant to creepers
    * A creeper standing on it may still be able to blow it up (usually only happens with slab graves)
  * Grave will be a slab if less than half or half the main inventory slots are filled
    * Grave slabs combine using beta slab logic
  * Grave will be a full block (Columbarium) if more than half the main inventory slots are filled
  * No grave will be created if player main inventory is empty
* Pigs will now seek out sugar and brown mushrooms when they are in item form
  * Pigs will eat the sugar healing them 0.5 hearts if they are hurt
  * The pigs are rather finicky and sometimes won't seek out or do anything
    * You can try to push/prod them to get them moving again, but it won't always work
  * Saddled pigs also have a slight pathing preference for sugar cube and brown mushroom blocks (not noticeable in small quantities)
    * Saddled pigs lose their slight pathing preference for grass blocks since they are now seeking the other blocks

### Crafting Recipes

All crafting recipes can be turned on/off from the mod config menu.
* All sands/cubes are crafted by placing 4 of the correct item in a square.
  * If enabled sands/cubes can be de-crafted back into 4 of the item used to make them. (default disabled)
* Graves can be crafted with soul sand, any vanilla slab, and either a regular chest or a locked chest.
  * Locked chests are a vanilla block that is normally unobtainable but will drop when a lost soul mob is killed.

![sugar cube crafting recipe](https://github.com/telvarost/HauntedSands-StationAPI/blob/main/images/SugarCubeCraftingRecipe.png)
![sugar cube de-crafting recipe](https://github.com/telvarost/HauntedSands-StationAPI/blob/main/images/SugarCubeDecraftingRecipe.png)
![gunpowder cube crafting recipe](https://github.com/telvarost/HauntedSands-StationAPI/blob/main/images/GunpowderCubeCraftingRecipe.png)
![gunpowder cube de-crafting recipe](https://github.com/telvarost/HauntedSands-StationAPI/blob/main/images/GunpowderCubeDecraftingRecipe.png)
![redstone sand crafting recipe](https://github.com/telvarost/HauntedSands-StationAPI/blob/main/images/RedstoneSandCraftingRecipe.png)
![redstone sand de-crafting recipe](https://github.com/telvarost/HauntedSands-StationAPI/blob/main/images/RedstoneSandDecraftingRecipe.png)
![glowstone sand crafting recipe](https://github.com/telvarost/HauntedSands-StationAPI/blob/main/images/GlowstoneSandCraftingRecipe.png)
![glowstone sand de-crafting recipe](https://github.com/telvarost/HauntedSands-StationAPI/blob/main/images/GlowstoneSandDecraftingRecipe.png)
![glowstone sand smelting recipe](https://github.com/telvarost/HauntedSands-StationAPI/blob/main/images/GlowstoneSandSmeltingRecipe.png)
![grave crafting recipe](https://github.com/telvarost/HauntedSands-StationAPI/blob/main/images/GraveCraftingRecipe.png)

## Installation using Prism Launcher

1. Download an instance of Babric for Prism Launcher: https://github.com/Glass-Series/babric-prism-instance
2. Install Java 17 and set the instance to use it: https://adoptium.net/temurin/releases/
3. Add GlassConfigAPI 3.0.2+ to the mod folder for the instance: https://modrinth.com/mod/glass-config-api
4. Add Glass Networking to the mod folder for the instance: https://modrinth.com/mod/glass-networking
5. Add StationAPI to the mod folder for the instance: https://modrinth.com/mod/stationapi
6. (Optional) Add Mod Menu to the mod folder for the instance: https://modrinth.com/mod/modmenu-beta
7. Add this mod to the mod folder for the instance: https://github.com/telvarost/HauntedSands-StationAPI/releases
8. Run and enjoy! 👍

## Feedback

Got any suggestions on what should be added next? Feel free to share it by [creating an issue](https://github.com/telvarost/HauntedSands-StationAPI/issues/new). Know how to code and want to do it yourself? Then look below on how to get started.

## Contributing

Thanks for considering contributing! To get started fork this repository, make your changes, and create a PR. 

If you are new to StationAPI consider watching the following videos on Babric/StationAPI Minecraft modding: https://www.youtube.com/watch?v=9-sVGjnGJ5s&list=PLa2JWzyvH63wGcj5-i0P12VkJG7PDyo9T
