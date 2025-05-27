package net.natsupotato.natsucraft.feature;

import net.minecraft.block.Block;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class TowerFeature extends Feature {

    private static final int FLOORS = 4;

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {

        if (random.nextInt(4) != 0)
            return false;

        if (!Block.BLOCKS_OPAQUE[world.getBlockId(x, y - 1, z)])
            return false;

        // foundation
        GenerationHelper.replaceRect(world, 0, Block.COBBLESTONE.id, x, y - 6, z, 10, 6, 10);

        // bottom floor
        GenerationHelper.fillHollowRect(world, Block.COBBLESTONE.id, x, y, z, 10, 5, 10);
        GenerationHelper.fillRect(world, Block.DIRT.id, x + 1, y, z + 1, 8, 1, 8);

        // door
        GenerationHelper.fillRect(world, Block.COBBLESTONE.id, x + 2, y, z - 1, 6, 5, 1);
        GenerationHelper.fillRect(world, 0, x + 3, y + 1, z - 1, 4, 3, 2);

        for (int i = 1; i < FLOORS; i++) {

            // floor
            GenerationHelper.generateMaze(world, random, x, y + i * 5, z, 5, 5, 5, 2, 2, Block.COBBLESTONE.id,
                    (World localWorld, Random localRandom, int localX, int localY, int localZ) -> {

                        if (localRandom.nextInt(10) == 0) {

                            GenerationHelper.replaceRectRandomly(world, random, 2, Block.COBBLESTONE.id, Block.MOSSY_COBBLESTONE.id, localX + 1, localY, localZ + 1, 3, 1, 3);

                            localWorld.setBlock(localX + 2, localY + 1, localZ + 2, Block.SPAWNER.id);
                            ((MobSpawnerBlockEntity) localWorld.getBlockEntity(localX + 2, localY + 1, localZ + 2))
                                    .setSpawnedEntityId("Zombie");
                        }
                    }
            );

            // ladder connecting down
            if (i % 2 == 0)
                GenerationHelper.fillRectMeta(world, Block.LADDER.id, 5, x + 1, y - 4 + i * 5, z + 2, 1, 6, 1);
            else
                GenerationHelper.fillRectMeta(world, Block.LADDER.id, 4, x + 8, y - 4 + i * 5, z + 7, 1, 6, 1);
        }

        GenerationHelper.replaceRectRandomly(world, random, 6, Block.COBBLESTONE.id, Block.MOSSY_COBBLESTONE.id, x, y - 6, z, 10, 6 + 5 * FLOORS, 10);

        return true;
    }
}
