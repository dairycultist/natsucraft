package net.natsupotato.natsucraft.mixin;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.feature.OreFeature;
import net.natsupotato.natsucraft.Natsucraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {

    @Shadow
    private Random random;

    @Shadow
    private World world;

    @Inject(method = "decorate", at = @At("TAIL"))
    private void decorateMixin(ChunkSource source, int x, int z, CallbackInfo ci) {

        // chunk to block
        x *= 16;
        z *= 16;

        // generate sapphire ore feature
        int fx = x + this.random.nextInt(16);
        int fy = this.random.nextInt(16) + this.random.nextInt(16);
        int fz = z + this.random.nextInt(16);

        (new OreFeature(Natsucraft.SAPPHIRE_ORE.id, 7)).generate(this.world, this.random, fx, fy, fz);
    }
}
