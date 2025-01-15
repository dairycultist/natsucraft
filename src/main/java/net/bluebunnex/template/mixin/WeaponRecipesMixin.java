package net.bluebunnex.template.mixin;

import net.minecraft.item.Item;
import net.minecraft.recipe.WeaponRecipes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WeaponRecipes.class)
public class WeaponRecipesMixin {

    @Shadow
    private Object[][] items;

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(CallbackInfo ci) {

        this.items = new Object[][] {
            { Item.IRON_INGOT, Item.DIAMOND, Item.GOLD_INGOT },
            { Item.IRON_SWORD, Item.DIAMOND_SWORD, Item.GOLDEN_SWORD }
        };
    }
}
