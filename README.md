# Project Structure
## Dont GO TO ANOTHER ONE
``` src/
└── main/
├── java/
│   └── com/
│       └── bllry/
│           └── client/
│               ├── core/
│               │   ├── events/
│               │   │   ├── Event.java
│               │   │   ├── EventManager.java
│               │   │   ├── EventTarget.java
│               │   │   └── KeyEvent.java
│               │   └── modules/
│               │       ├── Category.java
│               │       ├── Module.java
│               │       └── ModuleManager.java
│               ├── modules/
│               │   ├── combat/
│               │   │   └── KillAura.java
│               │   ├── movement/
│               │   │   └── Speed.java
│               │   └── render/
│               │       └── ESP.java
│               ├── commands/
│               │   ├── Command.java
│               │   ├── CommandManager.java
│               │   └── HelpCommand.java
│               ├── gui/
│               │   ├── clickgui/
│               │   │   ├── ClickGUI.java
│               │   │   ├── Panel.java
│               │   │   └── Button.java
│               │   └── hud/
│               │       ├── HudElement.java
│               │       ├── HudManager.java
│               │       └── InfoHud.java
│               ├── mixins/
│               │   ├── KeyboardMixin.java
│               │   └── GameRendererMixin.java
│               ├── utils/
│               │   ├── RenderUtils.java
│               │   └── KeyboardUtils.java
│               ├── Client.java
│               └── ClientInitializer.java
└── resources/
├── assets/
│   └── bllryclient/
│       └── icon.png
├── bllryclient.mixins.json
└── fabric.mod.json
```

# REFRENCES I USED
1. https://github.com/MeteorDevelopment/meteor-addon-template.git
2. https://github.com/MeteorDevelopment/meteor-client
3. https://github.com/FabricMC/fabric-example-mod
4. https://wiki.fabricmc.net/tutorial:setup
5. https://github.com/SpongePowered/Mixin/wiki
6. https://github.com/SpongePowered/Mixin
7. 

# Project Structure & File Explanations
## Core System Files

### Event System

- Event.java: Base class for all events in your client. Events represent occurrences like key presses, rendering frames, etc.
EventTarget.java: Annotation that marks methods as event handlers.
EventManager.java: Manages registering/unregistering event listeners and dispatching events.
KeyEvent.java: Specific event for keyboard input.

 - Reference: This event system pattern is common in Java applications and games. Similar systems are found in many Minecraft clients (like Impact, LiquidBounce) and game engines (like LibGDX).
Module System

- Category.java: Enum for organizing modules by category (combat, render, etc.).
Module.java: Base class for all client features/hacks.
ModuleManager.java: Manages all modules, handles toggling via keybinds.

- Reference: This module system architecture is standard in Minecraft clients and follows the Component pattern in software design.

### Minecraft Integration

#### Fabric Configuration

- fabric.mod.json: Tells Fabric Loader about your mod - its name, entry points, dependencies.
bllryclient.mixins.json: Configures which Minecraft classes your mixins will modify.
build.gradle.kts: Gradle build script in Kotlin DSL format that manages dependencies and build process.
gradle.properties: Stores project properties like version numbers.

- Reference: These follow the standard Fabric mod development pattern documented in the Fabric Wiki.
Mixins

- KeyboardMixin.java: Injects code into Minecraft's keyboard handling to capture key events.
GameRendererMixin.java: Injects code into Minecraft's rendering system to display your GUI.

- Reference: These use the Mixin library which is part of Fabric's toolchain.

### Client Core

 - Client.java: Main controller class for your client, manages all systems and handles startup/shutdown.
ClientInitializer.java: Entry point for Fabric, initializes your client when Minecraft loads.
Reference: This follows the Singleton pattern and Model-View-Controller architectural pattern commonly used in game development.

### GUI System

- ClickGUI.java: Interface for managing modules, appears when right-shift is pressed.
Panel.java: UI component representing a category of modules.
Reference: This GUI architecture pattern is common in Minecraft clients like Impact, Wurst, and others.

- Source Code References

# The code architecture is based on established patterns from:

- Minecraft Client Development:

- Open-source clients like Impact, LiquidBounce, and Wurst follow similar patterns
Projects like MeteorClient use comparable systems


- Fabric Mod Development:

- Fabric Example Mod
- Fabric API Documentation


- Software Design Patterns:

- Event system: Observer pattern from GoF design patterns
- Module system: Component pattern
-= Client class: Singleton pattern


Mixin Documentation:

SpongePowered Mixin Wiki
