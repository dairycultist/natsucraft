package net.bluebunnex.template;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MonsterEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LichEntity extends MonsterEntity {

    private static final ItemStack HELD_ITEM = new ItemStack(Item.IRON_SWORD, 1);

    public LichEntity(World world) {
        super(world);

        this.texture = "/assets/template/stationapi/textures/entity/lich.png";

        this.health = 200;
        this.attackDamage = 12;
        this.fireImmune = true;
    }

    @Override
    public boolean damage(Entity damageSource, int amount) {

        // prevent lich from being damaged by its own skeletons
        if (damageSource instanceof SkeletonEntity)
            return false;

        int prevHealth = this.health;

        boolean didDamage = super.damage(damageSource, amount);

        if (didDamage) {

            if (
                    (prevHealth > 150 && this.health <= 150) ||
                    (prevHealth > 100 && this.health <= 100) ||
                    (prevHealth >  50 && this.health <=  50)
            ) {
                spawnBackup();
            }
        }

        return didDamage;
    }

    private void spawnBackup() {

        Entity[] skeletons = {
                new SkeletonEntity(world),
                new SkeletonEntity(world),
                new SkeletonEntity(world)
        };

        double dist = 3;

        skeletons[0].setPosition(this.x, this.y, this.z + dist);
        skeletons[1].setPosition(this.x + 0.866 * dist, this.y, this.z - 0.5 * dist);
        skeletons[2].setPosition(this.x - 0.866 * dist, this.y, this.z - 0.5 * dist);

        for (Entity skeleton : skeletons) {

            for (int i = 0; i < 15; i++) {

                this.world.addParticle(
                        "smoke",
                        skeleton.x,
                        skeleton.y + 0.5,
                        skeleton.z,
                        (Math.random() - 0.5) / 5,
                        (Math.random() - 0.5) / 5,
                        (Math.random() - 0.5) / 5
                );
            }

            this.world.spawnEntity(skeleton);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public ItemStack getHeldItem() {
        return HELD_ITEM;
    }

    @Override
    protected void dropItems() {
        this.dropItem(Item.IRON_SWORD.id, 1);
    }

    @Override
    protected String getRandomSound() {
        return "mob.skeleton";
    }

    @Override
    protected String getHurtSound() {
        return "mob.skeletonhurt";
    }

    @Override
    protected String getDeathSound() {
        return "mob.skeletonhurt";
    }
}
