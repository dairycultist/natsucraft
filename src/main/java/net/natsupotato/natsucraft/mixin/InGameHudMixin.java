package net.natsupotato.natsucraft.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow
    private Minecraft minecraft;

    @Inject(method = "render", at = @At("TAIL"))
    public void render(float tickDelta, boolean screenOpen, int mouseX, int mouseY, CallbackInfo ci) {

        this.minecraft.textRenderer.drawWithShadow("NatsuCraft prerelease", 1, 1, -1);
    }
}
