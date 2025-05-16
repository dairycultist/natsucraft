package net.natsupotato.natsucraft.feature;

import net.minecraft.world.World;

import java.util.Random;

public class GenerationHelper {

    // generate a closed maze, where xyz is bottom corner
    public static void generateMaze(World world, Random random, int x, int y, int z, int wallBlockId) {

        fillHollowRect(world, wallBlockId, x, y, z, x + 20, y + 6, z + 20);
    }

    public static void fillRect(World world, int blockId, int x1, int y1, int z1, int x2, int y2, int z2) {

        for (int ox = x1; ox < x2; ox++)
            for (int oy = y1; oy < y2; oy++)
                for (int oz = z1; oz < z2; oz++)
                    world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, blockId);
    }

    public static void fillHollowRect(World world, int blockId, int x1, int y1, int z1, int x2, int y2, int z2) {

        fillRect(world, blockId, x1, y1, z1, x2, y2, z2);
        fillRect(world, 0, x1 + 1, y1 + 1, z1 + 1, x2 - 1, y2 - 1, z2 - 1);
    }
}
