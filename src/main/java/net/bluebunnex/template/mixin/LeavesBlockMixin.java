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

        cir.setReturnValue(blockMeta == 100 ? Item.STICK.id : Block.SAPLING.id);
    }

    @Inject(method = "getDroppedItemCount", at = @At("TAIL"), cancellable = true)
    public void getDroppedItemCount(Random random, CallbackInfoReturnable<Integer> cir) {

        // Leaves did NOT drop things as often as I would like.
        cir.setReturnValue(random.nextInt(10) == 0 ? 1 : 0);
    }

    @Inject(method = "onBreak", at = @At("HEAD"))
    public void onBreak(World world, int x, int y, int z, CallbackInfo ci) {

        // The meta of the block typically determines what kind of sapling to drop.
        // Indicate a stick should drop instead by passing blockMeta = 100.
        ((LeavesBlock) (Object) this).dropStacks(world, x, y, z, 100);
    }
}
