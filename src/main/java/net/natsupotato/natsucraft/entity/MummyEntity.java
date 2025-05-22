package net.natsupotato.natsucraft.entity;

import net.minecraft.entity.mob.MonsterEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.natsupotato.natsucraft.Natsucraft;

public class MummyEntity extends MonsterEntity {

    public MummyEntity(World world) {
        super(world);

        this.texture = "/assets/natsucraft/stationapi/textures/entity/mummy.png";
        this.movementSpeed = 0.5F;
        this.attackDamage = 5;
    }

    protected String getRandomSound() {
        return "mob.zombie";
    }

    protected String getHurtSound() {
        return "mob.zombiehurt";
    }

    protected String getDeathSound() {
        return "mob.zombiedeath";
    }

    protected int getDroppedItemId() { return Natsucraft.FABRIC.id; }
}
