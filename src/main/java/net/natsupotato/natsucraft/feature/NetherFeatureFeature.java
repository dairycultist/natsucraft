package net.natsupotato.natsucraft.feature;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class NetherFeatureFeature extends Feature {

    private final Feature feature;
    private final int attempts;

    public NetherFeatureFeature(Feature feature, int attempts) {

        this.feature = feature;
        this.attempts = attempts;
    }

    @Override
    public boolean generate(World world, Random random, int x, int y, int z) {

        for (int i=0; i<attempts; i++) {
            feature.generate(world, random, x, random.nextInt(128), z);
        }

        return true;
    }
}
