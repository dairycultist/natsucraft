package net.natsupotato.natsucraft.mixin.biome;

import net.minecraft.world.biome.HellBiome;
import net.minecraft.world.gen.feature.PlantPatchFeature;
import net.natsupotato.natsucraft.Natsucraft;
import net.natsupotato.natsucraft.feature.NetherFeatureFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HellBiome.class)
public class HellBiomeMixin {

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void initMixin(CallbackInfo ci) {

        HellBiome self = (HellBiome) (Object) this;

        self.addFeature(new NetherFeatureFeature(new PlantPatchFeature(Natsucraft.GLOWCAP.id), 2));
    }
}
