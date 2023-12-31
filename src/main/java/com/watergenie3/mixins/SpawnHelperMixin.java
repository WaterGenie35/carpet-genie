package com.watergenie3.mixins;

import static com.watergenie3.CarpetGenieExtension.LOG;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import com.watergenie3.settings.CarpetGenieSettings;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.WorldChunk;

@Mixin(SpawnHelper.class)
public abstract class SpawnHelperMixin {

    // Will still get the +1 outside the redirect
    @Redirect(
        method = "getRandomPosInChunkSection",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/chunk/WorldChunk;sampleHeightmap(Lnet/minecraft/world/Heightmap$Type;II)I"
        )
    )
    private static int sampleSurfaceHeightmap(WorldChunk chunk, Heightmap.Type type, int x, int z) {
        if (CarpetGenieSettings.spawnHeightmapIgnoresBedrock) {
            // return chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE_NO_BEDROCK, x, z);
            return chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, x, z);
        }
        return chunk.sampleHeightmap(type, x, z);
    }

    private static int sampleFloorHeightmap(WorldChunk chunk, int x, int z) {
        return 300;
        // if (CarpetGenieSettings.spawnHeightmapIgnoresBedrock) {
        //     return chunk.sampleHeightmap(Heightmap.Type.WORLD_FLOOR_NO_BEDROCK, x, z);
        // }
        // return chunk.sampleHeightmap(Heightmap.Type.WORLD_FLOOR, x, z);
    }

    @Redirect(
        method = "getRandomPosInChunkSection",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/util/math/MathHelper;nextBetween(Lnet/minecraft/util/math/random/Random;II)I"
        )
    )
    private static int moveDefaultYSamplingToInject(Random random, int min, int max) {
        if (!CarpetGenieSettings.spawnFromFloorHeightmap) {
            return MathHelper.nextBetween(random, min, max);
        }
        return min;
    }

    @Inject(
        method = "getRandomPosInChunkSection",
        at = @At("TAIL"),
        cancellable = true,
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    private static void getRandomPosWithFloorHeightmap(
        World world,
        WorldChunk chunk,
        CallbackInfoReturnable<BlockPos> callback,
        ChunkPos chunkPos,
        int sampledX,
        int sampledZ,
        int topY
    ) {
        int bottomY = world.getBottomY();
        if (CarpetGenieSettings.spawnFromFloorHeightmap) {
            bottomY = sampleFloorHeightmap(chunk, sampledX, sampledZ);
            int min = Math.min(bottomY, topY);
            int max = Math.max(bottomY, topY);
            int sampledY = MathHelper.nextBetween(world.random, min, max);
            callback.setReturnValue(new BlockPos(sampledX, sampledY, sampledZ));
        }
        if (50 <= sampledX && sampledX <= 55 && 50 <= sampledZ && sampledZ <= 55) {
            LOG.info("Rand between y: " + bottomY + " " + topY);
        }
    }

}
