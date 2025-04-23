package com.bllry.client.core.modules;

import com.bllry.client.core.events.EventTarget;
import com.bllry.client.core.events.KeyEvent;
import jdk.jfr.Category;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages client modules
 */
public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();
    private final Map<Category, List<Module>> modulesByCategory = new HashMap<>();

    /**
     * Initialize the module manager
     */
    public void initialize() {
        // Initialize category lists
        for (Category category : Category.values()) {
            modulesByCategory.put(category, new ArrayList<>());
        }

        // Register modules
        // These are just examples, you'll add your own modules later
        //registerModule(new ExampleModule());

        System.out.println("Initialized ModuleManager with " + modules.size() + " modules");
    }

    /**
     * Register a module
     * @param module The module to register
     */
    public void registerModule(Module module) {
        modules.add(module);
        modulesByCategory.get(module.getCategory()).add(module);
    }

    /**
     * Handle key presses for module toggling
     * @param event Key event
     */
    @EventTarget
    public void onKey(KeyEvent event) {
        // Only handle key presses, not releases
        if (event.isPressed()) {
            // Check if any module has this key bind
            for (Module module : modules) {
                if (module.getKeyBind() == event.getKeyCode()) {
                    module.toggle();
                }
            }
        }
    }

    /**
     * Get all modules
     * @return List of all modules
     */
    public List<Module> getModules() {
        return modules;
    }

    /**
     * Get modules by category
     * @param category The category
     * @return List of modules in the category
     */
    public List<Module> getModulesByCategory(Category category) {
        return modulesByCategory.get(category);
    }

    /**
     * Get a module by name
     * @param name Module name
     * @return The module, or null if not found
     */
    public Module getModuleByName(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }
}