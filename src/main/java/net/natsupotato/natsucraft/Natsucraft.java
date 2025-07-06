package net.natsupotato.natsucraft;

import com.matthewperiut.retrocommands.api.Command;
import com.matthewperiut.retrocommands.api.CommandRegistry;
import com.matthewperiut.retrocommands.util.SharedCommandSource;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateFoodItem;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import net.natsupotato.natsucraft.entity.LichEntity;
import net.natsupotato.natsucraft.entity.MummyEntity;
import net.natsupotato.natsucraft.feature.MegalithFeature;

public class Natsucraft implements ModInitializer {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE = Null.get();

    public static Item LICH_SWORD;
    public static Item CHICKEN_RAW;
    public static Item CHICKEN_COOKED;
    public static Item FABRIC;
    public static Item BANDAGE;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {

    }

    @EventListener
    public void registerItems(ItemRegistryEvent event) {

        LICH_SWORD = new TemplateSwordItem(NAMESPACE.id("lich_sword"), ToolMaterial.DIAMOND) {

            public int getAttackDamage(Entity attackedEntity) {
                return super.getAttackDamage(attackedEntity) + 1; // one more damage than diamond
            }
        }
        .setTranslationKey(NAMESPACE, "lich_sword");

        CHICKEN_RAW = new TemplateFoodItem(NAMESPACE.id("chicken_raw"), 2, true)
        .setTranslationKey(NAMESPACE, "chicken_raw");

        CHICKEN_COOKED = new TemplateFoodItem(NAMESPACE.id("chicken_cooked"), 6, true)
        .setTranslationKey(NAMESPACE, "chicken_cooked");

        FABRIC = new TemplateItem(NAMESPACE.id("fabric"))
                .setTranslationKey(NAMESPACE, "fabric");

        BANDAGE = new TemplateFoodItem(NAMESPACE.id("bandage"), 4, false)
                .setMaxCount(8)
                .setTranslationKey(NAMESPACE, "bandage");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {

        event.register(LichEntity.class, "Lich");
        event.register(MummyEntity.class, "Mummy");
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
