/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package cn.langya.modules.misc.particles;

import net.minecraft.block.*;
import net.minecraft.client.Minecraft;
import net.minecraft.util.*;

public class PlayerParticles {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static Block getBlock(final double offsetX, final double offsetY, final double offsetZ) {
        return mc.theWorld.getBlockState(new BlockPos(offsetX, offsetY, offsetZ)).getBlock();
    }

}