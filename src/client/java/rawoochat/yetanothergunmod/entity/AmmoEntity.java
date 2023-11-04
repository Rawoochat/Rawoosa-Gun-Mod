package rawoochat.yetanothergunmod.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import rawoochat.yetanothergunmod.items.core.AmmoItem;
import rawoochat.yetanothergunmod.items.ModItems;

import static rawoochat.yetanothergunmod.items.ModItems.AMMO_ENTITY_ENTITY_TYPE;

public class AmmoEntity extends PersistentProjectileEntity implements FlyingItemEntity {
    private float damage;
    private int TTL;
    private boolean explosive;
    private int forceOfNature;
    private float piercing;

    private static final TrackedData<ItemStack> ITEM = DataTracker.registerData(AmmoEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);


    public AmmoEntity(EntityType<? extends AmmoEntity> entityType, World world) {
        super(entityType, world);
        this.pickupType = PickupPermission.DISALLOWED;
    }

    public AmmoEntity(LivingEntity owner, World world, float damage, float piercing, boolean explosive, ItemStack stack) {
        super(AMMO_ENTITY_ENTITY_TYPE, owner, world);

        this.TTL = 0;
        this.damage = damage;
        this.piercing = piercing;
        this.explosive = explosive;
        this.forceOfNature = 0;

        this.setNoGravity(true);
        this.setSilent(true);

        this.dataTracker.set(ITEM, stack);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.TTL++ >= 20) {
            this.discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (entityHitResult.getEntity() instanceof LivingEntity entity) {
            entity.damage(this.getDamageSources().arrow(this, this.getOwner() != null ? this.getOwner() : this), this.damage);
            entity.timeUntilRegen = 0;
            if (this.forceOfNature > 0) {
                double d = Math.max(0.0, 1.0 - entity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE));
                Vec3d vec3d = this.getVelocity().multiply(1.0, 0.0, 1.0).normalize().multiply((double) this.forceOfNature * 0.3 * d);
                if (vec3d.lengthSquared() > 0.0) {
                    entity.addVelocity(vec3d.x, 0.1, vec3d.z);
                }
            }
            if (this.explosive) {
                if (!this.getWorld().isClient) {
                    this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.5f, false, World.ExplosionSourceType.NONE);
                }
            }
        }
        this.discard();
    }

    public void setForceOfNature(int forceOfNature) {
        this.forceOfNature = forceOfNature;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
//        if (this.ammoPiercing > 5) {
//
//        }
        if (!this.getWorld().isClient) {
            ((ServerWorld) this.getWorld()).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, getWorld().getBlockState(blockHitResult.getBlockPos())), blockHitResult.getPos().getX(), blockHitResult.getPos().getY(), blockHitResult.getPos().getZ(), 5, 0.0, 0.0, 0.0, 0.5f);
            if (this.explosive)
                this.getWorld().createExplosion(this, this.getX(), this.getY(), this.getZ(), 2.5f, false, World.ExplosionSourceType.NONE);
        }
        this.discard();
    }

    protected ItemStack getItem() {
        return this.getDataTracker().get(ITEM);
    }

    @Override
    public ItemStack getStack() {
        ItemStack itemStack = this.getItem();
        return itemStack.isEmpty() ? new ItemStack(ModItems.PISTOL_AMMO_FMJ, 1) : itemStack;
    }

    public void setItem(ItemStack item) {
        if (!(item.getItem() instanceof AmmoItem) || item.hasNbt()) {
            this.getDataTracker().set(ITEM, Util.make(item.copy(), stack -> stack.setCount(1)));
        }
    }

    @Override
    public boolean shouldRender(double distance) {
        return true;
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(ITEM, ItemStack.EMPTY);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        ItemStack itemStack = this.getItem();
        if (!itemStack.isEmpty()) {
            nbt.put("Item", itemStack.writeNbt(new NbtCompound()));
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("Item"));
        this.setItem(itemStack);
    }

    @Override
    protected ItemStack asItemStack() {
        return getStack();
    }
}