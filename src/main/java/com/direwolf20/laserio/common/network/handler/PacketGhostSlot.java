package com.direwolf20.laserio.common.network.handler;

import com.direwolf20.laserio.common.containers.CardFluidContainer;
import com.direwolf20.laserio.common.containers.CardItemContainer;
import com.direwolf20.laserio.common.containers.FilterCountContainer;
import com.direwolf20.laserio.common.containers.customhandler.FilterCountHandler;
import com.direwolf20.laserio.common.containers.customslot.FilterBasicSlot;
import com.direwolf20.laserio.common.items.filters.FilterCount;
import com.direwolf20.laserio.common.network.data.GhostSlotPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.Optional;

public class PacketGhostSlot {
    public static final PacketGhostSlot INSTANCE = new PacketGhostSlot();

    public static PacketGhostSlot get() {
        return INSTANCE;
    }

    public void handle(final GhostSlotPayload payload, final PlayPayloadContext context) {
        context.workHandler().submitAsync(() -> {
            Optional<Player> senderOptional = context.player();
            if (senderOptional.isEmpty())
                return;
            Player sender = senderOptional.get();


            AbstractContainerMenu container = sender.containerMenu;
            if (container == null)
                return;

            ItemStack filterStack = container.slots.get(0).getItem();

            if (container instanceof CardItemContainer && filterStack.getItem() instanceof FilterCount) {
                ItemStack stack = payload.stack();
                FilterCountHandler handler = (FilterCountHandler) ((CardItemContainer) container).filterHandler;
                int mbAmt = payload.mbAmt();
                if (mbAmt == 0 && container instanceof CardFluidContainer) {
                    stack.setCount(0);
                } else {
                    stack.setCount(payload.count());
                }
                handler.setStackInSlotSave(payload.slotNumber() - CardItemContainer.SLOTS, stack);

                if (mbAmt != -1 && container instanceof CardFluidContainer) { //MB amt is only done in CardFluidContainers
                    handler.setMBAmountInSlot(payload.slotNumber() - CardItemContainer.SLOTS, mbAmt);
                }
            } else if (container instanceof FilterCountContainer) {
                ItemStack stack = payload.stack();
                stack.setCount(payload.count());
                FilterCountHandler handler = ((FilterCountContainer) container).handler;
                handler.setStackInSlotSave(payload.slotNumber(), stack);
            } else {
                Slot slot = container.slots.get(payload.slotNumber());
                ItemStack stack = payload.stack();
                stack.setCount(payload.count());
                if (slot instanceof FilterBasicSlot)
                    slot.set(stack);
            }
        });
    }
}
