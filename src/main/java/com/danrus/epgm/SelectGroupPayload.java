package com.danrus.epgm;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

import java.util.UUID;

public record SelectGroupPayload(int slot) implements CustomPacketPayload {

    public static final StreamCodec<FriendlyByteBuf, SelectGroupPayload> STREAM_CODEC = CustomPacketPayload.codec(SelectGroupPayload::write, SelectGroupPayload::new);
    public static final CustomPacketPayload.Type<SelectGroupPayload> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath("epgm", "group"));

    private SelectGroupPayload(final FriendlyByteBuf input) {
        this(input.readInt());
    }

    private void write(final FriendlyByteBuf output) {
        output.writeInt(this.slot);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
