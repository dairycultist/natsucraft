package net.bluebunnex.template.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

    @Inject(method = "getDroppedItemCount", at = @At("TAIL"), cancellable = true)
    public void getDroppedItemCount(Random random, CallbackInfoReturnable<Integer> cir) {

        cir.setReturnValue(random.nextInt(10) == 0 ? 1 : 0); // doubled from original
    }

    @Inject(method = "getDroppedItemId", at = @At("TAIL"), cancellable = true)
    public void getDroppedItemId(int blockMeta, Random random, CallbackInfoReturnable<Integer> cir) {

        cir.setReturnValue(random.nextInt(2) == 0 ? Block.SAPLING.id : Item.STICK.id);
    }
}
