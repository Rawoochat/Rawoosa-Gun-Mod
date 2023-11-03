package rawoochat.yetanothergunmod.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class Items {
    public static final Item PISTOL_AMMO_FMJ = Registry.register(
            Registries.ITEM,
            new Identifier("yagm", "pistol_ammo_fmj"),
            new Item(new FabricItemSettings()));
}
