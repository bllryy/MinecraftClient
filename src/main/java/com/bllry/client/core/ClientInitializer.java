package com.bllry.client;

import net.fabricmc.api.ModInitializer;

/**
 * Fabric mod initializer for the client
 */
public class ClientInitializer implements ModInitializer {

    @Override
    public void onInitialize() {
        // Initialize our client
        System.out.println("Initializing BllryClient...");

        // Create and start the client instance
        com.bllry.client.Client client = com.bllry.client.Client.getInstance(); // TODO: FIX ME!!!
        client.startClient();

        System.out.println("BllryClient initialized!");
    }
}