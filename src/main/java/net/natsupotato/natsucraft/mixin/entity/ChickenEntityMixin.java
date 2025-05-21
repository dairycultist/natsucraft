package net.natsupotato.natsucraft.mixin.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.item.Item;
import net.natsupotato.natsucraft.Natsucraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChickenEntity.class)
public class ChickenEntityMixin {

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void initMixin(CallbackInfo ci) {

        ((LivingEntityAccessor) this).setTexture("/assets/natsucraft/stationapi/textures/entity/chicken_"
                                                 + (int) (Math.random() * 3) + ".png");
    }

    // might mixin NBT later to save variant
    // also make certain variants only spawn in certain biomes idk

    @Inject(method = "getDroppedItemId", at = @At(value = "HEAD"), cancellable = true)
    protected void getDroppedItemIdMixin(CallbackInfoReturnable<Integer> cir) {

        // always drop cooked food if burning, cuz we want cooked food bruh!
        if (((Entity) (Object) (this)).fireTicks > 0)
            cir.setReturnValue(Natsucraft.CHICKEN_COOKED.id);

        else if (Math.random() > 0.5)
            cir.setReturnValue(Natsucraft.CHICKEN_RAW.id);
    }
}
