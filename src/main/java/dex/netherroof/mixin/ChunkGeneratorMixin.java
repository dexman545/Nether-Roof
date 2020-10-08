package dex.netherroof.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(MultiNoiseBiomeSource.class)
public abstract class ChunkGeneratorMixin {

    @Shadow @Final private Optional<Pair<Registry<Biome>, MultiNoiseBiomeSource.Preset>> instance;

    @Inject(at = @At("HEAD"), method = "getBiomeForNoiseGen(III)Lnet/minecraft/world/biome/Biome;", cancellable = true)
    public void changeChunk(int biomeX, int biomeY, int biomeZ, CallbackInfoReturnable<Biome> cir) {
        if (biomeY >= (128 >> 2)) {
            this.instance.ifPresent(registryPresetPair -> {
                cir.setReturnValue(registryPresetPair.getFirst().get(BiomeKeys.THE_VOID));
            });
        }
    }
}
