package dex.netherroof.mixin;
import static dex.netherroof.NetherRoof.*;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.BiomeAccess;

import static net.minecraft.world.biome.source.BiomeCoords.*;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeAccess.class)
public abstract class ChunkGeneratorMixin {
	
	@Inject(at = @At("RETURN"), method = "getBiomeForNoiseGen(III)Lnet/minecraft/world/biome/Biome;", cancellable = true)
    public void changeBiomeLoc(int biomeX, int biomeY, int biomeZ, CallbackInfoReturnable<Biome> cir) {
		if (cir.getReturnValue() != null && cir.getReturnValue().getCategory() == Biome.Category.NETHER && biomeY >= fromBlock(NETHER_HEIGHT)) {
			cir.setReturnValue(BuiltinRegistries.BIOME.get(BiomeKeys.THE_VOID));
		}
    }
}
