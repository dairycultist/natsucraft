package net.natsupotato.natsucraft.util;

import net.minecraft.world.World;

public class GlobalPlacer implements Placer {

    private final World world;

    public GlobalPlacer(World world) {

        this.world = world;
    }

    @Override
    public void setBlock(int blockId, int x, int y, int z) {

        world.setBlockWithoutNotifyingNeighbors(x, y, z, blockId);
    }

    @Override
    public void fillRect(int blockId, int x, int y, int z, int w, int h, int l) {

        for (int ox = x; ox < x + w; ox++)
            for (int oy = y; oy < y + h; oy++)
                for (int oz = z; oz < z + l; oz++)
                    world.setBlockWithoutNotifyingNeighbors(ox, oy, oz, blockId);
    }

    @Override
    public void hollowRect(int blockId, int x, int y, int z, int w, int h, int l) {

        fillRect(blockId, x, y, z, w, h, l);
        fillRect(0, x + 1, y + 1, z + 1, w - 2, h - 2, l - 2);
    }
}
