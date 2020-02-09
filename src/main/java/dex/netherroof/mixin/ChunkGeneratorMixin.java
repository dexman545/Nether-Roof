package dex.netherroof.mixin;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MultiNoiseBiomeSource.class)
public abstract class ChunkGeneratorMixin {

    @Inject(at = @At("HEAD"), method = "getBiomeForNoiseGen(III)Lnet/minecraft/world/biome/Biome;", cancellable = true)
    public void changeChunk(int biomeX, int biomeY, int biomeZ, CallbackInfoReturnable<Biome> cir) {
        if (biomeY >= (128 >> 2)) {
            cir.setReturnValue(Biomes.THE_VOID);
        }
    }
}
