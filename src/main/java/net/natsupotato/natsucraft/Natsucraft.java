package net.natsupotato.natsucraft;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.GrassPatchFeature;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.event.world.biome.BiomeRegisterEvent;
import net.modificationstation.stationapi.api.event.worldgen.biome.BiomeProviderRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.block.TemplateLogBlock;
import net.modificationstation.stationapi.api.template.block.TemplateMushroomPlantBlock;
import net.modificationstation.stationapi.api.template.item.TemplateSwordItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import net.modificationstation.stationapi.api.worldgen.BiomeAPI;
import net.modificationstation.stationapi.api.worldgen.biome.BiomeBuilder;
import net.modificationstation.stationapi.api.worldgen.biome.ClimateBiomeProvider;

import java.util.Random;

import static net.minecraft.block.Block.WOOD_SOUND_GROUP;

public class Natsucraft {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();


    public static Block JUNGLE_LOG;

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

        new TemplateMushroomPlantBlock(NAMESPACE.id("glowcap"), 0)
        .setLuminance(0.8f) // 0.8f * 15f = 12f, mushrooms break at 13f
        .setTranslationKey(NAMESPACE, "glowcap");
    }


    public static Item LICH_SWORD;

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


    public static Biome JUNGLE_BIOME;

    @EventListener
    public void registerBiomes(BiomeRegisterEvent event) {

        BiomeBuilder biomeBuilder;

        biomeBuilder = BiomeBuilder.start("Jungle");
        biomeBuilder.fogColor(0x7db7ff);
        biomeBuilder.grassAndLeavesColor(0x22bd2c);
        biomeBuilder.precipitation(true);
        biomeBuilder.overworldOres();
        biomeBuilder.overworldLakes();
        biomeBuilder.passiveEntity(ChickenEntity.class, 10);
        biomeBuilder.passiveEntity(PigEntity.class, 10);
        biomeBuilder.hostileEntity(SpiderEntity.class, 10);
        biomeBuilder.hostileEntity(SlimeEntity.class, 10);
        biomeBuilder.height(60, 90);
        biomeBuilder.feature(new JungleTreeFeature());
        biomeBuilder.feature(new JungleBushFeature());
        biomeBuilder.feature(new GrassPatchFeature(Block.GRASS.id, 1));
        JUNGLE_BIOME = biomeBuilder.build();
    }

    public static ClimateBiomeProvider CLIMATE_BIOME_PROVIDER;

    @EventListener
    public void registerBiomeProvider(BiomeProviderRegisterEvent event) {

        // mystical forest

        CLIMATE_BIOME_PROVIDER = new ClimateBiomeProvider();
        // Add a biome in the temperature range of t2 - t1 and humidity of d2 - d1
        CLIMATE_BIOME_PROVIDER.addBiome(JUNGLE_BIOME, 1.0f, 0.95f, 1.0f, 0.95f);
        BiomeAPI.addOverworldBiomeProvider(Natsucraft.NAMESPACE.id("climate_biome_provider"), CLIMATE_BIOME_PROVIDER);
    }

    @EventListener
    public void registerEntities(EntityRegister event) {

        event.register(LichEntity.class, "Lich");
    }
}
