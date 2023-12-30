package com.watergenie3.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.watergenie3.settings.CarpetGenieSettings;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

@Mixin(SpawnHelper.class)
public abstract class SpawnHelperMixin {
    
    @Inject(
        method = "getRandomPosInChunkSection",
        at = @At("TAIL"),
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void getRandomPosWithFloorHeightmap(
        World world,
        WorldChunk chunk,
        CallbackInfoReturnable<BlockPos> cir,
        ChunkPos chunkPos,
        int randomX,
        int randomZ,
        int topY
    ) {
        if (CarpetGenieSettings.spawnFromFloorHeightmap) {
            cir.setReturnValue(new BlockPos(randomX, topY, randomZ));
        }
    }

}
