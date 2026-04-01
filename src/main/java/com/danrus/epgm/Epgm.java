package com.danrus.epgm;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Epgm implements ModInitializer {
	public static final String MOD_ID = "epgm";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		PayloadTypeRegistry.serverboundPlay().register(SelectGroupPayload.TYPE, SelectGroupPayload.STREAM_CODEC);
		PayloadTypeRegistry.clientboundPlay().register(SelectGroupPayload.TYPE, SelectGroupPayload.STREAM_CODEC);

		ServerPlayNetworking.registerGlobalReceiver(SelectGroupPayload.TYPE, (payload, context) -> {
			GroupSelectHelper.selectGroup(payload.slot(), context.player());
		});
	}
}