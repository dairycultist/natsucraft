package net.natsupotato.natsucraft.mixin;

import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(OverworldChunkGenerator.class)
public class OverworldChunkGeneratorMixin {

//    @Shadow
//    private Random random;
//
//    @Shadow
//    private World world;
//
// // generates trees, dungeons, ores, whatever
//    @Inject(method = "decorate", at = @At("TAIL"))
//    private void decorateMixin(ChunkSource source, int x, int z, CallbackInfo ci) {
//
//        // chunk to block
//        x *= 16;
//        z *= 16;
//
//        // generate sapphire ore feature
//        int fx = x + this.random.nextInt(16);
//        int fy = this.random.nextInt(16) + this.random.nextInt(16);
//        int fz = z + this.random.nextInt(16);
//
//        (new OreFeature(Natsucraft.SAPPHIRE_ORE.id, 7)).generate(this.world, this.random, fx, fy, fz);
//    }
}
