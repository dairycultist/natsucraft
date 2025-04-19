package net.natsupotato.natsucraft;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class RainforestFoliageFeature extends Feature {

    public boolean generate(World world, Random random, int x, int y, int z) {

        int count = random.nextInt(4) + 8;

        for (int i = 0; i < count; i++) {

            int lx = x + random.nextInt(16);
            int lz = z + random.nextInt(16);
            int ly = world.getTopSolidBlockY(lx, lz);

            int topID = world.getBlockId(lx, ly - 1, lz);

            if (topID == Block.GRASS_BLOCK.id || topID == Block.DIRT.id) {

                if (random.nextInt(5) == 0) {
                    generateBush(world, random, lx, ly, lz);
                } else {
                    generateTree(world, random, lx, ly, lz);
                }
            }
        }

        return true;
    }

    private void generateTree(World world, Random random, int lx, int ly, int lz) {

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

                    world.setBlockWithoutNotifyingNeighbors(lx + ox, ly + 4 - dist, lz + oz, Block.LEAVES.id, 2);
                }
            }
        }

        world.setBlockWithoutNotifyingNeighbors(lx, ly, lz, Natsucraft.JUNGLE_LOG.id);
    }

    private void generateBush(World world, Random random, int lx, int ly, int lz) {

        world.setBlockWithoutNotifyingNeighbors(lx, ly, lz, Natsucraft.JUNGLE_LOG.id);

        int maxDist = 2 + random.nextInt(2);

        for (int dist = maxDist; dist >= 1; dist--) {
            for (int ox = -dist; ox <= dist; ox++) {
                for (int oz = -dist; oz <= dist; oz++) {

                    if (Math.abs(ox) == dist && Math.abs(oz) == dist)
                        continue;

                    int interveningBlock = world.getBlockId(lx + ox, ly + maxDist - dist, lz + oz);

                    if (interveningBlock != 0 && interveningBlock != Block.GRASS.id)
                        continue;

                    world.setBlockWithoutNotifyingNeighbors(lx + ox, ly + maxDist - dist, lz + oz, Block.LEAVES.id, 2);
                }
            }
        }
    }

}
