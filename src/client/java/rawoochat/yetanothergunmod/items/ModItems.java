package rawoochat.yetanothergunmod.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import rawoochat.yetanothergunmod.entity.AmmoEntity;
import rawoochat.yetanothergunmod.items.core.AmmoItem;
import rawoochat.yetanothergunmod.items.core.GunItem;

import static net.minecraft.registry.Registries.ITEM;
import static net.minecraft.registry.Registries.ITEM_GROUP;
import static net.minecraft.registry.Registry.register;


public class ModItems {
    private static final String NAMESPACE = "yagm";

    public static final EntityType<AmmoEntity> AMMO_ENTITY_ENTITY_TYPE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(NAMESPACE, "bullet"),
            FabricEntityTypeBuilder.<AmmoEntity>create(SpawnGroup.MISC, AmmoEntity::new)
                    .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
                    .trackRangeBlocks(4)
                    .trackedUpdateRate(20).build());

    public static final Item PISTOL_AMMO_FMJ;
    public static final Item PISTOL_AMMO_AP;
    public static final Item PISTOL_AMMO_HP;
    public static final Item TEST_GUN;
    public static final Item TEST_GUN1;

    static {
        PISTOL_AMMO_FMJ = register(ITEM, new Identifier(NAMESPACE, "pistol_ammo_fmj"),
                new AmmoItem(getSettings(), 1.5f, 1, 1, false));

        PISTOL_AMMO_AP = register(ITEM, new Identifier(NAMESPACE, "pistol_ammo_ap"),
                new AmmoItem(getSettings(), 1.5f, 1.2f, 1.4f,false));

        PISTOL_AMMO_HP = register(ITEM, new Identifier(NAMESPACE, "pistol_ammo_hp"),
                new AmmoItem(getSettings(), 2.5f, 1, 0.6f,false));

        TEST_GUN = register(ITEM,
                new Identifier(NAMESPACE, "test_gun"),
                new GunItem(getSettings(),
                        4,
                        4,
                        1,
                        2,
                        ToolMaterials.IRON));

        TEST_GUN1 = register(ITEM,
                new Identifier(NAMESPACE, "test_gun_1"),
                new GunItem(getSettings(),
                        1,
                        2,
                        9,
                        2,
                        ToolMaterials.IRON));
    }

    public static void initializeGroups() {
        ItemGroup mainItemGroup = FabricItemGroup.builder()
                .displayName(Text.translatable("itemGroup.yagm.main_item_group"))
                .entries((context, entries) -> {
                    entries.add(PISTOL_AMMO_FMJ);
                    entries.add(PISTOL_AMMO_AP);
                    entries.add(PISTOL_AMMO_HP);
                    entries.add(TEST_GUN);
                    entries.add(TEST_GUN1);
                })
                .build();

        register(ITEM_GROUP, new Identifier(NAMESPACE, "main_item_group"), mainItemGroup);
    }


    private static FabricItemSettings getSettings() {
        return new FabricItemSettings();
    }
}
