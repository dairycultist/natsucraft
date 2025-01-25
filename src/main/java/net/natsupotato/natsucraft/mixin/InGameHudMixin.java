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

        int seconds = Math.abs(this.minecraft.player.score / 20);

        int minutes = seconds / 60;
        seconds %= 60;

        String text = String.format("%d:%02d", minutes, seconds);
        int color = -1;

        if (this.minecraft.player.score < 0) {
            text = "Beat game in: " + text;
            color = -16720572;
        }

        this.minecraft.textRenderer.drawWithShadow(text, 1, 1, color);
    }
}
