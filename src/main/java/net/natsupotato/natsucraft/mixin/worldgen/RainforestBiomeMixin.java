package net.natsupotato.natsucraft.mixin.worldgen;

import net.minecraft.world.biome.RainforestBiome;
import net.minecraft.world.gen.feature.Feature;
import net.natsupotato.natsucraft.RainforestFoliageFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(RainforestBiome.class)
public class RainforestBiomeMixin {

//    sky 0x7db7ff

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void initMixin(CallbackInfo ci) {

        RainforestBiome self = (RainforestBiome) (Object) this;

        self.grassColor = 0x22bd2c;
    }

    @Inject(method = "getRandomTreeFeature", at = @At(value = "HEAD"), cancellable = true)
    public void getRandomTreeFeatureMixin(Random random, CallbackInfoReturnable<Feature> cir) {

        cir.setReturnValue(new RainforestFoliageFeature());
    }
}
