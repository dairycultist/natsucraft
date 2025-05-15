package net.natsupotato.natsucraft.mixin.tool;

import net.minecraft.item.Item;
import net.minecraft.recipe.ToolRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToolRecipes.class)
public class ToolRecipesMixin {

    @Shadow
    private Object[][] items;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void initMixin(CallbackInfo ci) {

        this.items = new Object[][] {
                {Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT},
                {Item.IRON_PICKAXE, Item.DIAMOND_PICKAXE, Item.GOLDEN_PICKAXE},
                {Item.IRON_SHOVEL, Item.DIAMOND_SHOVEL, Item.GOLDEN_SHOVEL},
                {Item.IRON_AXE, Item.DIAMOND_AXE, Item.GOLDEN_AXE},
                {Item.IRON_HOE, Item.DIAMOND_HOE, Item.GOLDEN_HOE}
        };
    }
}
