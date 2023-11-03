package rawoochat.yetanothergunmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YetAnotherGunMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("yetanothergunmod");

//	public static final Item PISTOL_AMMO_FMJ = new Item(new FabricItemSettings());


	@Override
	public void onInitialize() {

//		Registry.register(Registries.ITEM, new Identifier("yagm", "pistol_ammo_fmj"), PISTOL_AMMO_FMJ);


		LOGGER.info("Hello Fabric world!");
	}
}