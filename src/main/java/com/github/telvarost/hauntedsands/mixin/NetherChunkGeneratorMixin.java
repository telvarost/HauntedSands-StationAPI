package com.github.telvarost.hauntedsands.mixin;

import com.github.telvarost.hauntedsands.Config;
import com.github.telvarost.hauntedsands.gen.NetherOreFeature;
import com.github.telvarost.hauntedsands.gen.NetherRedstoneSandFeature;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.chunk.NetherChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static com.github.telvarost.hauntedsands.events.init.BlockListener.*;

@Mixin(NetherChunkGenerator.class)
public class NetherChunkGeneratorMixin {

    @Shadow private Random random;

    @Shadow private World world;

    @Inject(
            method = "decorate",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/Random;nextInt(I)I",
                    ordinal = 16
            ),
            cancellable = true
    )
    public void hauntedSands_decorateCaveSand(ChunkSource source, int x, int z, CallbackInfo ci) {
        int var4 = x * 16;
        int var5 = z * 16;

        if (Config.config.TERRAIN_GENERATION_CONFIG.enableNetherGlowstoneSandGeneration) {
            int var13;
            int var14;
            int var15;
            int var16;

            for (var13 = 0; var13 < 4; ++var13) {
                var14 = var4 + this.random.nextInt(16);
                var15 = this.random.nextInt(128);
                var16 = var5 + this.random.nextInt(16);
                (new NetherOreFeature(GLOWSTONE_SAND.id, this.random.nextInt(8))).generate(this.world, this.random, var14, var15, var16);
            }
        }

        if (Config.config.TERRAIN_GENERATION_CONFIG.enableNetherRedstoneSandGeneration) {
            int var14;
            int var15;
            int var16;

            var14 = var4 + this.random.nextInt(16);
            var15 = this.random.nextInt(80);
            var16 = var5 + this.random.nextInt(16);
            (new NetherRedstoneSandFeature(REDSTONE_SAND.id, this.random.nextInt(64))).generate(this.world, this.random, var14, var15, var16);
        }
    }
}
