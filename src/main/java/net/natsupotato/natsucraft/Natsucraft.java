package net.natsupotato.natsucraft;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateLogBlock;
import net.modificationstation.stationapi.api.template.block.TemplateMushroomPlantBlock;
import net.modificationstation.stationapi.api.template.block.TemplateSandstoneBlock;
import net.modificationstation.stationapi.api.template.item.TemplateFoodItem;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import net.natsupotato.natsucraft.entity.LichEntity;
import net.natsupotato.natsucraft.entity.MummyEntity;
import net.natsupotato.natsucraft.mixin.tool.ToolItemAccessor;

import java.util.Random;

import static net.minecraft.block.Block.*;

public class Natsucraft {

    @Entrypoint.Namespace
    public static Namespace NAMESPACE = Null.get();

    public static Block LAPIS_SANDSTONE;
    public static Block GLOWSAP_SANDSTONE;
    public static Block JUNGLE_LOG;
    public static Block GLOWSAP_LOG;
    public static Block GLOWCAP;

    public static Item LICH_SWORD;
    public static Item GLOWSAP;
    public static Item CHICKEN_RAW;
    public static Item CHICKEN_COOKED;
    public static Item FABRIC;
    public static Item BANDAGE;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {

        LAPIS_SANDSTONE = new TemplateSandstoneBlock(NAMESPACE.id("lapis_sandstone"))
        .setHardness(0.8F)
        .setSoundGroup(STONE_SOUND_GROUP)
        .setTranslationKey(NAMESPACE, "lapis_sandstone");

        GLOWSAP_SANDSTONE = new TemplateSandstoneBlock(NAMESPACE.id("glowsap_sandstone"))
        .setLuminance(0.8f)
        .setHardness(0.8F)
        .setSoundGroup(STONE_SOUND_GROUP)
        .setTranslationKey(NAMESPACE, "glowsap_sandstone");

        JUNGLE_LOG = new TemplateLogBlock(NAMESPACE.id("jungle_log")) {

            public int getDroppedItemId(int blockMeta, Random random) {
                return this.id;
            }
        }
        .setHardness(2.0F)
        .setSoundGroup(WOOD_SOUND_GROUP)
        .setTranslationKey(NAMESPACE, "jungle_log");

        GLOWSAP_LOG = new TemplateLogBlock(NAMESPACE.id("glowsap_log")) {

            public int getDroppedItemId(int blockMeta, Random random) {
                return GLOWSAP.id;
            }

            public int getDroppedItemCount(Random random) {
                return random.nextInt(2, 5);
            }

            public void afterBreak(World world, PlayerEntity playerEntity, int x, int y, int z, int meta) {

                playerEntity.increaseStat(Stats.MINE_BLOCK[this.id], 1);

                ItemStack stack = playerEntity.getHand();

                // only drop items when mined with iron or diamond tier (since Material.WOOD usually drops regardless of tool requirement)
                if (stack != null && stack.getItem() instanceof AxeItem axe && ((ToolItemAccessor) axe).getToolMaterial().getMiningLevel() >= 2)
                    this.dropStacks(world, x, y, z, meta);
            }
        }
        .setLuminance(0.5f)
        .setHardness(2.0F)
        .setSoundGroup(WOOD_SOUND_GROUP)
        .setTranslationKey(NAMESPACE, "glowsap_log");

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
        .setTranslationKey(NAMESPACE, "lich_sword");

        GLOWSAP = new TemplateItem(NAMESPACE.id("glowsap"))
        .setTranslationKey(NAMESPACE, "glowsap");

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
}
