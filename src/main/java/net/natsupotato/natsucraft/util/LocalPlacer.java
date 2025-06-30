package net.natsupotato.natsucraft.util;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.World;

public class LocalPlacer implements Placer {

    private final World world;
    private final int localX, localY, localZ;

    public LocalPlacer(World world, int x, int y, int z) {

        this.world = world;
        this.localX = x;
        this.localY = y;
        this.localZ = z;
    }

    public void setBlock(int blockId, int x, int y, int z) {

        world.setBlockWithoutNotifyingNeighbors(x + localX, y + localY, z + localZ, blockId);
    }

    public void setBlock(int blockId, int meta, int x, int y, int z) {

        world.setBlockWithoutNotifyingNeighbors(x + localX, y + localY, z + localZ, blockId, meta);
    }

    public BlockEntity setBlockEntity(int blockId, int x, int y, int z) {

        setBlock(blockId, x, y, z);
        return world.getBlockEntity(x + localX, y + localY, z + localZ);
    }

    public void fillRect(int blockId, int x, int y, int z, int w, int h, int l) {

        int xb = x + localX + w;
        int yb = y + localY + h;
        int zb = z + localZ + l;
        
        for (int xa = x + localX; xa < xb; xa++)
            for (int ya = y + localY; ya < yb; ya++)
                for (int za = z + localZ; za < zb; za++)
                    world.setBlockWithoutNotifyingNeighbors(xa, ya, za, blockId);
    }

    public void fillRect(int blockId, int meta, int x, int y, int z, int w, int h, int l) {

        int xb = x + localX + w;
        int yb = y + localY + h;
        int zb = z + localZ + l;

        for (int xa = x + localX; xa < xb; xa++)
            for (int ya = y + localY; ya < yb; ya++)
                for (int za = z + localZ; za < zb; za++)
                    world.setBlockWithoutNotifyingNeighbors(xa, ya, za, blockId, meta);
    }

    public void hollowRect(int blockId, int x, int y, int z, int w, int h, int l) {

        fillRect(blockId, x, y, z, w, h, l);
        fillRect(0, x + 1, y + 1, z + 1, w - 2, h - 2, l - 2);
    }

    public void hollowRect(int blockId, int meta, int x, int y, int z, int w, int h, int l) {

        fillRect(blockId, meta, x, y, z, w, h, l);
        fillRect(0, x + 1, y + 1, z + 1, w - 2, h - 2, l - 2);
    }

    public void replaceRect(BlockLambda block, int x, int y, int z, int w, int h, int l) {

        int xb = x + localX + w;
        int yb = y + localY + h;
        int zb = z + localZ + l;

        for (int xa = x + localX; xa < xb; xa++)
            for (int ya = y + localY; ya < yb; ya++)
                for (int za = z + localZ; za < zb; za++)
                    world.setBlockWithoutNotifyingNeighbors(xa, ya, za, block.getBlock(world.getBlockId(xa, ya, za)));
    }
}