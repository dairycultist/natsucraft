package net.natsupotato.natsucraft.util;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ChestUtil {

    public static void addLoot(Random random, ChestBlockEntity chest, int tries, Item[] items, int[] mins, int[] maxes) {

        for (int i = 0; i < tries; i++) {

            int itemIndex = random.nextInt(items.length);

            chest.setStack(random.nextInt(chest.size()), new ItemStack(items[itemIndex], random.nextInt(mins[itemIndex], maxes[itemIndex])));
        }
    }
}
