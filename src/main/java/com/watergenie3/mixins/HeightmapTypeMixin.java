package com.watergenie3.mixins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.watergenie3.accessors.HeightmapTypeAccessor;

import net.minecraft.block.BlockState;
import net.minecraft.world.Heightmap;
import net.minecraft.world.Heightmap.Purpose;

// See https://github.com/SpongePowered/Mixin/issues/387#issuecomment-888408556
// For mixin on enum example
@Mixin(Heightmap.Type.class)
public abstract class HeightmapTypeMixin implements HeightmapTypeAccessor {

    @Final @Mutable private static Heightmap.Type[] $TYPES;

    // private static final Heightmap.Type WORLD_FLOOR = heightmapTypeMixin$addVariant("WORLD_FLOOR", Purpose.CLIENT, HeightmapMixin.NOT_AIR);
    // private static final Heightmap.Type WORLD_FLOOR_NO_BEDROCK = heightmapTypeMixin$addVariant("WORLD_FLOOR_NO_BEDROCK", Purpose.CLIENT, HeightmapMixin.NOT_AIR_NOR_BEDROCK());
    // private static final Heightmap.Type WORLD_SURFACE_NO_BEDROCK = heightmapTypeMixin$addVariant("WORLD_SURFACE_NO_BEDROCK", Purpose.CLIENT, HeightmapMixin.NOT_AIR_NOR_BEDROCK());

    @Invoker("<init>") public static Heightmap.Type heightmapTypeMixin$invokeInit(
        String internalName,
        int internalId,
        String name,
        Purpose purpose,
        Predicate<BlockState> blockPredicate
    ) {
        throw new AssertionError();
    }

    private static Heightmap.Type heightmapTypeMixin$addType(
        String name,
        Purpose purpose,
        Predicate<BlockState> blockPredicate
    ) {
        ArrayList<Heightmap.Type> types = new ArrayList<Heightmap.Type>(Arrays.asList(HeightmapTypeMixin.$TYPES));
        String internalName = name;
        int internalId = types.get(types.size() - 1).ordinal() + 1;
        Heightmap.Type type = heightmapTypeMixin$invokeInit(internalName, internalId, name, purpose, blockPredicate);
        types.add(type);
        HeightmapTypeMixin.$TYPES = types.toArray(new Heightmap.Type[0]);
        return type;
    }

    @Override public Heightmap.Type fromName(String name) {
        if (name.equals(Heightmap.Type.WORLD_SURFACE_WG.asString())) {
            return Heightmap.Type.WORLD_SURFACE_WG;
        } else if (name.equals(Heightmap.Type.WORLD_SURFACE.asString())) {
            return Heightmap.Type.WORLD_SURFACE;
        } else if (name.equals(Heightmap.Type.OCEAN_FLOOR_WG.asString())) {
            return Heightmap.Type.OCEAN_FLOOR_WG;
        } else if (name.equals(Heightmap.Type.OCEAN_FLOOR.asString())) {
            return Heightmap.Type.OCEAN_FLOOR;
        } else if (name.equals(Heightmap.Type.MOTION_BLOCKING.asString())) {
            return Heightmap.Type.MOTION_BLOCKING;
        } else if (name.equals(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES.asString())) {
            return Heightmap.Type.MOTION_BLOCKING_NO_LEAVES;
        }
        throw new AssertionError();
    }

}
