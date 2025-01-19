package net.bluebunnex.template.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    // score is currently being used for keeping track of game time, I'll change this later

    @Inject(method = "tickPortalCooldown", at = @At("TAIL"))
    public void tickPortalCooldown(CallbackInfo ci) {

        PlayerEntity player = ((PlayerEntity) (Object) this);

        player.score = Math.abs(player.score) * -1;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {

        PlayerEntity player = ((PlayerEntity) (Object) this);

        if (player.score >= 0)
            player.score++;
    }
}
