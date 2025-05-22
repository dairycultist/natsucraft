package net.natsupotato.natsucraft;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.render.entity.UndeadEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.ZombieEntityModel;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.util.Identifier;
import net.natsupotato.natsucraft.entity.LichEntity;
import net.natsupotato.natsucraft.entity.MummyEntity;

public class NatsucraftClient {

    @EventListener
    public void registerEntityRenderer(EntityRendererRegisterEvent event) {

        event.renderers.put(LichEntity.class, new UndeadEntityRenderer(new BipedEntityModel(), 0.5f));
        event.renderers.put(MummyEntity.class, new UndeadEntityRenderer(new ZombieEntityModel(), 0.5f));
    }

    @EventListener
    public void registerTextures(TextureRegisterEvent event) {

        Item.STONE_SWORD.setTextureId(
                Atlases.getGuiItems().addTexture(Identifier.of(Natsucraft.NAMESPACE, "item/copper_sword")).index
        );

        Item.STONE_PICKAXE.setTextureId(
                Atlases.getGuiItems().addTexture(Identifier.of(Natsucraft.NAMESPACE, "item/copper_pickaxe")).index
        );

        Item.STONE_AXE.setTextureId(
                Atlases.getGuiItems().addTexture(Identifier.of(Natsucraft.NAMESPACE, "item/copper_axe")).index
        );

        Item.STONE_SHOVEL.setTextureId(
                Atlases.getGuiItems().addTexture(Identifier.of(Natsucraft.NAMESPACE, "item/copper_shovel")).index
        );

        Item.STONE_HOE.setTextureId(
                Atlases.getGuiItems().addTexture(Identifier.of(Natsucraft.NAMESPACE, "item/copper_hoe")).index
        );
    }
}
