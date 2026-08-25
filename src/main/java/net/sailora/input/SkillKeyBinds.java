package net.sailora.input;

import net.sailora.network.SkillChannelClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import com.mojang.blaze3d.platform.InputConstants;

public class SkillKeyBinds {

    private static final int MAX_SLOTS = 11;

    private static final KeyMapping.Category SKILL_CATEGORY =
        KeyMapping.Category.register(Identifier.fromNamespaceAndPath("sailora", "skills"));

    private static final KeyMapping[] BINDS = new KeyMapping[MAX_SLOTS];

    private static KeyMapping TOGGLE_BIND;

    private static boolean techniqueEnabled = false;

    public static void register() {
        TOGGLE_BIND = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.sailora.ability00",
            InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(),
            SKILL_CATEGORY
        ));

        for (int i = 0; i < MAX_SLOTS; i++) {
            BINDS[i] = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                String.format("key.sailora.ability%02d", i + 1),
                InputConstants.Type.KEYSYM,
                InputConstants.UNKNOWN.getValue(),
                SKILL_CATEGORY
            ));
        }

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (TOGGLE_BIND.consumeClick()) {
                techniqueEnabled = !techniqueEnabled;
                SkillChannelClient.sendTechniqueState(techniqueEnabled);
                for (int i = 0; i < MAX_SLOTS; i++) BINDS[i].consumeClick();
                return;
            }
            for (int i = 0; i < MAX_SLOTS; i++) {
                if (BINDS[i].consumeClick() && techniqueEnabled) {
                    SkillChannelClient.sendCast(i);
                }
            }
        });
    }
}
