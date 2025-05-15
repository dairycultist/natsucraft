package net.natsupotato.natsucraft.mixin.tool;

import net.minecraft.block.Block;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToolItem.class)
public class ToolItemMixin {

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void initMixin(int id, int damageBoost, ToolMaterial toolMaterial, Block[] effectiveOn, CallbackInfo ci) {

        ((ToolItem) (Object) this).setMaxDamage(0);
    }
}
