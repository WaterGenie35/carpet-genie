package com.watergenie3.mixins;

import java.util.Set;
import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.block.BlockState;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.Chunk;

@Mixin(Heightmap.class)
public abstract class HeightmapMixin {

    // don't need this for chunkmixin
    // @Shadow public static void populateHeightmaps(Chunk chunk, Set<Heightmap.Type> types) {}

    // @Shadow @Final static Predicate<BlockState> NOT_AIR_NOR_BEDROCK;
    // @Unique static final Predicate<BlockState> NOT_AIR_NOR_BEDROCK = state -> !state.isAir() && state.getBlock() != Blocks.BEDROCK;

    // Predicate<BlockState> NOT_AIR_NOR_BEDROCK() {
    //     return state -> !state.isAir() && state.getBlock() != Blocks.BEDROCK;
    // }

}
