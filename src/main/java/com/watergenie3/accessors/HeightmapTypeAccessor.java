package com.watergenie3.accessors;

import net.minecraft.world.Heightmap;

public interface HeightmapTypeAccessor {
    
    Heightmap.Type fromName(String name);

}
