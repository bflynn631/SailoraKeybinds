# Sailora Client

**The official client-side mod for the Sailora JujutsuKaisen Minecraft server.**

Sailora Client adds a dedicated keybind system that lets you cast your technique's abilities directly from your keyboard — no item-swapping required. The mod communicates seamlessly with the server to activate the right skill at the right time.

---

## Features

- **Technique Toggle** — Enable or disable your technique with a single key press
- **11 Skill Slots** — Bind any ability in your technique's lineup to its own key
- **Zero Latency Casting** — Inputs are sent directly to the server over a custom channel the moment you press the key
- **Plug & Play** — Connects automatically when you join the server; no configuration files needed

---

## Requirements

| Requirement | Version |
|-------------|---------|
| Minecraft | 26.2 (Lunar Client) |
| Fabric Loader | ≥ 0.19.3 |
| Fabric API | 0.156.0+26.2 |

> **Lunar Client users:** install the mod through your Lunar Client profile's mod folder. The mod is client-side only and does not need to be installed on the server.

---

## Installation

1. Download the latest `.jar` from the [Releases](../../releases/latest) page
2. Place it in your Fabric mods folder:
   - **Lunar Client:** `~/.lunarclient/profiles/<profile>/mods/fabric-<version>/`
   - **Standard Fabric:** `~/.minecraft/mods/`
3. Launch the game and join the server

---

## Keybinds

All keybinds are unbound by default except **Ability 01**, which defaults to **R**.  
Configure them in **Options → Controls → Sailora**.

| Keybind | Default | Description |
|---------|---------|-------------|
| Enable Technique | _unbound_ | Toggles your technique on/off |
| Technique Ability 01 | R | Casts slot 1 |
| Technique Ability 02–11 | _unbound_ | Casts slots 2–11 |

> Abilities only fire while your technique is **enabled**. Toggling it off while mid-cast is safe.

---

## License

All Rights Reserved — © Alliedd. This mod is provided for use on the Sailora server only.
