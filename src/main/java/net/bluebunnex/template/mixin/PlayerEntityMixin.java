package net.bluebunnex.template.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Inject(method = "tickPortalCooldown", at = @At("TAIL"))
    public void tickPortalCooldown(CallbackInfo ci) {

        ((PlayerEntity) (Object) this).score = Math.abs(((PlayerEntity) (Object) this).score) * -1;
    }
}
