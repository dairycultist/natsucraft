package net.natsupotato.natsucraft;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class JungleTreeFeature extends Feature {

    public boolean generate(World world, Random random, int x, int y, int z) {

        int count = random.nextInt(4) + 8;

        for (int i = 0; i < count; i++) {

            int lx = x + random.nextInt(16);
            int lz = z + random.nextInt(16);
            int ly = world.getTopSolidBlockY(lx, lz);

            int topID = world.getBlockId(lx, ly - 1, lz);

            if (topID == Block.GRASS_BLOCK.id || topID == Block.DIRT.id) {

                int height = ly + random.nextInt(10) + 8;

                while (ly < height) {
                    world.setBlockWithoutNotifyingNeighbors(lx, ly, lz, Natsucraft.JUNGLE_LOG.id);
                    // TODO slight chance to spawn an offshoot
                    ly++;
                }

                for (int dist = 4; dist >= 2; dist--) {
                    for (int ox = -dist; ox <= dist; ox++) {
                        for (int oz = -dist; oz <= dist; oz++) {

                            if (Math.abs(ox) == dist && Math.abs(oz) == dist)
                                continue;

                            world.setBlockWithoutNotifyingNeighbors(lx + ox, ly + 4 - dist, lz + oz, Block.GLASS.id); // TODO replace with leaves
                        }
                    }
                }

                world.setBlockWithoutNotifyingNeighbors(lx, ly, lz, Natsucraft.JUNGLE_LOG.id);
            }
        }

        return true;
    }

}
