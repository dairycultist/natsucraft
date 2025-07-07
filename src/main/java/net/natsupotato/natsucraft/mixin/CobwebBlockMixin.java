package net.natsupotato.natsucraft.mixin;

import net.minecraft.block.CobwebBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CobwebBlock.class)
public class CobwebBlockMixin {

    @Inject(method = "onEntityCollision", at = @At(value = "HEAD"), cancellable = true)
    public void onEntityCollisionMixin(World world, int x, int y, int z, Entity entity, CallbackInfo ci) {

        if (entity instanceof SpiderEntity)
            ci.cancel();
    }
}
