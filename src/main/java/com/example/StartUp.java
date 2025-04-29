package com.example;

import com.example.mixin.BllryClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class StartUp implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Initializing Bllry Client...");

        // Initialize the client
        BllryClient.INSTANCE = new BllryClient();
        BllryClient.INSTANCE.init();
    }
}