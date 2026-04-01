package com.danrus.epgm;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class EpgmClient implements ClientModInitializer {

	public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(
			Identifier.fromNamespaceAndPath(Epgm.MOD_ID, "category")
	);

	public static final KeyMapping GROUP_SELECT_MODIFIER = KeyMappingHelper.registerKeyMapping( new KeyMapping(
			"key." + Epgm.MOD_ID + ".group_modifier",
			InputConstants.Type.KEYSYM,
			GLFW.GLFW_KEY_LEFT_ALT,
			CATEGORY
	));

	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
	}
}