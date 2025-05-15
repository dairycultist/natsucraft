package net.natsupotato.natsucraft;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateLogBlock;
import net.modificationstation.stationapi.api.template.block.TemplateMushroomPlantBlock;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

import java.util.Random;

import static net.minecraft.block.Block.DIRT_SOUND_GROUP;
import static net.minecraft.block.Block.WOOD_SOUND_GROUP;

public class Natsucraft {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Block JUNGLE_LOG;
    public static Block FAIRY_SAP_LOG;
    public static Block GLOWCAP;

    public static Item LICH_SWORD;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {

        JUNGLE_LOG = new TemplateLogBlock(NAMESPACE.id("jungle_log")) {

            public int getDroppedItemId(int blockMeta, Random random) {
                return this.id;
            }

        }
        .setHardness(2.0F)
        .setSoundGroup(WOOD_SOUND_GROUP)
        .setTranslationKey(NAMESPACE, "jungle_log");

        FAIRY_SAP_LOG = new TemplateLogBlock(NAMESPACE.id("fairy_sap_log")) {

            public int getDroppedItemId(int blockMeta, Random random) {
                return Item.DIAMOND.id; // TEMP
            }

        }
        .setHardness(2.0F)
        .setSoundGroup(WOOD_SOUND_GROUP)
        .setTranslationKey(NAMESPACE, "fairy_sap_log");

        GLOWCAP = new TemplateMushroomPlantBlock(NAMESPACE.id("glowcap"), 0)
        .setLuminance(0.8f) // 0.8f * 15f = 12f, mushrooms break at 13f
        .setSoundGroup(DIRT_SOUND_GROUP)
        .setTranslationKey(NAMESPACE, "glowcap");
    }

    @EventListener
    public void registerItems(ItemRegistryEvent event) {

        LICH_SWORD = new TemplateSwordItem(NAMESPACE.id("lich_sword"), ToolMaterial.DIAMOND) {

            public int getAttackDamage(Entity attackedEntity) {
                return super.getAttackDamage(attackedEntity) + 1; // one more damage than diamond
            }

        }
        .setMaxDamage(ToolMaterial.DIAMOND.getDurability() * 2) // twice that of a diamond sword
        .setTranslationKey(NAMESPACE, "lich_sword");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {

        event.register(LichEntity.class, "Lich");
    }
}
