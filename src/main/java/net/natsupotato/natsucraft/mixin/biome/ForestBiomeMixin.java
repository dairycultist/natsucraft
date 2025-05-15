package net.natsupotato.natsucraft.mixin.biome;

import net.minecraft.block.Block;
import net.minecraft.world.biome.ForestBiome;
import net.minecraft.world.gen.feature.GrassPatchFeature;
import net.natsupotato.natsucraft.Natsucraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ForestBiome.class)
public class ForestBiomeMixin {

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void initMixin(CallbackInfo ci) {

        ForestBiome self = (ForestBiome) (Object) this;

        self.addFeature(new GrassPatchFeature(Block.BROWN_MUSHROOM.id, 0));
        self.addFeature(new GrassPatchFeature(Block.RED_MUSHROOM.id, 0));
        self.addFeature(new GrassPatchFeature(Natsucraft.GLOWCAP.id, 0)); // TEMP
    }
}
