package dex.netherroof.mixin;
import static dex.netherroof.NetherRoof.*;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;

import static net.minecraft.world.biome.source.BiomeCoords.*;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class SpawnBehaviorMixin {
	
	@Inject(at = @At("RETURN"), method = "canMobSpawn(Lnet/minecraft/entity/EntityType;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/util/math/BlockPos;Ljava/util/Random;)Z", cancellable = true)
    private static void noSpawnsOnCeiling(EntityType<? extends MobEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
		if ((world.getBiome(pos).getCategory() == Biome.Category.NETHER || world.getBiome(pos) == BuiltinRegistries.BIOME.get(BiomeKeys.THE_VOID)) && pos.getY() >= fromBlock(NETHER_HEIGHT)) {
			cir.setReturnValue(false);
		}
    }
}
