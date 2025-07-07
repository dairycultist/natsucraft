package net.dairycultist.megaliths;

import com.matthewperiut.retrocommands.api.Command;
import com.matthewperiut.retrocommands.api.CommandRegistry;
import com.matthewperiut.retrocommands.util.SharedCommandSource;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.player.PlayerEntity;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeModificationEvent;
import net.dairycultist.megaliths.feature.MegalithFeature;

public class Natsucraft implements ModInitializer {

    @EventListener
    public void registerEntitySpawn(BiomeModificationEvent event) {

        event.biome.addFeature(new MegalithFeature());
    }

    public void onInitialize() {

        if (FabricLoader.getInstance().isModLoaded("retrocommands")) {

            CommandRegistry.add(new Command() {

                @Override
                public void command(SharedCommandSource commandSource, String[] parameters) {

                    PlayerEntity p = commandSource.getPlayer();

                    new MegalithFeature().place(
                            p.world,
                            p.world.random,
                            (int) p.x,
                            p.world.getTopY((int) p.x, (int) p.z),
                            (int) p.z
                    );
                }

                @Override
                public String name() {
                    return "place";
                }

                @Override
                public void manual(SharedCommandSource commandSource) {
                    commandSource.sendFeedback("Places a MegalithFeature");
                }
            });
        }
    }
}
