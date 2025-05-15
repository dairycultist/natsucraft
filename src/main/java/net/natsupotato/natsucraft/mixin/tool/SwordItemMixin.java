package net.natsupotato.natsucraft.mixin.tool;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SwordItem.class)
public class SwordItemMixin {

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void initMixin(int id, ToolMaterial toolMaterial, CallbackInfo ci) {

        ((SwordItem) (Object) this).setMaxDamage(0);
    }
}
