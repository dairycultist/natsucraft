package net.natsupotato.natsucraft.feature;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.natsupotato.natsucraft.util.GenerationHelper;
import net.natsupotato.natsucraft.util.GlobalPlacer;
import net.natsupotato.natsucraft.util.LocalPlacer;
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

        Placer topPlacer = new LocalPlacer(world, x - 5, y, z - 5);

        // top floor
        topPlacer.hollowRect(Block.COBBLESTONE.id, 0, 0, 0, 11, 7, 11);
        topPlacer.fillRect(Block.DIRT.id, 1, 0, 1, 9, 1, 9);

        topPlacer.fillRect(Block.COBBLESTONE.id, 0, 7, 0, 11, 1, 11);

//        topPlacer.fillRectMeta(Block.SLAB.id, 3, x - 5, y + 6, z - 5, 11, 1, 1);
//        topPlacer.fillRectMeta(Block.SLAB.id, 3, x - 5, y + 6, z + 5, 11, 1, 1);
//        topPlacer.fillRectMeta(Block.SLAB.id, 3, x - 5, y + 6, z - 5, 1, 1, 11);
//        topPlacer.fillRectMeta(Block.SLAB.id, 3, x + 5, y + 6, z - 5, 1, 1, 11);

        // doors
        topPlacer.fillRect(0, 3, 1, 0, 5, 5, 1);
        topPlacer.fillRect(0, 3, 1, 10, 5, 5, 1);
        topPlacer.fillRect(0, 0, 1, 3, 1, 5, 5);
        topPlacer.fillRect(0, 10, 1, 3, 1, 5, 5);

        // maze
        GenerationHelper.generateMaze(
                world, random,
                x - 22, y - 16, z - 22,
                9, 7, 9,
                5, 5,
                Block.COBBLESTONE.id,
                (placer, localRandom) -> {

                    placer.fillRect(Block.LOG.id, 1, 1, 1, 1, 5, 1);
                    placer.fillRect(Block.LOG.id, 7, 1, 1, 1, 5, 1);
                    placer.fillRect(Block.LOG.id, 1, 1, 7, 1, 5, 1);
                    placer.fillRect(Block.LOG.id, 7, 1, 7, 1, 5, 1);

                    placer.fillRect(Block.COBBLESTONE.id, 1, 5, 1, 7, 1, 7);
                    placer.fillRect(0, 2, 5, 2, 5, 1, 5);

//                    int roomType = localRandom.nextInt(10);
//
//                    if (roomType == 0 || roomType == 1 || roomType == 2) {
//                        localWorld.setBlock(localX + 1, localY + 4, localZ + 4, Block.TORCH.id);
//                        localWorld.setBlock(localX + 7, localY + 4, localZ + 4, Block.TORCH.id);
//                        localWorld.setBlock(localX + 4, localY + 4, localZ + 1, Block.TORCH.id);
//                        localWorld.setBlock(localX + 4, localY + 4, localZ + 7, Block.TORCH.id);
//                    }
//
//                    ChestBlockEntity chest = null;
//
//                    switch (roomType) {
//
//                        // treasure room
//                        case 0:
//                            chest = GenerationHelper.lootChest(localWorld, localRandom, localX + 4, localY + 2, localZ + 4, 4,
//                                    new Item[] { Item.GOLD_INGOT, Item.IRON_INGOT, Item.DIAMOND },
//                                    new int[] { 2, 2, 1 },
//                                    new int[] { 5, 5, 2 }
//                            );
//
//                            if (localRandom.nextBoolean())
//                                chest.setStack(localRandom.nextInt(chest.size()), new ItemStack(Item.GOLDEN_APPLE, 1));
//
//                            GenerationHelper.fillRect(localWorld, Block.SANDSTONE.id, localX + 3, localY + 1, localZ + 3, 3, 1, 3);
//                            break;
//
//                        // weird pillar room
//                        case 1:
//                            GenerationHelper.fillRect(localWorld, Block.SANDSTONE.id, localX + 3, localY + 1, localZ + 3, 3, 4, 3);
//                            break;
//
//                        // tomb
//                        case 2:
//                            GenerationHelper.fillRect(localWorld, Block.SANDSTONE.id, localX + 2, localY + 1, localZ + 3, 5, 1, 3);
//                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 2, localY + 1, localZ + 3, Block.SLAB.id, 1);
//                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 2, localY + 1, localZ + 5, Block.SLAB.id, 1);
//                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 4, localY + 1, localZ + 3, Block.SLAB.id, 1);
//                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 4, localY + 1, localZ + 5, Block.SLAB.id, 1);
//                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 5, localY + 1, localZ + 3, Block.SLAB.id, 1);
//                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 5, localY + 1, localZ + 5, Block.SLAB.id, 1);
//                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 6, localY + 1, localZ + 3, Block.SLAB.id, 1);
//                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 6, localY + 1, localZ + 5, Block.SLAB.id, 1);
//                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 3, localY + 1, localZ + 4, Block.GOLD_BLOCK.id);
//
//                            chest = GenerationHelper.lootChest(localWorld, localRandom, localX + 3, localY, localZ + 4, 8,
//                                    new Item[] { Item.BONE, Natsucraft.FABRIC },
//                                    new int[] { 1, 1 },
//                                    new int[] { 5, 5 }
//                            );
//
//                            if (localRandom.nextBoolean())
//                                chest.setStack(localRandom.nextInt(chest.size()), new ItemStack(Item.IRON_SWORD, 1));
//
//                            break;
//
//                        // spawner room/chest room
//                        default:
//
//                            // either spiders or mummies or none
//                            String monster = null;
//
//                            if (localRandom.nextBoolean()) {
//                                monster = "Mummy";
//                            } else if (localRandom.nextBoolean()) {
//                                monster = "Spider";
//
//                                for (int ox = localX + 1; ox < localX + 8; ox++)
//                                    for (int oz = localZ + 1; oz < localZ + 8; oz++)
//                                        for (int oy = localY + 1; oy < localY + 6; oy++)
//                                            if (localWorld.getBlockId(ox, oy, oz) == 0 && localRandom.nextInt(3) == 0)
//                                                localWorld.setBlock(ox, oy, oz, Block.COBWEB.id);
//                            }
//
//                            if (monster != null) {
//
//                                localWorld.setBlock(localX + 4, localY + 1, localZ + 4, Block.SPAWNER.id);
//                                ((MobSpawnerBlockEntity) localWorld.getBlockEntity(localX + 4, localY + 1, localZ + 4))
//                                        .setSpawnedEntityId(monster);
//                            }
//
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
//
//                            break;
//                    }
                }
        );

        // tunnel down
        topPlacer.fillRect(Block.COBBLESTONE.id, 3, -10, 3, 5, 11, 5);
        topPlacer.fillRect(0, 4, -12, 4, 3, 13, 3);

        return true;
    }
}
