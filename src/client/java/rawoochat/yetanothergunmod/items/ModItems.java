package rawoochat.yetanothergunmod.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

import static net.minecraft.registry.Registries.ITEM;
import static net.minecraft.registry.Registries.ITEM_GROUP;
import static net.minecraft.registry.Registry.register;


public class ModItems {
    private static final String NAMESPACE = "yagm";

    public void initializeItems() {
        // registering items and mapping them to list
        List<Item> customItems = Arrays.stream(ItemNames.values())
                .map(item -> register(ITEM, new Identifier(NAMESPACE, item.getPath()), new Item(getSettings())))
                .toList();

        // mainItemGroup takes customItems list with all mod items and adds each to custom group
        ItemGroup mainItemGroup = FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.yagm.main_item_group"))
                .entries((context, entries) -> customItems.forEach(entries::add))
                .build();

        // register mainItemGroup
        register(ITEM_GROUP, new Identifier(NAMESPACE, "main_item_group"), mainItemGroup);

    }

    private FabricItemSettings getSettings() {
        return new FabricItemSettings();
    }
}
