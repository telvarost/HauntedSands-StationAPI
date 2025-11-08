package com.github.telvarost.hauntedsands;

import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;

public class HauntedSands {
    //public static final Namespace NAMESPACE = Namespace.resolve();
    @Entrypoint.Namespace
    public static Namespace HAUNTED_SANDS = Null.get();

    public static int GLOWSTONE_SAND_TEXTURE    = 0;
    public static int REDSTONE_SAND_TEXTURE     = 0;
    public static int LIT_REDSTONE_SAND_TEXTURE = 0;
    public static int SUGAR_CUBE_TEXTURE        = 0;
    public static int GUNPOWDER_CUBE_TEXTURE    = 0;
    public static int GRAVE_TOP_TEXTURE         = 0;
    public static int GRAVE_SIDE_TEXTURE        = 0;
}
