package net.bluebunnex.template;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.template.item.TemplateAxeItem;
import net.modificationstation.stationapi.api.template.item.TemplateFoodItem;
import net.modificationstation.stationapi.api.template.item.TemplatePickaxeItem;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class Template {

    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    public static Item FLINT_AXE;
    public static Item FLINT_PICKAXE;

    public static Item TOMATO;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {

        FLINT_AXE = new TemplateAxeItem(NAMESPACE.id("flint_axe"), ToolMaterial.STONE)
                .setTranslationKey(NAMESPACE, "flint_axe");

        FLINT_PICKAXE = new TemplatePickaxeItem(NAMESPACE.id("flint_pickaxe"), ToolMaterial.STONE)
                .setTranslationKey(NAMESPACE, "flint_pickaxe");

        TOMATO = new TemplateFoodItem(NAMESPACE.id("tomato"), 2, false)
                .setMaxCount(8)
                .setTranslationKey(NAMESPACE, "tomato");
    }
}
