package net.natsupotato.natsucraft.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.UndeadEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.natsupotato.natsucraft.Natsucraft;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(UndeadEntityRenderer.class)
public class UndeadEntityRendererMixin {

    @Inject(
            method = "renderMore",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;)V",
                    shift = At.Shift.BEFORE
            )
    )
    public void clientsideEssentials_playerRendering(LivingEntity entity, float tickDelta, CallbackInfo ci) {

        ItemStack item = entity.getHeldItem();

        if (item != null && item.itemId == Natsucraft.LICH_SWORD.id) {
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
            GL11.glScalef(1.5f, 1.5f, 1.5f);
        }
    }
}
