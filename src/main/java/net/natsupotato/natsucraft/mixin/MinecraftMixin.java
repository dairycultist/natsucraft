package net.natsupotato.natsucraft.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {

    @Shadow
    public ClientPlayerEntity player;

    @Inject(method = "setWorld(Lnet/minecraft/world/World;Ljava/lang/String;Lnet/minecraft/entity/player/PlayerEntity;)V", at = @At(value = "TAIL"))
    public void setWorldMixin(World world, String message, PlayerEntity player, CallbackInfo ci) {

        if (this.player != null) {

            boolean canGiveStuff = true;

            for (int i = 0; i < this.player.inventory.size(); i++) {

                if (this.player.inventory.getStack(i) != null) {

                    canGiveStuff = false;
                    break;
                }
            }

            if (canGiveStuff)
                giveStuff();
        }
    }

    @Inject(method = "respawnPlayer", at = @At(value = "TAIL"))
    public void respawnPlayerMixin(boolean worldSpawn, int dimension, CallbackInfo ci) {

        giveStuff();
    }

    @Unique
    private void giveStuff() {

        this.player.inventory.addStack(new ItemStack(Item.STONE_SWORD, 1));
        this.player.inventory.addStack(new ItemStack(Item.STONE_PICKAXE, 1));
        this.player.inventory.addStack(new ItemStack(Item.STONE_AXE, 1));
    }
}
