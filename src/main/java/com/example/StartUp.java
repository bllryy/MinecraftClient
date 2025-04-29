package com.example;

import com.example.mixin.BllryClient;
import net.fabricmc.api.ClientModInitializer;

public class StartUp implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Initialize the client
        new BllryClient().init();
    }
}
