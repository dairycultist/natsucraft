package net.natsupotato.natsucraft.util;

import net.minecraft.world.World;

public class LocalPlacer {

    private final World localWorld;
    private final int localX, localY, localZ;

    public LocalPlacer(World world, int x, int y, int z) {

        this.localWorld = world;
        this.localX = x;
        this.localY = y;
        this.localZ = z;
    }

    public void setBlock(int blockId, int x, int y, int z) {

        localWorld.setBlock(blockId, x + localX, y + localY, z + localZ);
    }

    public void fillRect(int blockId, int x, int y, int z, int w, int h, int l) {

        int xb = x + localX + w;
        int yb = y + localY + h;
        int zb = z + localZ + l;
        
        for (int xa = x + localX; xa < xb; xa++)
            for (int ya = y + localY; ya < yb; ya++)
                for (int za = z + localZ; za < zb; za++)
                    localWorld.setBlockWithoutNotifyingNeighbors(xa, ya, za, blockId);
    }
}