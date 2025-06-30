package net.natsupotato.natsucraft.util;

public interface Placer {

    // might want an overload with { int meta } or smth

    public void setBlock(int blockId, int x, int y, int z);
    public void setBlock(int blockId, int meta, int x, int y, int z);

    public void fillRect(int blockId, int x, int y, int z, int w, int h, int l);
    public void fillRect(int blockId, int meta, int x, int y, int z, int w, int h, int l);

    public void hollowRect(int blockId, int x, int y, int z, int w, int h, int l);
    public void hollowRect(int blockId, int meta, int x, int y, int z, int w, int h, int l);
}
