package com.danrus.epgm;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.livingblock.LivingBlockGroup;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GroupSelectHelper {

    public static void selectFromWheel(int wheel) {
        selectGroup( group -> group.id() + wheel );
    }

    public static void selectFromSlot(int slot) {
        if (slot > 8 || slot < 0) return;
        selectGroup(g -> slot);
    }

    private static void selectGroup(Function<LivingBlockGroup, Integer> selector) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) return;
        LivingBlockGroup group = player.getSelectedGroup();
        LivingBlockGroup nextGroup = LivingBlockGroup.BY_ID.apply(selector.apply(group));
        player.setSelectedGroup(nextGroup);
        findAll(player, Items.GROUP_ACTION).forEach(itemStack -> itemStack.set(DataComponents.DYED_COLOR, new DyedItemColor(nextGroup.color())));
        Component name = Component.translatable(
                "item.minecraft.select_group_action.details", Component.translatable("living_blocks.group." + nextGroup.getSerializedName())
        );
        player.sendOverlayMessage(name);
    }

    public static List<ItemStack> findAll(Player player, Item item) {
        Inventory inv = player.getInventory();
        List<ItemStack> result = new ArrayList<>();

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty() && stack.getItem() == item) {
                result.add(stack);
            }
        }

        return result;
    }

}
