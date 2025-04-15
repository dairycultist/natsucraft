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
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

import static net.minecraft.block.Block.WOOD_SOUND_GROUP;

public class Natsucraft {

    // TODO texture lich sword (dark and ashy and with a cool skull on its hilt or smth)
    // TODO fix lich sword holding visual bug
    // TODO add crafting recipe for wood from jungle logs

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Block JUNGLE_LOG;

    public static Item LICH_SWORD;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {

        JUNGLE_LOG = new TemplateLogBlock(NAMESPACE.id("jungle_log"))
        .setHardness(2.0F)
        .setSoundGroup(WOOD_SOUND_GROUP)
        .setTranslationKey(NAMESPACE, "jungle_log");
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
