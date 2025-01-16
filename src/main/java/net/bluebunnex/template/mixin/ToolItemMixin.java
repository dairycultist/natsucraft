package net.bluebunnex.template.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.ToolItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ToolItem.class)
public class ToolItemMixin {

    @Unique
    public boolean isSuitableFor(Block block) {
        return true;
    }
}
