package net.sailora;

import net.sailora.input.SkillKeyBinds;
import net.sailora.network.SkillChannelClient;
import net.fabricmc.api.ClientModInitializer;

public class SailoraClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SkillKeyBinds.register();
        SkillChannelClient.register();
    }
}
