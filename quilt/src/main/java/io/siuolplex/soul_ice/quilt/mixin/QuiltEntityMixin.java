package io.siuolplex.soul_ice.quilt.mixin;

import io.siuolplex.soul_ice.quilt.registry.SoulIceEnchantments;
import io.siuolplex.soul_ice.util.SoulIceVelocityFixer;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Entity.class, priority = 999)
public abstract class QuiltEntityMixin {
    @Inject(method = "slowMovement(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Vec3d;)V", at = @At("HEAD"), cancellable = true)
    private void soulIceQuilt$forUnfaltering(BlockState state, Vec3d multiplier, CallbackInfo ci) {
        if (unfalteringCheck()) ci.cancel();
    }

    @Inject(method = "getVelocityMultiplier()F", at = @At("RETURN"), cancellable = true)
    private void soulIceQuilt$velocityFix(CallbackInfoReturnable<Float> cir) {
        if (unfalteringCheck()) cir.setReturnValue(1.0f);
    }

    @Inject(method = "getVelocityAffectingPos()Lnet/minecraft/util/math/BlockPos;", at = @At("RETURN"), cancellable = true)
    private void soulIceQuilt$nullifiedVelocity(CallbackInfoReturnable<BlockPos> cir) {
        cir.setReturnValue(SoulIceVelocityFixer.velocityFix(((Entity)((Object) this))));
    }


    //Lnet/minecraft/entity/Entity;getVelocityMultiplier()F

    private boolean unfalteringCheck() {
        return ((Entity)((Object) this) instanceof LivingEntity player && EnchantmentHelper.getEquipmentLevel(SoulIceEnchantments.UNFALTERING, player) > 0);
    }
}
