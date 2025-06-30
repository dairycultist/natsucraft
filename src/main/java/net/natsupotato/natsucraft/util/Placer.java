package net.natsupotato.natsucraft.util;

import net.minecraft.block.entity.BlockEntity;

public interface Placer {

    interface BlockLambda {

        int getBlock(int prevBlockId);
    }

    void setBlock(int blockId, int x, int y, int z);
    void setBlock(int blockId, int meta, int x, int y, int z);

    BlockEntity setBlockEntity(int blockId, int x, int y, int z);

    void fillRect(int blockId, int x, int y, int z, int w, int h, int l);
    void fillRect(int blockId, int meta, int x, int y, int z, int w, int h, int l);

    void hollowRect(int blockId, int x, int y, int z, int w, int h, int l);
    void hollowRect(int blockId, int meta, int x, int y, int z, int w, int h, int l);

    void replaceRect(BlockLambda block, int x, int y, int z, int w, int h, int l);
}
