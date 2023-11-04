package rawoochat.yetanothergunmod.items.core;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Predicate;

import rawoochat.yetanothergunmod.entity.AmmoEntity;
import rawoochat.yetanothergunmod.items.ModItems;

public class GunItem extends RangedWeaponItem implements Vanishable {
    private final Random random;
    public final float damage;
    public final int cooldown;
    public final int shotCount;
    public final float spread;
    public final ToolMaterial material;

    public GunItem(Settings settings, float damage, int cooldown, int shotCount,
                   float spread, ToolMaterial material) {
        super(settings);
        this.random = new Random();
        this.damage = damage;
        this.cooldown = cooldown;
        this.shotCount = shotCount;
        this.spread = spread;
        this.material = material;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack gun = user.getStackInHand(hand);
        ItemStack ammo = user.getProjectileType(gun);

        if (canShoot(gun, ammo, user)) {
            if (ammo.isEmpty() || !(ammo.getItem() instanceof AmmoItem)) ammo = new ItemStack(ModItems.PISTOL_AMMO_FMJ);

            AmmoItem bulletItem = (AmmoItem) (ammo.getItem() instanceof AmmoItem ? ammo.getItem() : ModItems.PISTOL_AMMO_FMJ);

            float ammoPreserveChance = 0;
            int forceOfNatureLevel = 0;

            if (!world.isClient()) {
                for (int i = 0; i < shotCount; i++) {
                    AmmoEntity ammoEntity = bulletItem.createBullet(world, ammo, this.damage, 1, user);
                    ammoEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0f, ((AmmoItem) ammo.getItem()).getVelocity(), this.spread);
                    ammoEntity.setForceOfNature(forceOfNatureLevel);

                    world.spawnEntity(ammoEntity);
                }
                if (!user.isCreative()) {
                    gun.damage(1, user, p -> p.sendToolBreakStatus(user.getActiveHand()));
                    if (!shouldPreserveAmmo(ammoPreserveChance)) {
                        ammo.decrement(1);
                        if (ammo.isEmpty()) {
                            user.getInventory().removeOne(ammo);
                        }
                    }
                }
            }
            user.getItemCooldownManager().set(this, this.cooldown);
        }

        return TypedActionResult.fail(gun);
    }

    private boolean shouldPreserveAmmo(float chance) {
        return this.random.nextFloat(0.01f, 1) <= chance;
    }

    private boolean canShoot(ItemStack gun, ItemStack ammo, PlayerEntity user) {
        return !user.getItemCooldownManager().isCoolingDown(gun.getItem()) && (user.isCreative() || !ammo.isEmpty());
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return (stack) -> stack.getItem() instanceof AmmoItem;
    }

    @Override
    public int getRange() {
        return 15;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    @Override
    public int getEnchantability() {
        return this.material.getEnchantability();
    }
}