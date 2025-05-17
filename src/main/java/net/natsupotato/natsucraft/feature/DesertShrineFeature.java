package net.natsupotato.natsucraft.feature;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.natsupotato.natsucraft.Natsucraft;

import java.util.Random;

public class DesertShrineFeature extends Feature {

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {

        if (random.nextInt(4) != 0) // 128
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
        GenerationHelper.generateMaze(world, random, x - 3, y - 15, z - 3, 4, 4, Block.SANDSTONE.id);

        // tunnel down
        for (int ox = x - 2; ox <= x + 2; ox++) {
            for (int oz = z - 2; oz <= z + 2; oz++) {

                if (ox == x - 2 || ox == x + 2 || oz == z - 2 || oz == z + 2)
                    for (int oy = y - 10; oy <= y; oy++)
                        world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, Block.SANDSTONE.id);
                else
                    for (int oy = y - 10; oy <= y; oy++)
                        world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, 0);
            }
        }

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
