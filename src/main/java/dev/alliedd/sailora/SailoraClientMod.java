package dev.alliedd.sailora;

import dev.alliedd.sailora.input.SkillKeyBinds;
import dev.alliedd.sailora.network.SkillChannelClient;
import net.fabricmc.api.ClientModInitializer;

public class SailoraClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SkillKeyBinds.register();
        SkillChannelClient.register();
    }
}
