package com.danrus.epgm.mixin.client;

import com.danrus.epgm.EpgmClient;
import com.danrus.epgm.GroupSelectHelper;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@WrapOperation(
			method = "handleKeybinds",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;setSelectedSlot(I)V")
	)
	private void epgm$selectFromSlot(Inventory instance, int selected, Operation<Void> original) {
		if (EpgmClient.GROUP_SELECT_MODIFIER.isDown()) {
			GroupSelectHelper.selectFromSlot(selected);
			return;
		}
		original.call(instance, selected);
	}
}