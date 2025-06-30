package net.natsupotato.natsucraft.util;

import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class MazeUtil {

    public interface SubGenerator {

        void generate(LocalPlacer placer);
    }

    // generate a closed maze, where xyz is bottom corner
    public static void generateMaze(World world, Random random, int x, int y, int z, int roomW, int roomH, int roomL, int xCels, int zCels, int wallBlockId, SubGenerator roomPopulator) {

        GlobalPlacer placer = new GlobalPlacer(world);

        // generate rooms
        for (int xCel = 0; xCel < xCels; xCel++) {
            for (int zCel = 0; zCel < zCels; zCel++) {

                placer.hollowRect(wallBlockId, x + xCel * roomW, y, z + zCel * roomL, roomW, roomH, roomL);
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
                        placer.fillRect(0, minusX_X, y + 1, minusX_Z, 2, 3, 3);
                        xStack.push(xCel - 1);
                        zStack.push(zCel);
                        discovered[xCel - 1][zCel] = true;
                        break;

                    case 1: // +x
                        placer.fillRect(0, minusX_X + roomW, y + 1, minusX_Z, 2, 3, 3);
                        xStack.push(xCel + 1);
                        zStack.push(zCel);
                        discovered[xCel + 1][zCel] = true;
                        break;

                    case 2: // -z
                        placer.fillRect(0, minusZ_X, y + 1, minusZ_Z, 3, 3, 2);
                        xStack.push(xCel);
                        zStack.push(zCel - 1);
                        discovered[xCel][zCel - 1] = true;
                        break;

                    case 3: // +z
                        placer.fillRect(0, minusZ_X, y + 1, minusZ_Z + roomL, 3, 3, 2);
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

                roomPopulator.generate(new LocalPlacer(world, x + xCel * roomW, y, z + zCel * roomL));
            }
        }
    }
}
