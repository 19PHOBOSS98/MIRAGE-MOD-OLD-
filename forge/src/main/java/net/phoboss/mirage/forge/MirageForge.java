package net.phoboss.mirage.forge;

import dev.architectury.platform.forge.EventBuses;
import net.phoboss.mirage.Mirage;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Mirage.MOD_ID)
public class MirageForge {
    public MirageForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Mirage.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Mirage.init();
    }
}
