package net.bluebunnex.template;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;

public class EctogelBlock extends TemplateBlock {

    public EctogelBlock(Identifier identifier) {
        super(identifier, Material.SPONGE);

        this.setSoundGroup(EctogelBlock.GRAVEL_SOUND_GROUP);
    }

    @Override
    public Box getCollisionShape(World world, int x, int y, int z) {
        float var5 = 0.125F;
        return Box.createCached((double)x, (double)y, (double)z, (double)(x + 1), (double)((float)(y + 1) - var5), (double)(z + 1));
    }

    @Override
    public void onEntityCollision(World world, int x, int y, int z, Entity entity) {

        double speed = MathHelper.sqrt(entity.velocityX * entity.velocityX + entity.velocityZ * entity.velocityZ);

        if (speed > 0.2 && speed < 0.4) {

            entity.velocityX *= 1.5;
            entity.velocityZ *= 1.5;

            if (Math.random() > 0.9)
                world.playSound(x, y, z, "mob.slime", 0.6f, 1f);
        }
    }
}
