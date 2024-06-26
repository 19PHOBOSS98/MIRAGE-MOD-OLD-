package net.phoboss.mirage.items;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.phoboss.mirage.Mirage;
import net.phoboss.mirage.blocks.ModBlocks;
import net.phoboss.mirage.items.mirageprojector.MirageBlockItem;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Mirage.MOD_ID, Registry.ITEM_KEY);

    public static final Item MIRAGE_BLOCK_ITEM = registerItem("mirage_block",
            new MirageBlockItem(
                    //ModBlocks.MIRAGE_BLOCK,
                    ModBlocks.MIRAGE_BLOCK.get(),
                    new Item.Settings().group(ModItemGroups.MIRAGE)));

            public static Item registerItem(String name, Item item){
                return Registry.register(Registry.ITEM,new Identifier(Mirage.MOD_ID,name),item);
            }
    public static void registerAll(){
        Mirage.LOGGER.debug("Registering Mod Items for "+ Mirage.MOD_ID);
        ITEMS.register();
    }
}
