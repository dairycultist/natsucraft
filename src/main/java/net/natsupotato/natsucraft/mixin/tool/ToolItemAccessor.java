package net.natsupotato.natsucraft.mixin.tool;

import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ToolItem.class)
public interface ToolItemAccessor {

    @Accessor("toolMaterial")
    ToolMaterial getToolMaterial();
}
