package com.watergenie3.mixins;

import static com.watergenie3.CarpetGenieExtension.LOG;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.watergenie3.settings.CarpetGenieSettings;

import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureHolder;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.light.LightSourceView;

@Mixin(Chunk.class)
public abstract class ChunkMixin implements BiomeAccess.Storage, LightSourceView, StructureHolder {
    
    @Shadow private static Logger LOGGER;

    @Shadow protected Map<Heightmap.Type, Heightmap> heightmaps;

    @Shadow public abstract ChunkSection[] getSectionArray();

    @Shadow public abstract int getHighestNonEmptySection();

    @Unique public int getLowestNonEmptySection() {
        ChunkSection[] chunkSections = this.getSectionArray();
        LOG.debug(chunkSections.toString());
        for (int i = 0; i < chunkSections.length; ++i) {
            ChunkSection chunkSection = chunkSections[i];
            if (chunkSection.isEmpty()) continue;
            return i;
        }
        return -1;
    }

    @Unique public int getLowestNonEmptySectionYOffset() {
        int i = this.getLowestNonEmptySection();
        return i == -1 ? this.getTopY() : ChunkSectionPos.getBlockCoord(this.sectionIndexToCoord(i));
    }

    @Redirect(
        method = "sampleHeightmap",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/Heightmap;populateHeightmaps(Lnet/minecraft/world/chunk/Chunk;Ljava/util/Set;)V"
        )
    )
    public void basePopulateHeightmaps(Chunk chunk, Set<Heightmap.Type> types) {
        if (CarpetGenieSettings.spawnFromFloorHeightmap) {
            Heightmap.populateHeightmaps(chunk, types);
        } else {
            Heightmap.populateHeightmaps(chunk, types);
        }
    }

    @Inject(
        method = "sampleHeightmap",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/Heightmap;populateHeightmaps(Lnet/minecraft/world/chunk/Chunk;Ljava/util/Set;)V",
            shift = At.Shift.AFTER
        )
    )
    public void populateFloorHeightmap(Heightmap.Type type, int x, int z, CallbackInfoReturnable<Void> callback) {
        
    }

    // @Inject(method="sampleHeightmap", at=@At("HEAD"))
    // public void populateHeightmapLogger(Heightmap.Type type, int x, int z, CallbackInfoReturnable<Void> callback) {
    //     LOG.info("Overwritten sampleHeightmap: " + type + " " + x + " " + z);
    // }

}
