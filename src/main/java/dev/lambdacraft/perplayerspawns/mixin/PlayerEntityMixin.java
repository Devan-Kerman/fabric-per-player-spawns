package dev.lambdacraft.perplayerspawns.mixin;

import com.mojang.authlib.GameProfile;
import dev.lambdacraft.perplayerspawns.Main;
import dev.lambdacraft.perplayerspawns.util.PooledHashSets;
import dev.lambdacraft.perplayerspawns.access.PlayerEntityAccess;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements PlayerEntityAccess {
	private int[] mobCounts;
	private PooledHashSets.PooledObjectLinkedOpenHashSet<PlayerEntity> pooled;
	@Inject(method = "<init>", at = @At("RETURN"))
	private void pooled(World world, GameProfile profile, CallbackInfo ci) {
		this.mobCounts = new int[Main.ENTITIES];
		this.pooled = new PooledHashSets.PooledObjectLinkedOpenHashSet<>((PlayerEntity)(Object) this);
	}

	@Override
	public final int[] getMobCounts() {
		return this.mobCounts;
	}

	@Override
	public final PooledHashSets.PooledObjectLinkedOpenHashSet<PlayerEntity> getDistanceMap() {
		return this.pooled;
	}
}
