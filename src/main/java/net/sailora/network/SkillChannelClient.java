package net.sailora.network;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public class SkillChannelClient {

    private record SailoraPayload(byte[] data) implements CustomPacketPayload {
        static final CustomPacketPayload.Type<SailoraPayload> TYPE = new CustomPacketPayload.Type<>(
            Identifier.fromNamespaceAndPath("sailora", "skills")
        );
        static final StreamCodec<RegistryFriendlyByteBuf, SailoraPayload> CODEC = StreamCodec.of(
            (buf, value) -> buf.writeBytes(value.data()),
            buf -> {
                byte[] b = new byte[buf.readableBytes()];
                buf.readBytes(b);
                return new SailoraPayload(b);
            }
        );
        @Override public CustomPacketPayload.Type<SailoraPayload> type() { return TYPE; }
    }

    private static final byte   MSG_HANDSHAKE = 0x00;
    private static final byte   MSG_CAST      = 0x01;
    private static final byte   MSG_TOGGLE    = 0x02;
    private static final String MOD_VERSION   = "26.2";

    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(SailoraPayload.TYPE, SailoraPayload.CODEC);
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> sendHandshake());
    }

    private static void sendHandshake() {
        if (!ClientPlayNetworking.canSend(SailoraPayload.TYPE)) return;
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeByte(MSG_HANDSHAKE);
        buf.writeUtf(MOD_VERSION);
        ClientPlayNetworking.send(new SailoraPayload(toBytes(buf)));
        buf.release();
    }

    public static void sendCast(int slotIndex) {
        if (!ClientPlayNetworking.canSend(SailoraPayload.TYPE)) return;
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeByte(MSG_CAST);
        buf.writeByte(slotIndex);
        ClientPlayNetworking.send(new SailoraPayload(toBytes(buf)));
        buf.release();
    }

    public static void sendTechniqueState(boolean enabled) {
        if (!ClientPlayNetworking.canSend(SailoraPayload.TYPE)) return;
        FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
        buf.writeByte(MSG_TOGGLE);
        buf.writeBoolean(enabled);
        ClientPlayNetworking.send(new SailoraPayload(toBytes(buf)));
        buf.release();
    }

    private static byte[] toBytes(FriendlyByteBuf buf) {
        byte[] b = new byte[buf.readableBytes()];
        buf.readBytes(b);
        return b;
    }
}
