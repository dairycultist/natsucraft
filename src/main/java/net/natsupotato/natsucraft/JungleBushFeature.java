package net.natsupotato.natsucraft;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class JungleBushFeature extends Feature {

    public boolean generate(World world, Random random, int x, int y, int z) {

        int count = random.nextInt(4) + 2;

        for (int i = 0; i < count; i++) {

            int lx = x + random.nextInt(16);
            int lz = z + random.nextInt(16);
            int ly = world.getTopSolidBlockY(lx, lz);

            int topID = world.getBlockId(lx, ly - 1, lz);

            if (topID == Block.GRASS_BLOCK.id || topID == Block.DIRT.id) {

                world.setBlockWithoutNotifyingNeighbors(lx, ly, lz, Natsucraft.JUNGLE_LOG.id);

                for (int dist = 2 + random.nextInt(2); dist >= 1; dist--) {
                    for (int ox = -dist; ox <= dist; ox++) {
                        for (int oz = -dist; oz <= dist; oz++) {

                            if (Math.abs(ox) == dist && Math.abs(oz) == dist)
                                continue;

                            int interveningBlock = world.getBlockId(lx + ox, ly + 3 - dist, lz + oz);

                            if (interveningBlock != 0 && interveningBlock != Block.GRASS.id)
                                continue;

                            world.setBlockWithoutNotifyingNeighbors(lx + ox, ly + 3 - dist, lz + oz, Block.LEAVES.id, 2);
                        }
                    }
                }
            }
        }

        return true;
    }

}
