package com.xavdigital.pigsplus.items;

import com.xavdigital.pigsplus.PigsPlus;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.FoodOnAStickItem;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {
    public static final Item GOLDEN_CARROT_ON_STICK = registerItem(
            "golden_carrot_on_a_stick",
            (properties) -> new FoodOnAStickItem(EntityType.PIG, 1, properties),
            new Item.Properties().durability(25)
    );


    private static Item registerItem(
            String name,
            Function<Item.Properties, Item> function,
            Item.Properties properties
    ) {
        Identifier id = Identifier.fromNamespaceAndPath(PigsPlus.MOD_ID, name);
        ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, id);

        Item item = function.apply(properties.setId(key));

        return Registry.register(BuiltInRegistries.ITEM, key, item);
    }

    public static  void registerModItems()
    {
        PigsPlus.LOGGER.info("Registering Mod Items for " + PigsPlus.MOD_ID);

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(output -> {
            output.accept(GOLDEN_CARROT_ON_STICK);
        });
    }

}
