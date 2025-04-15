package net.natsupotato.natsucraft;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateLogBlock;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

import static net.minecraft.block.Block.WOOD_SOUND_GROUP;

public class Natsucraft {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Block JUNGLE_LOG;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {

        JUNGLE_LOG = new TemplateLogBlock(NAMESPACE.id("jungle_log"))
        .setHardness(2.0F)
        .setSoundGroup(WOOD_SOUND_GROUP)
        .setTranslationKey(NAMESPACE, "jungle_log");
    }

    @EventListener
    public void registerItems(ItemRegistryEvent event) {

//        SAPPHIRE = new TemplateItem(NAMESPACE.id("sapphire"))
//                .setTranslationKey(NAMESPACE, "sapphire");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {

        event.register(LichEntity.class, "Lich");
    }
}
