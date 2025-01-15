package net.bluebunnex.template.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.platform.Lighting;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.hit.HitResultType;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class Tooltip {

    @Unique
    private static final Item[] TOOLS = {
            Item.SHEARS,
            Item.WOODEN_AXE, Item.STONE_AXE, Item.IRON_AXE, Item.DIAMOND_AXE,
            Item.WOODEN_PICKAXE, Item.STONE_PICKAXE, Item.IRON_PICKAXE, Item.DIAMOND_PICKAXE,
            Item.WOODEN_SHOVEL, Item.STONE_SHOVEL, Item.IRON_SHOVEL, Item.DIAMOND_SHOVEL
    };

    @Shadow
    private static ItemRenderer ITEM_RENDERER = new ItemRenderer();

    @Shadow
    private Minecraft minecraft;

    @Inject(method = "render", at = @At("TAIL"))
    private void renderMixin(float tickDelta, boolean screenOpen, int mouseX, int mouseY, CallbackInfo ci) {

        // tooltip for What Am I Looking At clone
        if (this.minecraft.crosshairTarget != null) {

            TextRenderer textRenderer = this.minecraft.textRenderer;
            InGameHud hud = (InGameHud) (Object) this;

            // stolen from AchievementToast
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glEnable(3553);
            GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/achievement/bg.png"));
            GL11.glDisable(2896);

            hud.drawTexture(4, 4, 96, 202, 40, 32);
            hud.drawTexture(44, 4, 176, 202, 80, 32);

            if (this.minecraft.crosshairTarget.type == HitResultType.ENTITY) {

                Entity entity = this.minecraft.crosshairTarget.entity;

                // entity name
                textRenderer.drawWithShadow(EntityRegistry.getId(entity), 12, 11, -1);

                // health
                if (entity instanceof LivingEntity livingEntity) {

                    GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/gui/icons.png"));

                    for (int i = 0; i < livingEntity.maxHealth / 2; i++) {

                        hud.drawTexture(12 + i * 8, 21, 16, 0, 9, 9);

                        if (i * 2 + 1 < livingEntity.health) {
                            hud.drawTexture(12 + i * 8, 21, 52, 0, 9, 9);
                        }

                        if (i * 2 + 1 == livingEntity.health) {
                            hud.drawTexture(12 + i * 8, 21, 61, 0, 9, 9);
                        }
                    }
                }

            } else {

                int blockId = this.minecraft.world.getBlockId(
                        this.minecraft.crosshairTarget.blockX,
                        this.minecraft.crosshairTarget.blockY,
                        this.minecraft.crosshairTarget.blockZ
                );

                Block block = Block.BLOCKS[blockId];
                Item blockItem = block.asItem();

                Item neededTool = null;

                for (Item tool : TOOLS) {

                    if (tool.isSuitableFor(block)) {

                        neededTool = tool;
                        break;
                    }
                }

                // name + id
                textRenderer.drawWithShadow(blockItem.getTranslatedName(), 32, 16, -1);
                textRenderer.drawWithShadow(" #" + blockId, 32 + textRenderer.getWidth(blockItem.getTranslatedName()), 16, -7829368);

                // item display
                GL11.glDisable(3042);
                GL11.glEnable(32826);
                GL11.glPushMatrix();
                GL11.glRotatef(120.0F, 1.0F, 0.0F, 0.0F);
                Lighting.turnOn();
                GL11.glPopMatrix();

                ITEM_RENDERER.renderGuiItem(
                        textRenderer,
                        this.minecraft.textureManager,
                        blockId,
                        0,
                        blockItem.getTextureId(0),
                        12,
                        12
                );

                if (neededTool != null) {

                    GL11.glTranslatef(0F, 0F, 100F);

                    ITEM_RENDERER.renderGuiItem(
                            textRenderer,
                            this.minecraft.textureManager,
                            neededTool.id,
                            0,
                            neededTool.getTextureId(0),
                            7,
                            7
                    );
                }

                Lighting.turnOff();
                GL11.glDisable(32826);

                GL11.glDisable(2896); // renderGuiItem is destructive
                GL11.glDisable(2884);
            }
        }
    }
}
