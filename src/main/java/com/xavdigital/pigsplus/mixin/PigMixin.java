package com.xavdigital.pigsplus.mixin;

import com.xavdigital.pigsplus.items.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Pig.class)
public abstract class PigMixin {
    @Inject(method = "getControllingPassenger", at = @At("HEAD"), cancellable = true)
    private void pigsplus$allowGoldenStick(CallbackInfoReturnable<LivingEntity> cir){
        Pig pig = (Pig)(Object)this;
        if(!pig.isSaddled()) return;

        Entity passenger = pig.getFirstPassenger();

        if(passenger instanceof Player player)
        {
            boolean holdingVanilla = player.isHolding(Items.CARROT_ON_A_STICK);
            boolean holdingGolden = player.isHolding(ModItems.GOLDEN_CARROT_ON_STICK);

            if (holdingVanilla || holdingGolden)
            {
                cir.setReturnValue(player);
            }
        }
    }

    @Inject(method = "getRiddenSpeed", at = @At("RETURN"), cancellable = true)
    private void pigsplus$goldenSpeed(Player controller, CallbackInfoReturnable<Float> cir) {

        if (controller == null) return;

        ItemStack stack = controller.getMainHandItem();

        boolean holdingGolden = controller.isHolding(ModItems.GOLDEN_CARROT_ON_STICK);

        if (holdingGolden) {
            float original = cir.getReturnValue();

            cir.setReturnValue(original * 2.5F);
        }
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void pigsplus$pigsHealGold(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {

        Pig pig = (Pig)(Object)this;

        ItemStack stack = player.getItemInHand(hand);

        if (stack.is(Items.GOLDEN_CARROT)) {
            if (pig.getHealth() < pig.getMaxHealth()) {

                pig.heal(8.0F); // adjust amount

                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }

                cir.setReturnValue(InteractionResult.SUCCESS);
            }
        }
    }
}