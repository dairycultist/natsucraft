package net.bluebunnex.template;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateFoodItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class Template {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Item TOMATO;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {

        TOMATO = new TemplateFoodItem(NAMESPACE.id("tomato"), 2, false)
                .setMaxCount(8)
                .setTranslationKey(NAMESPACE, "tomato");
    }
}
