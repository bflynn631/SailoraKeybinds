package dev.alliedd.sailora.input;

import dev.alliedd.sailora.network.SkillChannelClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public class SkillKeyBinds {

    private static final int MAX_SLOTS = 11;

    private static final KeyMapping.Category SKILL_CATEGORY =
        KeyMapping.Category.register(Identifier.fromNamespaceAndPath("sailora", "skills"));

    private static final KeyMapping[] BINDS = new KeyMapping[MAX_SLOTS];

    private static KeyMapping TOGGLE_BIND;

    private static boolean techniqueEnabled = false;

    public static void register() {
        // key.sailora.ability00 sorts before ability01-ability11 alphabetically,
        // so toggle appears at the top of the controls list.
        TOGGLE_BIND = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.sailora.ability00",
            InputConstants.Type.KEYSYM,
            InputConstants.UNKNOWN.getValue(),
            SKILL_CATEGORY
        ));

        for (int i = 0; i < MAX_SLOTS; i++) {
            int defaultKey = (i == 0) ? GLFW.GLFW_KEY_R : InputConstants.UNKNOWN.getValue();
            // Zero-pad so ability01-ability09 sort before ability10-ability11.
            BINDS[i] = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                String.format("key.sailora.ability%02d", i + 1),
                InputConstants.Type.KEYSYM,
                defaultKey,
                SKILL_CATEGORY
            ));
        }

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (TOGGLE_BIND.consumeClick()) {
                techniqueEnabled = !techniqueEnabled;
                SkillChannelClient.sendTechniqueState(techniqueEnabled);
                // Drain any queued ability clicks so toggling on never fires a skill.
                for (int i = 0; i < MAX_SLOTS; i++) BINDS[i].consumeClick();
                return;
            }
            // Always consume clicks; only send cast when technique is enabled.
            for (int i = 0; i < MAX_SLOTS; i++) {
                if (BINDS[i].consumeClick() && techniqueEnabled) {
                    SkillChannelClient.sendCast(i);
                }
            }
        });
    }
}
