package net.natsupotato.natsucraft.feature;

import net.minecraft.block.Block;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.natsupotato.natsucraft.util.ChestUtil;
import net.natsupotato.natsucraft.util.LocalPlacer;
import net.natsupotato.natsucraft.util.MazeUtil;
import net.natsupotato.natsucraft.util.Placer;

import java.util.Random;

public class CryptFeature extends Feature {

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {

        if (random.nextInt(128) != 0)
            return false;

        return place(world, random, x, y, z);
    }

    public boolean place(World world, Random random, int x, int y, int z) {

        // maze
        MazeUtil.generateMaze(
                world, random,
                x - 22, y - 16, z - 22,
                9, 7, 9,
                5, 5,
                Block.COBBLESTONE.id,
                (placer) -> {

                    // pillars
                    placer.fillRect(Block.LOG.id, 1, 1, 1, 1, 5, 1);
                    placer.fillRect(Block.LOG.id, 7, 1, 1, 1, 5, 1);
                    placer.fillRect(Block.LOG.id, 1, 1, 7, 1, 5, 1);
                    placer.fillRect(Block.LOG.id, 7, 1, 7, 1, 5, 1);

                    // top trim
                    placer.fillRect(Block.COBBLESTONE.id, 1, 5, 1, 7, 1, 7);
                    placer.fillRect(0, 2, 5, 2, 5, 1, 5);

                    // round(ish) corners
                    placer.setBlock(Block.COBBLESTONE.id, 2, 5, 2);
                    placer.setBlock(Block.COBBLESTONE.id, 6, 5, 2);
                    placer.setBlock(Block.COBBLESTONE.id, 2, 5, 6);
                    placer.setBlock(Block.COBBLESTONE.id, 6, 5, 6);

                    placer.setBlock(Block.COBBLESTONE.id, 1, 4, 2);
                    placer.setBlock(Block.COBBLESTONE.id, 2, 4, 1);

                    placer.setBlock(Block.COBBLESTONE.id, 7, 4, 2);
                    placer.setBlock(Block.COBBLESTONE.id, 6, 4, 1);

                    placer.setBlock(Block.COBBLESTONE.id, 1, 4, 6);
                    placer.setBlock(Block.COBBLESTONE.id, 2, 4, 7);

                    placer.setBlock(Block.COBBLESTONE.id, 7, 4, 6);
                    placer.setBlock(Block.COBBLESTONE.id, 6, 4, 7);

                    // rooms
                    int roomType = random.nextInt(5);

                    // torches
                    if (roomType == 0) {
                        placer.setBlock(Block.TORCH.id, 1, 4, 4);
                        placer.setBlock(Block.TORCH.id, 7, 4, 4);
                        placer.setBlock(Block.TORCH.id, 4, 4, 1);
                        placer.setBlock(Block.TORCH.id, 4, 4, 7);
                    }

//                    ChestBlockEntity chest = null;

                    switch (roomType) {

                        // treasure room
                        case 0:
//                            chest = ChestUtil.lootChest(world, random, localX + 4, localY + 2, localZ + 4, 4,
//                                    new Item[] { Item.GOLD_INGOT, Item.IRON_INGOT, Item.DIAMOND },
//                                    new int[] { 2, 2, 1 },
//                                    new int[] { 5, 5, 2 }
//                            );
//
//                            if (localRandom.nextBoolean())
//                                chest.setStack(localRandom.nextInt(chest.size()), new ItemStack(Item.GOLDEN_APPLE, 1));

                            placer.fillRect(Block.COBBLESTONE.id, 3, 1, 3, 3, 1, 3);
                            break;

                        // spawner room/chest room
                        default:

                            String monster = null;

                            switch (random.nextInt(3)) {
                                case 0: monster = "Mummy"; break;
                                case 1: monster = "Spider";

                                        // webs
                                        placer.replaceRect((prevBlockId) -> {

                                                    if (prevBlockId == 0 && random.nextInt(3) == 0)
                                                        return Block.COBWEB.id;

                                                    return prevBlockId;
                                                },
                                                1, 1, 1, 8, 6, 8);
                                        break;
                            }

                            if (monster != null) {

                                ((MobSpawnerBlockEntity) placer.setBlockEntity(Block.SPAWNER.id, 4, 1, 4))
                                        .setSpawnedEntityId(monster);
                            }

//                            // chests
//                            if (localRandom.nextBoolean())
//                                chest = GenerationHelper.lootChest(localWorld, localRandom, localX + 1, localY + 1, localZ + 2, 4,
//                                        new Item[]{Natsucraft.FABRIC, Item.IRON_INGOT},
//                                        new int[]{2, 2, 1},
//                                        new int[]{6, 5, 4}
//                                );
//
//                            if (localRandom.nextBoolean())
//                                chest = GenerationHelper.lootChest(localWorld, localRandom, localX + 7, localY + 1, localZ + 6, 4,
//                                        new Item[]{Natsucraft.FABRIC, Item.IRON_INGOT},
//                                        new int[]{2, 2, 1},
//                                        new int[]{6, 5, 4}
//                                );
//
//                            if (chest != null && localRandom.nextInt(20) == 0)
//                                chest.setStack(localRandom.nextInt(chest.size()), new ItemStack(Item.CHAIN_CHESTPLATE));

                            break;
                    }

                    // randomize block palette
                    placer.replaceRect((prevBlockId) -> {

                        if (prevBlockId == Block.COBBLESTONE.id) {

                            switch (random.nextInt(5)) {
                                case 0:
                                case 1:
                                    return Block.STONE.id;
                                case 2: return Block.MOSSY_COBBLESTONE.id;
                            }
                        }

                        return prevBlockId;
                    },
                    0, 0, 0, 9, 7, 9);
                }
        );

        Placer placer = new LocalPlacer(world, x - 5, y, z - 5);

        // top floor
        placer.hollowRect(Block.COBBLESTONE.id, 0, 0, 0, 11, 7, 11);
        placer.fillRect(Block.DIRT.id, 1, 0, 1, 9, 1, 9);

        placer.fillRect(Block.COBBLESTONE.id, 0, 7, 0, 11, 1, 11);

        placer.fillRect(Block.SLAB.id, 3, 0, 6, 0, 11, 1, 1);
        placer.fillRect(Block.SLAB.id, 3, 0, 6, 10, 11, 1, 1);
        placer.fillRect(Block.SLAB.id, 3, 0, 6, 0, 1, 1, 11);
        placer.fillRect(Block.SLAB.id, 3, 10, 6, 0, 1, 1, 11);

        // doors
        placer.fillRect(0, 3, 1, 0, 5, 5, 1);
        placer.fillRect(0, 3, 1, 10, 5, 5, 1);
        placer.fillRect(0, 0, 1, 3, 1, 5, 5);
        placer.fillRect(0, 10, 1, 3, 1, 5, 5);

        // tunnel down
        placer.fillRect(Block.COBBLESTONE.id, 3, -10, 3, 5, 11, 5);
        placer.fillRect(0, 4, -12, 4, 3, 13, 3);

        return true;
    }
}
