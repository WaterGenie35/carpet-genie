package com.watergenie3.mixins;

import carpet.utils.Messenger;
import com.watergenie3.settings.CarpetGenieSettings;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin extends ProjectileEntity {

    @Shadow private int waitCountdown;
    @Shadow protected abstract boolean isOpenOrWaterAround(BlockPos pos);

    public FishingBobberEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method="tickFishingLogic", at=@At("TAIL"))
    private void tickFishingLogic(BlockPos pos, CallbackInfo ci) {
        if (CarpetGenieSettings.debugFishing) {
            BlockPos posAboveBobber = pos.up();
            ServerWorld world = (ServerWorld) this.getWorld();
            boolean seesSky = world.isSkyVisible(posAboveBobber);
            boolean seesRain = world.hasRain(posAboveBobber);
            boolean inOpenWater = this.isOpenOrWaterAround(pos);

            this.setCustomName(Messenger.c(
                (seesSky ? "wb ☁ sky " : "fb ☁ no sky "),
                (seesRain ? "cb ☔ rain " : "fb ☔ no rain "),
                (inOpenWater ? "db ⚓ treasure " : "fb ⚓ no treasure "),
                (this.waitCountdown == 0 ? "lb " : "wb ") + "\ud83d\udd51 " + this.waitCountdown
            ));
            this.setCustomNameVisible(true);
        }
    }

}
