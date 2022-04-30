package com.direwolf20.laserio.datagen;

import com.direwolf20.laserio.setup.Registration;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class LaserIORecipes extends RecipeProvider {

    public LaserIORecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {


        //Crafting Components
        ShapedRecipeBuilder.shaped(Registration.Logic_Chip_Raw.get(), 4)
                .pattern("rgr")
                .pattern("cqc")
                .pattern("rgr")
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .define('q', Tags.Items.STORAGE_BLOCKS_QUARTZ)
                .define('g', Tags.Items.NUGGETS_GOLD)
                .define('c', Items.CLAY_BALL)
                .group("laserio")
                .unlockedBy("has_quartz", InventoryChangeTrigger.TriggerInstance.hasItems(Items.QUARTZ_BLOCK))
                .save(consumer);
        SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registration.Logic_Chip_Raw.get()),
                        Registration.Logic_Chip.get(), 1.0f, 100)
                .unlockedBy("has_raw_chip", inventoryTrigger(ItemPredicate.Builder.item().of(Registration.Logic_Chip_Raw.get()).build()))
                .save(consumer, "logic_chip");

        //Blocks
        ShapedRecipeBuilder.shaped(Registration.LaserConnector.get(), 1)
                .pattern(" g ")
                .pattern("rbr")
                .pattern("iii")
                .define('g', Tags.Items.GLASS)
                .define('i', Tags.Items.INGOTS_IRON)
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .define('b', Registration.Logic_Chip.get())
                .group("laserio")
                .unlockedBy("has_logic_chip", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.Logic_Chip.get()))
                .save(consumer);
        ShapedRecipeBuilder.shaped(Registration.LaserNode.get(), 1)
                .pattern("igi")
                .pattern("gbg")
                .pattern("igi")
                .define('i', Tags.Items.INGOTS_IRON)
                .define('g', Tags.Items.GLASS_PANES)
                .define('b', Registration.LaserConnector.get())
                .group("laserio")
                .unlockedBy("has_logic_connector", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.LaserConnector.get()))
                .save(consumer);


        //Misc Items
        ShapedRecipeBuilder.shaped(Registration.Laser_Wrench.get(), 1)
                .pattern("i i")
                .pattern(" b ")
                .pattern(" i ")
                .define('b', Registration.Logic_Chip.get())
                .define('i', Tags.Items.INGOTS_IRON)
                .group("laserio")
                .unlockedBy("has_logic_chip", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.Logic_Chip.get()))
                .save(consumer);

        //Cards
        ShapedRecipeBuilder.shaped(Registration.Card_Item.get(), 1)
                .pattern("rlr")
                .pattern("qpq")
                .pattern("ggg")
                .define('r', Tags.Items.DUSTS_REDSTONE)
                .define('p', Registration.Logic_Chip.get())
                .define('g', Tags.Items.NUGGETS_GOLD)
                .define('l', Tags.Items.GEMS_LAPIS)
                .define('q', Tags.Items.GEMS_QUARTZ)
                .group("laserio")
                .unlockedBy("has_logic_chip", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.Logic_Chip.get()))
                .save(consumer);

        //Filters
        ShapedRecipeBuilder.shaped(Registration.Filter_Basic.get(), 4)
                .pattern("igi")
                .pattern("gqg")
                .pattern("igi")
                .define('i', Items.IRON_BARS)
                .define('q', Registration.Logic_Chip.get())
                .define('g', Tags.Items.GLASS_PANES)
                .group("laserio")
                .unlockedBy("has_logic_chip", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.Logic_Chip.get()))
                .save(consumer);
        ShapelessRecipeBuilder.shapeless(Registration.Filter_Count.get(), 1)
                .requires(Registration.Filter_Basic.get(), 1)
                .requires(Items.DISPENSER, 1)
                .group("laserio")
                .unlockedBy("has_filter_basic", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.Filter_Basic.get()))
                .save(consumer);

        //NBT Clearing Recipes
        ShapelessRecipeBuilder.shapeless(Registration.Card_Item.get())
                .requires(Registration.Card_Item.get())
                .group("laserio")
                .unlockedBy("has_card_item", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.Card_Item.get()))
                .save(consumer, Registration.Card_Item.getId() + "_nbtclear");
        ShapelessRecipeBuilder.shapeless(Registration.Filter_Basic.get())
                .requires(Registration.Filter_Basic.get())
                .group("laserio")
                .unlockedBy("has_filter_basic", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.Filter_Basic.get()))
                .save(consumer, Registration.Filter_Basic.getId() + "_nbtclear");
        ShapelessRecipeBuilder.shapeless(Registration.Filter_Count.get())
                .requires(Registration.Filter_Count.get())
                .group("laserio")
                .unlockedBy("has_filter_count", InventoryChangeTrigger.TriggerInstance.hasItems(Registration.Filter_Count.get()))
                .save(consumer, Registration.Filter_Count.getId() + "_nbtclear");

    }
}