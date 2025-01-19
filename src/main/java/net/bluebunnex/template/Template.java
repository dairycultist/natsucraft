package net.bluebunnex.template;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.render.entity.UndeadEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.client.event.render.entity.EntityRendererRegisterEvent;
import net.modificationstation.stationapi.api.event.entity.EntityRegister;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class Template {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Item IRON_BAUBLE;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {

        IRON_BAUBLE = new TemplateItem(NAMESPACE.id("iron_bauble"))
                .setMaxCount(1)
                .setTranslationKey(NAMESPACE, "iron_bauble");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {

        event.register(LichEntity.class, "Lich");
    }

    @EventListener
    public void registerEntityRenderer(EntityRendererRegisterEvent event) {

        event.renderers.put(LichEntity.class, new UndeadEntityRenderer(new BipedEntityModel(), 0.5f));
    }
}
