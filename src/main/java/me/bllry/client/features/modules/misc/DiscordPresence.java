package me.bllry.client.features.modules.misc;

import me.bllry.client.forks.Version;
import me.bllry.client.features.modules.Module;
import me.bllry.client.features.modules.Module.Category;
import me.bllry.client.meteordevelopment.DiscordIPC;
import me.bllry.client.meteordevelopment.RichPresence;

import java.time.Instant;

public class DiscordPresence extends Module {
    public static DiscordPresence INSTANCE = new DiscordPresence();
    private RichPresence richPresence;

    // Customize these values to change what shows on Discord
    private static final String TITLE_TEXT = "Bllry Client"; // Change this to your desired title
    private static final String STATUS_TEXT = "https://bllry.com/"; // Change this to your desired status text

    // Image keys - these need to be configured in your Discord Developer Portal
    private static final String LARGE_IMAGE_KEY = "my_client_logo"; // Change to your large image key
    private static final String LARGE_IMAGE_TEXT = "The Best Minecraft Client"; // Tooltip for large image
    private static final String SMALL_IMAGE_KEY = "small_icon"; // Change to your small image key
    private static final String SMALL_IMAGE_TEXT = "Premium Edition"; // Tooltip for small image

    public DiscordPresence() {
        super("DiscordPresence", "Custom Discord Rich Presence", Category.CLIENT, false, false, false);
    }

    private static void lambda$onEnable$0() {
        System.out.println("Started Discord Presence Controller");
    }

    @Override
    public void onEnable() {
        // Your Discord Application ID - must be created in Discord Developer Portal
        long discordAppId = Version.isBeta ? 1085603982304608337L : 853740617964322826L;

        if (!DiscordIPC.method2283() && !DiscordIPC.method2282(discordAppId, DiscordPresence::lambda$onEnable$0)) {
            this.setEnabled(false);
        } else {
            this.richPresence = new RichPresence();

            // Set the title text (displayed at the top)
            this.richPresence.method2291(TITLE_TEXT);

            // Set the status text (displayed below the title)
            this.richPresence.method2292(STATUS_TEXT);

            // Set the small image (shown in the corner)
            this.richPresence.method2293(SMALL_IMAGE_KEY, SMALL_IMAGE_TEXT);

            // Set the large image (main logo)
            this.richPresence.method2294(LARGE_IMAGE_KEY, LARGE_IMAGE_TEXT);

            // Set the timestamp
            this.richPresence.method2295(Instant.now().getEpochSecond());

            // Update the Discord presence
            DiscordIPC.method2285(this.richPresence);
        }
    }

    @Override
    public void onDisable() {
        DiscordIPC.method2286();
    }

    public void setDetails(String details) {
        if (this.richPresence != null) {
            this.richPresence.method2291(details);
            DiscordIPC.method2285(this.richPresence); // Update after changing
        }
    }

    public void setState(String state) {
        if (this.richPresence != null) {
            this.richPresence.method2292(state);
            DiscordIPC.method2285(this.richPresence); // Update after changing
        }
    }

    public void setSmallImage(String key, String text) {
        if (this.richPresence != null) {
            this.richPresence.method2293(key, text);
            DiscordIPC.method2285(this.richPresence); // Update after changing
        }
    }

    public void setLargeImage(String key, String text) {
        if (this.richPresence != null) {
            this.richPresence.method2294(key, text);
            DiscordIPC.method2285(this.richPresence); // Update after changing
        }
    }
}