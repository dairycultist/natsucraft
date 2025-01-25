package net.bluebunnex.template;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
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
    public static Item SAPPHIRE;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {

        IRON_BAUBLE = new TemplateItem(NAMESPACE.id("iron_bauble"))
                .setMaxCount(1)
                .setTranslationKey(NAMESPACE, "iron_bauble");

        IRON_BAUBLE = new TemplateItem(NAMESPACE.id("sapphire"))
                .setTranslationKey(NAMESPACE, "sapphire");
    }

    @EventListener
    public void registerEntities(EntityRegister event) {

        event.register(LichEntity.class, "Lich");
    }
}
