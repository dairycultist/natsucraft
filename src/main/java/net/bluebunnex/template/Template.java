package net.bluebunnex.template;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

import java.util.Random;

import static net.minecraft.block.Block.STONE_SOUND_GROUP;

public class Template {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Block SAPPHIRE_ORE;

    public static Item SAPPHIRE;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {

        SAPPHIRE_ORE = new TemplateBlock(NAMESPACE.id("sapphire_ore"), Material.SPONGE) {

            public int getDroppedItemId(int blockMeta, Random random) {
                return SAPPHIRE.id;
            }
        }
        .setHardness(1.5f) // copied from stone
        .setResistance(10.0f)
        .setSoundGroup(STONE_SOUND_GROUP)
        .setTranslationKey(NAMESPACE, "sapphire_ore");
    }

    @EventListener
    public void registerItems(ItemRegistryEvent event) {

        SAPPHIRE = new TemplateItem(NAMESPACE.id("sapphire"))
                .setTranslationKey(NAMESPACE, "sapphire");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {

        event.register(LichEntity.class, "Lich");
    }
}
