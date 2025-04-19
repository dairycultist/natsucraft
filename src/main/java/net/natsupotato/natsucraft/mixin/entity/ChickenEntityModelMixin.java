package net.natsupotato.natsucraft.mixin.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.ChickenEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ChickenEntityModel.class)
public class ChickenEntityModelMixin {

    @Unique
    public ModelPart tail;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void initMixin(CallbackInfo ci) {

        this.tail = new ModelPart(38, -8);
        this.tail.addCuboid(0.0F, 10.0F, 0.0F, 0, 8, 8, 0.0F);
        this.tail.setPivot(0.0F, 0.0F, 0.0F);
    }

    @Inject(method = "render", at = @At(value = "TAIL"))
    private void renderMixin(float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch, float scale, CallbackInfo ci) {

        this.tail.render(scale);
    }
}
