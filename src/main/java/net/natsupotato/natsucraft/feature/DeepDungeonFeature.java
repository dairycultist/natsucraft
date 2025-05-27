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

public class DeepDungeonFeature extends Feature {

    private static final int FLOORS = 9;

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {

        // TODO make spawn 3 times more often in Jungle biomes

        if (random.nextInt(96) != 0)
            return false;

        if (!Block.BLOCKS_OPAQUE[world.getBlockId(x, y - 1, z)])
            return false;

        // top floor
        GenerationHelper.fillHollowRect(world, Block.COBBLESTONE.id, x, y, z, 10, 5, 10);
        GenerationHelper.fillRect(world, Block.DIRT.id, x + 1, y, z + 1, 8, 1, 8);

        // door
        GenerationHelper.fillRect(world, Block.COBBLESTONE.id, x + 2, y, z - 1, 6, 5, 1);
        GenerationHelper.fillRect(world, 0, x + 3, y + 1, z - 1, 4, 3, 2);

        for (int i = 1; i < FLOORS; i++) {

            int floorY = y - i * 5;
            final int DIAMOND_CHANCE = FLOORS * 2 - i;

            // floor
            GenerationHelper.generateMaze(world, random, x, floorY, z, 5, 5, 5, 2, 2, Block.COBBLESTONE.id,

                    (World localWorld, Random localRandom, int localX, int localY, int localZ) -> {

                        if (localRandom.nextInt(6) == 0) {

                            GenerationHelper.replaceRectRandomly(world, random, 2, Block.COBBLESTONE.id, Block.MOSSY_COBBLESTONE.id, localX + 1, localY, localZ + 1, 3, 1, 3);

                            localWorld.setBlock(localX + 2, localY + 1, localZ + 2, Block.SPAWNER.id);
                            ((MobSpawnerBlockEntity) localWorld.getBlockEntity(localX + 2, localY + 1, localZ + 2))
                                    .setSpawnedEntityId("Zombie");

                        } else if (localRandom.nextInt(4) == 0) {

                            GenerationHelper.replaceRectRandomly(world, random, 5, Block.COBBLESTONE.id, Block.IRON_ORE.id, localX + 1, localY, localZ + 1, 3, 1, 3);

                            if (localRandom.nextInt(DIAMOND_CHANCE) == 0)
                                GenerationHelper.replaceRectRandomly(world, random, 5, Block.COBBLESTONE.id, Block.DIAMOND_ORE.id, localX + 1, localY, localZ + 1, 3, 1, 3);

                        } else if (localRandom.nextInt(4) == 0) {

                            ChestBlockEntity chest = GenerationHelper.lootChest(world, random, localX + 2, localY + 1, localZ + 2, 10,
                                    new Item[] { Item.IRON_INGOT, Item.GOLD_INGOT, Item.STRING, Item.LEATHER, Item.SEEDS, Natsucraft.BANDAGE },
                                    new int[] { 3, 3, 1, 1, 1, 1 },
                                    new int[] { 5, 5, 4, 4, 4, 3 }
                            );

                            if (localRandom.nextInt(32) == 0)
                                chest.setStack(random.nextInt(chest.size()), new ItemStack(Item.RECORD_THIRTEEN));
                        }
                    }
            );

            // get mossier as you go down
            GenerationHelper.replaceRectRandomly(world, random, FLOORS + 1 - i, Block.COBBLESTONE.id, Block.MOSSY_COBBLESTONE.id, x, y - i * 5, z, 10, 5, 10);

            // ladder connecting up
            if (i % 2 == 0)
                GenerationHelper.fillRectMeta(world, Block.LADDER.id, 5, x + 1, floorY + 1, z + 2, 1, 6, 1);
            else
                GenerationHelper.fillRectMeta(world, Block.LADDER.id, 4, x + 8, floorY + 1, z + 7, 1, 6, 1);
        }

        return true;
    }
}
