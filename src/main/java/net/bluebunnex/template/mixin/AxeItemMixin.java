package net.bluebunnex.template.mixin;

import net.minecraft.block.Block;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AxeItem.class)
public class AxeItemMixin {

    @Shadow
    private static Block[] axeEffectiveBlocks;

    @Unique
    public boolean isSuitableFor(Block block) {

        for (Block axeBlock : axeEffectiveBlocks) {

            if (block.id == axeBlock.id)
                return true;
        }

        return false;
    }

    static {
        axeEffectiveBlocks = new Block[] { Block.PLANKS, Block.BOOKSHELF, Block.LOG, Block.CHEST, Block.CRAFTING_TABLE };
    }
}
