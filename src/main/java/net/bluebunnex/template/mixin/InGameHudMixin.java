package net.bluebunnex.template.mixin;

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

        int seconds = this.minecraft.player.score / 20;

        int minutes = seconds / 60;
        seconds %= 60;

        this.minecraft.textRenderer.drawWithShadow(minutes + ":" + seconds, 1, 1, -1);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tick(CallbackInfo ci) {

        if (this.minecraft.player != null)
            this.minecraft.player.score++;
    }
}
