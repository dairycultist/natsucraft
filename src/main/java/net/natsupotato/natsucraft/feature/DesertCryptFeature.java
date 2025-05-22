package net.natsupotato.natsucraft.feature;

import net.minecraft.block.Block;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;
import net.natsupotato.natsucraft.Natsucraft;

import java.util.Random;

public class DesertCryptFeature extends Feature {

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {

        if (random.nextInt(8) != 0) // 128
            return false;

        // base/foundation
        GenerationHelper.fillRect(world, Block.SANDSTONE.id, x - 3, y - 5, z - 3, 7, 5, 7);

        for (int ox = x - 3; ox <= x + 3; ox++)
            for (int oz = z - 3; oz <= z + 3; oz++)
                world.setBlockWithoutNotifyingNeighbors(ox, y, oz, Block.SLAB.id, 1); // should probably add a helper with meta

        // center platform
        GenerationHelper.fillRect(world, Block.SANDSTONE.id, x - 2, y, z - 2, 5, 1, 5);

        // maze
        GenerationHelper.generateMaze(
                world, random,
                x - 22, y - 15, z - 22,
                9, 6, 9,
                5, 5,
                Block.SANDSTONE.id,
                (localWorld, localRandom, localX, localY, localZ) -> {

                    int roomType = localRandom.nextInt(6);

                    if (roomType != 0 && roomType != 1) {
                        localWorld.setBlock(localX + 1, localY + 4, localZ + 4, Block.TORCH.id);
                        localWorld.setBlock(localX + 7, localY + 4, localZ + 4, Block.TORCH.id);
                        localWorld.setBlock(localX + 4, localY + 4, localZ + 1, Block.TORCH.id);
                        localWorld.setBlock(localX + 4, localY + 4, localZ + 7, Block.TORCH.id);
                    }

                    ChestBlockEntity chest;

                    switch (roomType) {

                        // 1/3 chance of spawner room
                        case 0:
                        case 1:

                            // either spiders or mummies (zombies as placeholder)
                            String monster;

                            if (localRandom.nextBoolean()) {
                                monster = "Mummy";
                            } else {
                                monster = "Spider";

                                for (int ox = localX + 1; ox < localX + 8; ox++)
                                    for (int oz = localZ + 1; oz < localZ + 8; oz++)
                                        for (int oy = localY + 1; oy < localY + 5; oy++)
                                            if (localWorld.getBlockId(ox, oy, oz) == 0 && localRandom.nextInt(3) == 0)
                                                localWorld.setBlock(ox, oy, oz, Block.COBWEB.id);
                            }

                            localWorld.setBlock(localX + 4, localY + 1, localZ + 4, Block.SPAWNER.id);
                            ((MobSpawnerBlockEntity) localWorld.getBlockEntity(localX + 4, localY + 1, localZ + 4))
                                    .setSpawnedEntityId(monster);

                            break;

                        // 1/6 chance of treasure room
                        case 2:
                            localWorld.setBlock(localX + 4, localY + 2, localZ + 4, Block.CHEST.id);
                            chest = (ChestBlockEntity) localWorld.getBlockEntity(localX + 4, localY + 2, localZ + 4);

                            chest.setStack(localRandom.nextInt(chest.size()), new ItemStack(Item.GOLD_INGOT, localRandom.nextInt(2, 5)));
                            chest.setStack(localRandom.nextInt(chest.size()), new ItemStack(Item.GOLD_INGOT, localRandom.nextInt(2, 5)));
                            chest.setStack(localRandom.nextInt(chest.size()), new ItemStack(Item.IRON_INGOT, localRandom.nextInt(2, 5)));

                            if (localRandom.nextBoolean())
                                chest.setStack(localRandom.nextInt(chest.size()), new ItemStack(Item.GOLDEN_APPLE, 1));

                            GenerationHelper.fillRect(localWorld, Block.SANDSTONE.id, localX + 3, localY + 1, localZ + 3, 3, 1, 3);
                            GenerationHelper.replaceRect(localWorld, Block.SANDSTONE.id, Natsucraft.LAPIS_SANDSTONE.id, localX, localY + 1, localZ, 9, 1, 9);
                            break;

                        // 1/6 chance of weird pillar room
                        case 3:
                            GenerationHelper.fillRect(localWorld, Block.SANDSTONE.id, localX + 3, localY + 1, localZ + 3, 3, 4, 3);
                            break;

                        // 1/6 chance of tomb
                        case 4:
                            GenerationHelper.fillRect(localWorld, Block.SANDSTONE.id, localX + 2, localY + 1, localZ + 3, 5, 1, 3);
                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 2, localY + 1, localZ + 3, Block.SLAB.id, 1);
                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 2, localY + 1, localZ + 5, Block.SLAB.id, 1);
                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 4, localY + 1, localZ + 3, Block.SLAB.id, 1);
                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 4, localY + 1, localZ + 5, Block.SLAB.id, 1);
                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 5, localY + 1, localZ + 3, Block.SLAB.id, 1);
                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 5, localY + 1, localZ + 5, Block.SLAB.id, 1);
                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 6, localY + 1, localZ + 3, Block.SLAB.id, 1);
                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 6, localY + 1, localZ + 5, Block.SLAB.id, 1);
                            localWorld.setBlockWithoutNotifyingNeighbors(localX + 3, localY + 1, localZ + 4, Block.GOLD_BLOCK.id);

                            localWorld.setBlock(localX + 3, localY, localZ + 4, Block.CHEST.id);
                            chest = (ChestBlockEntity) localWorld.getBlockEntity(localX + 3, localY, localZ + 4);

                            for (int i=0; i<6; i++)
                                chest.setStack(localRandom.nextInt(chest.size()), new ItemStack(Item.BONE, localRandom.nextInt(1, 5)));

                            if (localRandom.nextBoolean())
                                chest.setStack(localRandom.nextInt(chest.size()), new ItemStack(Item.IRON_SWORD, 1));

                        // 1/6 chance of hallway room
                        default:
                            break;
                    }
                }
        );

        // tunnel down
        GenerationHelper.fillRect(world, Block.SANDSTONE.id, x - 2, y - 10, z - 2, 5, 11, 5);
        GenerationHelper.fillRect(world, 0, x - 1, y - 12, z - 1, 3, 13, 3);

        // ornate pillars
        for (int ox = x - 3; ox <= x + 3; ox += 6) {
            for (int oz = z - 3; oz <= z + 3; oz += 6) {

                world.setBlockWithoutNotifyingNeighbors(ox, y, oz, Block.SANDSTONE.id);
                world.setBlockWithoutNotifyingNeighbors(ox, y + 1, oz, Block.SANDSTONE.id);
                world.setBlockWithoutNotifyingNeighbors(ox, y + 2, oz, Natsucraft.GLOWSAP_SANDSTONE.id);
                world.setBlockWithoutNotifyingNeighbors(ox, y + 3, oz, Block.SLAB.id, 1);
            }
        }

        return true;
    }
}
