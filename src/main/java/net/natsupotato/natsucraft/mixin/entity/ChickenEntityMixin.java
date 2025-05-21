package net.natsupotato.natsucraft.mixin.entity;

import net.minecraft.entity.passive.ChickenEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChickenEntity.class)
public class ChickenEntityMixin {

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void initMixin(CallbackInfo ci) {

        ((LivingEntityAccessor) this).setTexture("/assets/natsucraft/stationapi/textures/entity/chicken_"
                                                 + (int) (Math.random() * 3) + ".png");
    }

    // might mixin NBT later to save variant
    // also make certain variants only spawn in certain biomes idk
}
