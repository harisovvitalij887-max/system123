# Client HUD (Fabric, Minecraft 1.21.4)

Pill-style HUD: Watermark, System (Fps / MS / BPS), Coords, Potions, Armor, Cooldowns, Keybinds, Target,
Inventory, Waypoints, Party, Notifications.
Client-side and cosmetic only: it reads what the game already shows and sends nothing anywhere.

## Build
Requires JDK 21 and Gradle 8.12+ (or copy this folder into the official Fabric example-mod template).

    gradle wrapper --gradle-version 8.12
    ./gradlew build          # jar: build/libs/clienthud-1.0.0.jar
    ./gradlew runClient      # test in a dev client

Install: put the jar and Fabric API into `.minecraft/mods`.

## Use
Right Shift opens the editor. Drag = move, right-click = show/hide, wheel = scale, Shift+wheel = opacity,
Esc = save. Settings live in `config/clienthud.json`.

## Commands
    /hud wp add <name> | remove <name> | list    waypoints saved at your current position
    /hud party add <name> | remove <name>        players shown in the Party widget

## Other Minecraft versions
Code targets Yarn mappings of 1.21.4. Newer versions rename some APIs
(HudRenderCallback became HudElementRegistry, KeyBinding categories changed, mappings changed in 26.x).
Change the versions in gradle.properties and follow https://docs.fabricmc.net/develop/porting/

## Easiest ways to get the jar
- Windows: install JDK 21 + Gradle, double-click `build.bat`. Jar appears in `build/libs/`.
- No local setup: push this folder to a GitHub repo, open Actions > build > Run workflow,
  download the `clienthud-jar` artifact. Use the file WITHOUT `-sources` in its name.
