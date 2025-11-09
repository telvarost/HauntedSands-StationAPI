package com.github.telvarost.hauntedsands.mixin;

import com.github.telvarost.hauntedsands.Config;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.feature.OreFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static com.github.telvarost.hauntedsands.events.init.BlockListener.*;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {

    @Shadow private Random random;

    @Shadow private World world;

    @Inject(
            method = "decorate",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/World;method_1781()Lnet/minecraft/world/biome/source/BiomeSource;",
                    ordinal = 1
            ),
            cancellable = true
    )
    public void hauntedSands_decorateUnderground(ChunkSource source, int x, int z, CallbackInfo ci) {
        if (Config.config.TERRAIN_GENERATION_CONFIG.enableCaveGunpowderGeneration) {
            int var4 = x * 16;
            int var5 = z * 16;
            Biome biomeToDecorate = this.world.method_1781().getBiome(var4 + 16, var5 + 16);
            int var14;
            int var15;
            int var16;

            if (  Biome.FOREST != biomeToDecorate
               && Biome.PLAINS != biomeToDecorate
               && Biome.TUNDRA != biomeToDecorate
            ) {
                var14 = var4 + this.random.nextInt(16);
                var15 = 4 + this.random.nextInt(16) + this.random.nextInt(32);
                var16 = var5 + this.random.nextInt(16);
                (new OreFeature(GUNPOWDER_CUBE.id, this.random.nextInt(24))).generate(this.world, this.random, var14, var15, var16);
            }
        }
    }
}
