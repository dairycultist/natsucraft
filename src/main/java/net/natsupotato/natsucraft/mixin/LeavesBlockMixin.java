package net.natsupotato.natsucraft.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.world.World;
import net.natsupotato.natsucraft.Natsucraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LeavesBlock.class)
public class LeavesBlockMixin {

    @WrapOperation(
            method = "onTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/World;getBlockId(III)I")
    )
    public int getBlockId(World world, int x, int y, int z, Operation<Integer> original) {

        int actualBlockId = original.call(world, x, y, z);

        if (actualBlockId == Natsucraft.JUNGLE_LOG.id)
            return Block.LOG.id;

        return actualBlockId;
    }
}
