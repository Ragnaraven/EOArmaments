package io.github.ragnaraven.eoarmaments.core.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class Vec3Util {
    public static BlockPos vec3ToBlockPos(Vec3 vector) {
        // Convert each component using Math.floor to ensure the coordinates round down
        int x = (int) Math.floor(vector.x);
        int y = (int) Math.floor(vector.y);
        int z = (int) Math.floor(vector.z);

        // Return a new BlockPos, which is a specific implementation of Vec3i suitable for block positions
        return new BlockPos(x, y, z);
    }
}