package rawoochat.yetanothergunmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rawoochat.yetanothergunmod.items.ModItems;
import rawoochat.yetanothergunmod.render.AmmoEntityRender;

@Environment(EnvType.CLIENT)
public class YetAnotherGunModClient implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("yetanothergunmod");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Hello Fabric world!");
		ModItems.initializeGroups();
		EntityRendererRegistry.register(ModItems.AMMO_ENTITY_ENTITY_TYPE, context -> new AmmoEntityRender<>(context, 0.5f, true));
	}
}