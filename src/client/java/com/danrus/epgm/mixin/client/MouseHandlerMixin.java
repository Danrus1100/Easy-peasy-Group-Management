package com.danrus.epgm.mixin.client;

import com.danrus.epgm.EpgmClient;
import com.danrus.epgm.GroupSelectHelper;
import com.danrus.epgm.SelectGroupPayload;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @WrapOperation(
            method = "onScroll",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;setSelectedSlot(I)V")
    )
    private void epgm$wrapScrollIfSelectingGroup(Inventory instance, int selected, Operation<Void> original) {
        if (EpgmClient.GROUP_SELECT_MODIFIER.isDown()) {
            GroupSelectHelper.selectFromWheel(selected, Minecraft.getInstance().player);
            if (Minecraft.getInstance().player != null) {
                ClientPlayNetworking.send(new SelectGroupPayload(Minecraft.getInstance().player.getSelectedGroup().id() + selected));
            }
            return;
        }
        original.call(instance, selected);
    }
}
