package net.bluebunnex.template.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

    @Inject(method = "getDroppedItemId", at = @At("TAIL"), cancellable = true)
    public void getDroppedItemId(int blockMeta, Random random, CallbackInfoReturnable<Integer> cir) {

        cir.setReturnValue(blockMeta == 0 ? Item.STICK.id : Block.SAPLING.id);
    }

    @Inject(method = "breakLeaves", at = @At("HEAD"))
    private void breakLeaves(World world, int x, int y, int z, CallbackInfo ci) {

        // The base function passes the meta of the block to determine what
        // kind of sapling to drop.
        // Allow a stick to drop instead by passing blockMeta = 0.
        ((LeavesBlock) (Object) this).dropStacks(world, x, y, z, 0);
    }
}
