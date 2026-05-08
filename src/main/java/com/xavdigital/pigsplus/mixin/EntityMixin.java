package com.xavdigital.pigsplus.mixin;

import com.xavdigital.pigsplus.items.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class EntityMixin {
    @Inject(method = "travel", at = @At("HEAD"))
    private void pigsplus$goldenStickSteering(Vec3 movementInput, CallbackInfo ci) {

        if (!((Object)this instanceof Pig pig)) return;

        if (!pig.isVehicle() || !pig.isInWater()) return;

        Entity rider = pig.getFirstPassenger();

            if (rider instanceof Player player && player.isHolding(ModItems.GOLDEN_CARROT_ON_STICK)) {
                pig.setYRot(player.getYRot());
                pig.yRotO = pig.getYRot();
                pig.setXRot(player.getXRot() * 0.5F);

                pig.setDeltaMovement(pig.getDeltaMovement().add(0, 0.02, 0));

                pig.move(MoverType.SELF, pig.getDeltaMovement());
            }
        }
    }
