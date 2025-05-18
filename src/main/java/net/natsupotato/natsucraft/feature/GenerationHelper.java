package net.natsupotato.natsucraft.feature;

import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GenerationHelper {

    // generate a closed maze, where xyz is bottom corner
    public static void generateMaze(World world, Random random, int x, int y, int z, int xCels, int zCels, int wallBlockId) {

        // generate rooms
        for (int xCel = 0; xCel < xCels; xCel++) {
            for (int zCel = 0; zCel < zCels; zCel++) {

                fillHollowRect(world, wallBlockId, x + xCel * 7, y, z + zCel * 7, 7, 6, 7);
            }
        }

        // cut doors using randomized DFS
        boolean[][] discovered = new boolean[xCels][zCels];
        Stack<Integer> xStack = new Stack<>();
        Stack<Integer> zStack = new Stack<>();

        xStack.push(0);
        zStack.push(0);
        discovered[0][0] = true;

        while (!xStack.empty()) {

            int xCel = xStack.peek();
            int zCel = zStack.peek();

            // find all possible paths we can go down from here
            ArrayList<Integer> possibleDirections = new ArrayList<>();

            if (xCel > 0 && !discovered[xCel - 1][zCel])
                possibleDirections.add(0); // -x

            if (xCel < xCels - 1 && !discovered[xCel + 1][zCel])
                possibleDirections.add(1); // +x

            if (zCel > 0 && !discovered[xCel][zCel - 1])
                possibleDirections.add(2); // -z

            if (zCel < zCels - 1 && !discovered[xCel][zCel + 1])
                possibleDirections.add(3); // +z

            if (possibleDirections.isEmpty()) {

                // no possible paths; backtrack
                xStack.pop();
                zStack.pop();

            } else {

                // take path at random
                switch (possibleDirections.get(random.nextInt(possibleDirections.size()))) {

                    case 0: // -x
                        fillRect(world, 0, x - 1 + xCel * 7, y + 1, z + 2 + zCel * 7, 2, 3, 3);
                        xStack.push(xCel - 1);
                        zStack.push(zCel);
                        discovered[xCel - 1][zCel] = true;
                        break;

                    case 1: // +x
                        fillRect(world, 0, x + 6 + xCel * 7, y + 1, z + 2 + zCel * 7, 2, 3, 3);
                        xStack.push(xCel + 1);
                        zStack.push(zCel);
                        discovered[xCel + 1][zCel] = true;
                        break;

                    case 2: // -z
                        fillRect(world, 0, x + 2 + xCel * 7, y + 1, z - 1 + zCel * 7, 3, 3, 2);
                        xStack.push(xCel);
                        zStack.push(zCel - 1);
                        discovered[xCel][zCel - 1] = true;
                        break;

                    case 3: // +z
                        fillRect(world, 0, x + 2 + xCel * 7, y + 1, z + 6 + zCel * 7, 3, 3, 2);
                        xStack.push(xCel);
                        zStack.push(zCel + 1);
                        discovered[xCel][zCel + 1] = true;
                        break;

                }
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
