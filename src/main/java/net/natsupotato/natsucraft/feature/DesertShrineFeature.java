package net.natsupotato.natsucraft.feature;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.natsupotato.natsucraft.Natsucraft;

import java.util.Random;

public class DesertShrineFeature extends Feature {

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {

        if (random.nextInt(8) != 0) // 128
            return false;

        // base/foundation
        for (int ox = x - 3; ox <= x + 3; ox++) {
            for (int oz = z - 3; oz <= z + 3; oz++) {

                world.setBlockWithoutNotifyingNeighbors(ox, y, oz, Block.SLAB.id, 1);

                world.setBlockWithoutNotifyingNeighbors(ox, y - 1, oz, Block.SANDSTONE.id);
                world.setBlockWithoutNotifyingNeighbors(ox, y - 2, oz, Block.SANDSTONE.id);
                world.setBlockWithoutNotifyingNeighbors(ox, y - 3, oz, Block.SANDSTONE.id);
                world.setBlockWithoutNotifyingNeighbors(ox, y - 4, oz, Block.SANDSTONE.id);
                world.setBlockWithoutNotifyingNeighbors(ox, y - 5, oz, Block.SANDSTONE.id);
            }
        }

        // center platform
        for (int ox = x - 2; ox <= x + 2; ox++) {
            for (int oz = z - 2; oz <= z + 2; oz++) {

                world.setBlockWithoutNotifyingNeighbors(ox, y, oz, Block.SANDSTONE.id);
            }
        }

        // maze
        GenerationHelper.generateMaze(world, random, x - 17, y - 15, z - 17, 7, 6, 7, 5, 5, Block.SANDSTONE.id);

        GenerationHelper.replaceRect(world, Block.SANDSTONE.id, Natsucraft.LAPIS_SANDSTONE.id, x - 17, y - 14, z - 17, 36, 1, 36);

        // tunnel down
        GenerationHelper.fillRect(world, Block.SANDSTONE.id, x - 2, y - 10, z - 2, 5, 11, 5);
        GenerationHelper.fillRect(world, 0, x - 1, y - 10, z - 1, 3, 11, 3);

        // ornate pillars
        for (int ox = x - 3; ox <= x + 3; ox += 6) {
            for (int oz = z - 3; oz <= z + 3; oz += 6) {

                world.setBlockWithoutNotifyingNeighbors(ox, y, oz, Block.SANDSTONE.id);
                world.setBlockWithoutNotifyingNeighbors(ox, y + 1, oz, Block.SANDSTONE.id);
                world.setBlockWithoutNotifyingNeighbors(ox, y + 2, oz, Natsucraft.GLOWSAP_SANDSTONE.id);
                world.setBlockWithoutNotifyingNeighbors(ox, y + 3, oz, Block.SLAB.id);
                world.setBlockMetaWithoutNotifyingNeighbors(ox, y + 3, oz, 1);
            }
        }

        return true;
    }
}
