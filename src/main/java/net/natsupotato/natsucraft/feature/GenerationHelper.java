package net.natsupotato.natsucraft.feature;

import net.minecraft.world.World;

import java.util.Random;
import java.util.Stack;

public class GenerationHelper {

    // generate a closed maze, where xyz is bottom corner
    public static void generateMaze(World world, Random random, int x, int y, int z, int xCels, int zCels, int wallBlockId) {

        for (int xCel = 0; xCel < xCels; xCel++) {
            for (int zCel = 0; zCel < zCels; zCel++) {

                fillHollowRect(world, wallBlockId, x + xCel * 7, y, z + zCel * 7, 7, 6, 7);
            }
        }

        // cut doors
        boolean[][] visited = new boolean[xCels][zCels];
        Stack<Boolean> stack = new Stack<Boolean>();

        for (int xCel = 0; xCel < xCels; xCel++) {
            for (int zCel = 0; zCel < zCels; zCel++) {

                // +z
                fillRect(world, 0, x + 2 + xCel * 7, y + 1, z + 6 + zCel * 7, 3, 3, 2);

                // +x
                fillRect(world, 0, x + 6 + xCel * 7, y + 1, z + 2 + zCel * 7, 2, 3, 3);

                // -z
                fillRect(world, 0, x + 2 + xCel * 7, y + 1, z - 1 + zCel * 7, 3, 3, 2);

                // -x
                fillRect(world, 0, x - 1 + xCel * 7, y + 1, z + 2 + zCel * 7, 2, 3, 3);
            }
        }
    }

    public static void replaceRect(World world, int fromId, int toId, int x, int y, int z, int w, int h, int l) {

        for (int ox = x; ox < x + w; ox++)
            for (int oy = y; oy < y + h; oy++)
                for (int oz = z; oz < z + l; oz++)
                    if (world.getBlockId(ox, oy, oz) == fromId)
                        world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, toId);
    }

    public static void fillRect(World world, int blockId, int x, int y, int z, int w, int h, int l) {

        for (int ox = x; ox < x + w; ox++)
            for (int oy = y; oy < y + h; oy++)
                for (int oz = z; oz < z + l; oz++)
                    world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, blockId);
    }

    public static void fillHollowRect(World world, int blockId, int x, int y, int z, int w, int h, int l) {

        fillRect(world, blockId, x, y, z, w, h, l);
        fillRect(world, 0, x + 1, y + 1, z + 1, w - 2, h - 2, l - 2);
    }
}
