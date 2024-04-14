package io.github.ragnaraven.eoarmaments.network;

import io.github.ragnaraven.eoarmaments.EnderObsidianArmorsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class PacketHandler {
    private static final int PROTOCOL_VERSION = 1;
    public static final SimpleChannel INSTANCE =
            ChannelBuilder.named(new ResourceLocation(EnderObsidianArmorsMod.MOD_ID, "networking"))
                    .clientAcceptedVersions((status, version) -> version == PROTOCOL_VERSION)
                    .serverAcceptedVersions((status, version) -> version == PROTOCOL_VERSION)
                    .networkProtocolVersion(PROTOCOL_VERSION)
                    .simpleChannel();

    public static void register() {
        INSTANCE.messageBuilder(SGuiAbilityPacket.class, NetworkDirection.PLAY_TO_SERVER)
                .encoder(SGuiAbilityPacket::encode)
                .decoder(SGuiAbilityPacket::decode)
                .consumerMainThread(SGuiAbilityPacket::handle)
                .add();
    }

    public static void sendToServer(Object msg) {
        INSTANCE.send(msg, PacketDistributor.SERVER.noArg());
    }

    public static void sendToPlayer(Object msg, net.minecraft.server.level.ServerPlayer player) {
        INSTANCE.send(msg, PacketDistributor.PLAYER.with(player));
    }

    public static void sendToAll(Object msg) {
        INSTANCE.send(msg, PacketDistributor.ALL.noArg());
    }
}