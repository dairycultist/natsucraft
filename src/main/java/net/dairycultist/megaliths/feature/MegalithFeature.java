package net.dairycultist.megaliths.feature;

import net.minecraft.block.Block;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.dairycultist.megaliths.util.LocalPlacer;
import net.dairycultist.megaliths.util.MazeUtil;

import java.util.Random;

public class MegalithFeature extends Feature {

    private static final int ROOMS_WIDE = 3;
    private static final int EXTERIOR_WALL_PADDING = 3;
    private static final int HEIGHT_ABOVE_SURFACE = 20;
    private static final int DEPTH_BENEATH_SURFACE = 20;

//    private static final int ROOMS_WIDE = 9;
//    private static final int EXTERIOR_WALL_PADDING = 3;
//    private static final int HEIGHT_ABOVE_SURFACE = 40;
//    private static final int DEPTH_BENEATH_SURFACE = 40;

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {

        if (random.nextInt(16) != 0)
            return false;

        place(world, random, x, y, z);
        return true;
    }

    public void place(World world, Random random, int x, int y, int z) {

        // worst code on the planet idc tho

        int w = ROOMS_WIDE * 9 + EXTERIOR_WALL_PADDING * 2;
        int bottom = y - DEPTH_BENEATH_SURFACE;

        LocalPlacer placer = new LocalPlacer(world, x - w / 2, bottom, z - w / 2);

        // base
        placer.fillRect(Block.COBBLESTONE.id, 0, 0, 0, w, HEIGHT_ABOVE_SURFACE + DEPTH_BENEATH_SURFACE, w);

        placer.fillRect(Block.SLAB.id, 3, 0, DEPTH_BENEATH_SURFACE + HEIGHT_ABOVE_SURFACE - 2, 0, w, 1, 1);
        placer.fillRect(Block.SLAB.id, 3, 0, DEPTH_BENEATH_SURFACE + HEIGHT_ABOVE_SURFACE - 2, 0, 1, 1, w);
        placer.fillRect(Block.SLAB.id, 3, 0, DEPTH_BENEATH_SURFACE + HEIGHT_ABOVE_SURFACE - 2, w - 1, w, 1, 1);
        placer.fillRect(Block.SLAB.id, 3, w - 1, DEPTH_BENEATH_SURFACE + HEIGHT_ABOVE_SURFACE - 2, 0, 1, 1, w);

        // internal layers of mazes
        for (int layerY = bottom; layerY < y + HEIGHT_ABOVE_SURFACE - 8; layerY += 10) {

            MazeUtil.generateMaze(
                    world,
                    random,
                    new LocalPlacer(placer, EXTERIOR_WALL_PADDING, layerY - bottom, EXTERIOR_WALL_PADDING),
                    9,
                    7,
                    9,
                    ROOMS_WIDE,
                    ROOMS_WIDE,
                    Block.COBBLESTONE.id,
                    (LocalPlacer roomPlacer) -> {

                        // pillars
                        roomPlacer.fillRect(Block.LOG.id, 1, 1, 1, 1, 5, 1);
                        roomPlacer.fillRect(Block.LOG.id, 7, 1, 1, 1, 5, 1);
                        roomPlacer.fillRect(Block.LOG.id, 1, 1, 7, 1, 5, 1);
                        roomPlacer.fillRect(Block.LOG.id, 7, 1, 7, 1, 5, 1);

                        // top trim
                        roomPlacer.fillRect(Block.COBBLESTONE.id, 1, 5, 1, 7, 1, 7);
                        roomPlacer.fillRect(0, 2, 5, 2, 5, 1, 5);

                        // round(ish) corners
                        roomPlacer.setBlock(Block.COBBLESTONE.id, 2, 5, 2);
                        roomPlacer.setBlock(Block.COBBLESTONE.id, 6, 5, 2);
                        roomPlacer.setBlock(Block.COBBLESTONE.id, 2, 5, 6);
                        roomPlacer.setBlock(Block.COBBLESTONE.id, 6, 5, 6);

                        roomPlacer.setBlock(Block.COBBLESTONE.id, 1, 4, 2);
                        roomPlacer.setBlock(Block.COBBLESTONE.id, 2, 4, 1);

                        roomPlacer.setBlock(Block.COBBLESTONE.id, 7, 4, 2);
                        roomPlacer.setBlock(Block.COBBLESTONE.id, 6, 4, 1);

                        roomPlacer.setBlock(Block.COBBLESTONE.id, 1, 4, 6);
                        roomPlacer.setBlock(Block.COBBLESTONE.id, 2, 4, 7);

                        roomPlacer.setBlock(Block.COBBLESTONE.id, 7, 4, 6);
                        roomPlacer.setBlock(Block.COBBLESTONE.id, 6, 4, 7);

                        // rooms
                        int roomType = random.nextInt(5);

                        // torches
                        if (roomType == 0 || roomType == 1) {
                            roomPlacer.setBlock(Block.TORCH.id, 1, 4, 4);
                            roomPlacer.setBlock(Block.TORCH.id, 7, 4, 4);
                            roomPlacer.setBlock(Block.TORCH.id, 4, 4, 1);
                            roomPlacer.setBlock(Block.TORCH.id, 4, 4, 7);
                        }

                        switch (roomType) {

                            // treasure room
                            case 0:
                                roomPlacer.fillRect(Block.COBBLESTONE.id, 3, 1, 3, 3, 1, 3);
                                break;

                            // stairwell down room
                            case 1:
                                roomPlacer.fillRect(0, 3, -4, 3, 3, 5, 3);
                                break;

                            // spawner room/chest room
                            default:

                                String monster = null;

                                switch (random.nextInt(3)) {
                                    case 0:
                                        monster = "Zombie";
                                        break;
                                    case 1:
                                        monster = "Spider";

                                        // webs
                                        roomPlacer.replaceRect((prevBlockId) -> {

                                            if (prevBlockId == 0 && random.nextInt(3) == 0)
                                                return Block.COBWEB.id;

                                            return prevBlockId;
                                        },
                                        1, 1, 1, 8, 6, 8);
                                        break;
                                }

                                if (monster != null)
                                    ((MobSpawnerBlockEntity) roomPlacer.setBlockEntity(Block.SPAWNER.id, 4, 1, 4))
                                            .setSpawnedEntityId(monster);

                                break;
                        }
                    }
            );
        }

        // carving
        for (int i = 0; i < ROOMS_WIDE; i++) {

            // carve in holes to make pillars
            // -z
            placer.fillRect(0, EXTERIOR_WALL_PADDING + 1 + 9 * i, DEPTH_BENEATH_SURFACE + 6, 0, 7, HEIGHT_ABOVE_SURFACE - 8, 2);
            // +z
            placer.fillRect(0, EXTERIOR_WALL_PADDING + 1 + 9 * i, DEPTH_BENEATH_SURFACE + 6, w - 2, 7, HEIGHT_ABOVE_SURFACE - 8, 2);
            // -x
            placer.fillRect(0, 0, DEPTH_BENEATH_SURFACE + 6, EXTERIOR_WALL_PADDING + 1 + 9 * i, 2, HEIGHT_ABOVE_SURFACE - 8, 7);
            // +x
            placer.fillRect(0, w - 2, DEPTH_BENEATH_SURFACE + 6, EXTERIOR_WALL_PADDING + 1 + 9 * i, 2, HEIGHT_ABOVE_SURFACE - 8, 7);

            for (int layerY = 1; layerY < DEPTH_BENEATH_SURFACE + HEIGHT_ABOVE_SURFACE - 5; layerY += 10) {

                if (layerY < DEPTH_BENEATH_SURFACE)
                    continue;

                // randomly carve some exits
                // -z
                if (random.nextBoolean())
                    placer.fillRect(0, EXTERIOR_WALL_PADDING + 3 + 9 * i, layerY, 2, 3, 3, EXTERIOR_WALL_PADDING - 1);
                // +z
                if (random.nextBoolean())
                    placer.fillRect(0, EXTERIOR_WALL_PADDING + 3 + 9 * i, layerY, w - EXTERIOR_WALL_PADDING - 1, 3, 3, EXTERIOR_WALL_PADDING - 1);
                // -x
                if (random.nextBoolean())
                    placer.fillRect(0, 2, layerY, EXTERIOR_WALL_PADDING + 3 + 9 * i, EXTERIOR_WALL_PADDING - 1, 3, 3);
                // +x
                if (random.nextBoolean())
                    placer.fillRect(0, w - EXTERIOR_WALL_PADDING - 1, layerY, EXTERIOR_WALL_PADDING + 3 + 9 * i, EXTERIOR_WALL_PADDING - 1, 3, 3);
            }
        }

        // randomize block palette
        placer.replaceRect((prevBlockId) -> {

            if (prevBlockId == Block.COBBLESTONE.id) {

                switch (random.nextInt(5)) {
                    case 0:
                    case 1: return Block.STONE.id;
                    case 2: return Block.MOSSY_COBBLESTONE.id;
                }
            }

            return prevBlockId;
        },
        0, 0, 0, w, DEPTH_BENEATH_SURFACE + HEIGHT_ABOVE_SURFACE, w);
    }
}
