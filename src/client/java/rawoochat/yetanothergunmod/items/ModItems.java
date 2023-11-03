package rawoochat.yetanothergunmod.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import static net.minecraft.registry.Registry.register;
import static net.minecraft.registry.Registries.*;


public class ModItems extends Items {
    private static final String NAMESPACE = "yagm";

    public void initializeItems() {
        register(ITEM, new Identifier(NAMESPACE, "pistol_ammo_fmj"), new Item(getSettings()));
    }

    private FabricItemSettings getSettings() {
        return new FabricItemSettings();
    }
}
