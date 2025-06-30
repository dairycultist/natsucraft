package net.natsupotato.natsucraft.util;

import net.minecraft.block.Block;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class GenerationHelper {

    public interface SubGenerator {

        void generate(LocalPlacer placer, Random localRandom);
    }

    public static ChestBlockEntity lootChest(World world, Random random, int x, int y, int z, int tries, Item[] items, int[] mins, int[] maxes) {

        world.setBlock(x, y, z, Block.CHEST.id);
        ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(x, y, z);

        for (int i = 0; i < tries; i++) {

            int itemIndex = random.nextInt(items.length);

            chest.setStack(random.nextInt(chest.size()), new ItemStack(items[itemIndex], random.nextInt(mins[itemIndex], maxes[itemIndex])));
        }

        return chest;
    }

    // generate a closed maze, where xyz is bottom corner
    public static void generateMaze(World world, Random random, int x, int y, int z, int roomW, int roomH, int roomL, int xCels, int zCels, int wallBlockId, SubGenerator roomPopulator) {

        // generate rooms
        for (int xCel = 0; xCel < xCels; xCel++) {
            for (int zCel = 0; zCel < zCels; zCel++) {

                fillHollowRect(world, wallBlockId, x + xCel * roomW, y, z + zCel * roomL, roomW, roomH, roomL);
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
                int minusX_X = x - 1 + xCel * roomW;
                int minusX_Z = z + ((roomL - 3) / 2) + zCel * roomL;

                int minusZ_X = x + ((roomW - 3) / 2) + xCel * roomW;
                int minusZ_Z = z - 1 + zCel * roomL;

                switch (possibleDirections.get(random.nextInt(possibleDirections.size()))) {

                    case 0: // -x
                        fillRect(world, 0, minusX_X, y + 1, minusX_Z, 2, 3, 3);
                        xStack.push(xCel - 1);
                        zStack.push(zCel);
                        discovered[xCel - 1][zCel] = true;
                        break;

                    case 1: // +x
                        fillRect(world, 0, minusX_X + roomW, y + 1, minusX_Z, 2, 3, 3);
                        xStack.push(xCel + 1);
                        zStack.push(zCel);
                        discovered[xCel + 1][zCel] = true;
                        break;

                    case 2: // -z
                        fillRect(world, 0, minusZ_X, y + 1, minusZ_Z, 3, 3, 2);
                        xStack.push(xCel);
                        zStack.push(zCel - 1);
                        discovered[xCel][zCel - 1] = true;
                        break;

                    case 3: // +z
                        fillRect(world, 0, minusZ_X, y + 1, minusZ_Z + roomL, 3, 3, 2);
                        xStack.push(xCel);
                        zStack.push(zCel + 1);
                        discovered[xCel][zCel + 1] = true;
                        break;
                }
            }
        }

        // lambda function for generating individual room contents (after cutting out shape)
        for (int xCel = 0; xCel < xCels; xCel++) {
            for (int zCel = 0; zCel < zCels; zCel++) {

                roomPopulator.generate(new LocalPlacer(world, x + xCel * roomW, y, z + zCel * roomL), random);
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

    public static void replaceRectRandomly(World world, Random random, int oneOutOf, int fromId, int toId, int x, int y, int z, int w, int h, int l) {

        for (int ox = x; ox < x + w; ox++)
            for (int oy = y; oy < y + h; oy++)
                for (int oz = z; oz < z + l; oz++)
                    if (world.getBlockId(ox, oy, oz) == fromId && random.nextInt(oneOutOf) == 0)
                        world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, toId);
    }

    public static void fillRect(World world, int blockId, int x, int y, int z, int w, int h, int l) {

        for (int ox = x; ox < x + w; ox++)
            for (int oy = y; oy < y + h; oy++)
                for (int oz = z; oz < z + l; oz++)
                    world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, blockId);
    }

    public static void fillRectMeta(World world, int blockId, int meta, int x, int y, int z, int w, int h, int l) {

        for (int ox = x; ox < x + w; ox++)
            for (int oy = y; oy < y + h; oy++)
                for (int oz = z; oz < z + l; oz++)
                    world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, blockId, meta);
    }

    public static void fillHollowRect(World world, int blockId, int x, int y, int z, int w, int h, int l) {

        fillRect(world, blockId, x, y, z, w, h, l);
        fillRect(world, 0, x + 1, y + 1, z + 1, w - 2, h - 2, l - 2);
    }
}
