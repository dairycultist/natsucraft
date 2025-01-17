package net.bluebunnex.template;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
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
}
