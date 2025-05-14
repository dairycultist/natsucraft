package net.natsupotato.natsucraft.mixin.entity;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Unique
    private static final int TICKS_TO_HEAL = 200;

    @Unique
    private int ticksLeft = TICKS_TO_HEAL;

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {

        PlayerEntity player = ((PlayerEntity) (Object) this);

        if (player.health != player.maxHealth) {

            ticksLeft--;

            if (ticksLeft == 0) {
                player.heal(1);
                ticksLeft = TICKS_TO_HEAL;
            }

        } else {

            ticksLeft = TICKS_TO_HEAL;
        }
    }
}
