package com.direwolf20.laserio.common.items.cards;

import com.direwolf20.laserio.common.containers.CardFluidContainer;
import com.direwolf20.laserio.setup.Config;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


public class CardFluid extends BaseCard {
    public CardFluid() {
        super();
        CARDTYPE = CardType.FLUID;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (level.isClientSide()) return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);

        ((ServerPlayer) player).openMenu(new SimpleMenuProvider(
                (windowId, playerInventory, playerEntity) -> new CardFluidContainer(windowId, playerInventory, player, itemstack), Component.translatable("")), (buf -> {
            buf.writeItem(itemstack);
            buf.writeByte(-1);
        }));

        //System.out.println(itemstack.getItem().getRegistryName()+""+itemstack.getTag());
        return new InteractionResultHolder<>(InteractionResult.PASS, itemstack);
    }

    public static int setFluidExtractAmt(ItemStack card, int fluidextractamt) {
        if (fluidextractamt == Config.BASE_MILLI_BUCKETS.get())
            card.removeTagKey("fluidextractamt");
        else
            card.getOrCreateTag().putInt("fluidextractamt", fluidextractamt);
        return fluidextractamt;
    }

    public static int getFluidExtractAmt(ItemStack card) {
        CompoundTag compound = card.getTag();
        if (compound == null || !compound.contains("fluidextractamt")) return Config.BASE_MILLI_BUCKETS.get();
        return compound.getInt("fluidextractamt");
    }
}
