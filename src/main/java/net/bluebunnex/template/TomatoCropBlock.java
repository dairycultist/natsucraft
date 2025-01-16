package net.bluebunnex.template;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.template.block.TemplateCropBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

public class TomatoCropBlock extends TemplateCropBlock {

    // gonna have to override getTexture
    // and bind our own textures the same way we did in Peanutbutter for copper tools

    // also need to add tomato seeds, which can be planted and which drop from grass

    public TomatoCropBlock(Identifier identifier) {
        super(identifier, 88);

        this.setSoundGroup(Block.DIRT_SOUND_GROUP);
    }

    @Override
    public void dropStacks(World world, int x, int y, int z, int meta, float luck) {

        if (!world.isRemote) {

            int count = world.random.nextInt(2, 5);

            for (int i = 0; i < count; i++) {

                this.dropStack(world, x, y, z, new ItemStack(Template.TOMATO, 1, 0));
            }
        }
    }

    @Override
    public boolean onBonemealUse(World world, int x, int y, int z, BlockState state) {

        if (world.getBlockMeta(x, y, z) != 7) {

            this.applyFullGrowth(world, x, y, z);
            return true;

        } else {

            return false;
        }
    }
}
