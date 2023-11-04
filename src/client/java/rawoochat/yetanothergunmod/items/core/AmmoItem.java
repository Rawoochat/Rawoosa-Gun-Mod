package rawoochat.yetanothergunmod.items.core;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rawoochat.yetanothergunmod.entity.AmmoEntity;

public class AmmoItem extends Item {
    private final float damage;
    private final float velocity;
    private final float piercing;
    private final boolean explosive;

    public AmmoItem(Settings settings, float damage, float velocity, float piercing, boolean explosive) {
        super(settings);
        this.damage = damage;
        this.velocity = velocity;
        this.piercing = piercing;
        this.explosive = explosive;
    }

    public AmmoEntity createBullet(World world, ItemStack stack, float gunDamage, float damageMult, LivingEntity shooter) {
        AmmoEntity bullet = new AmmoEntity(shooter, world, (this.damage + gunDamage) * damageMult, piercing, this.explosive, stack);
        bullet.setItem(stack);
        return bullet;
    }

    public float getVelocity() {
        return this.velocity;
    }
}