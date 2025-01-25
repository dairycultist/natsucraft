package net.natsupotato.natsucraft;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.render.entity.UndeadEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;

public class TemplateClient {

    @EventListener
    public void registerEntityRenderer(EntityRendererRegisterEvent event) {

        event.renderers.put(LichEntity.class, new UndeadEntityRenderer(new BipedEntityModel(), 0.5f));
    }
}
