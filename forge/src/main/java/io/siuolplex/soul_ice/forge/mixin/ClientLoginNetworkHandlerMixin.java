package io.siuolplex.soul_ice.forge.mixin;

import io.siuolplex.soul_ice.SoulIceConfig;
import io.siuolplex.soul_ice.SoulIceSlipSetter;
import net.minecraft.client.network.ClientLoginNetworkHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLoginNetworkHandler.class)
public class ClientLoginNetworkHandlerMixin {
    @Inject(method = "onDisconnected", at = @At("HEAD"), cancellable = true)
    private void soulIce$disconnectReset(Text reason, CallbackInfo ci){
        new SoulIceSlipSetter(SoulIceConfig.instance().slipperiness);
    }
}