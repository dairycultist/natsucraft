package net.natsupotato.natsucraft.mixin.biome;

import net.minecraft.world.biome.Biome;
import net.natsupotato.natsucraft.entity.MummyEntity;
import net.natsupotato.natsucraft.feature.DeepDungeonFeature;
import net.natsupotato.natsucraft.feature.DesertCryptFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class BiomeMixin {

    @Inject(method = "setName", at = @At(value = "HEAD"))
    protected void setNameMixin(String name, CallbackInfoReturnable<Biome> cir) {

        Biome self = (Biome) (Object) this;

        self.addFeature(new DeepDungeonFeature());

        if (name.equals("Desert")) {
            self.addFeature(new DesertCryptFeature());
            self.addHostileEntity(MummyEntity.class, 10);
        }
    }
}
