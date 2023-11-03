package rawoochat.yetanothergunmod;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rawoochat.yetanothergunmod.items.ModItems;

public class YetAnotherGunModClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("yetanothergunmod");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Hello Fabric world!");
		new ModItems().initializeItems();
	}
}