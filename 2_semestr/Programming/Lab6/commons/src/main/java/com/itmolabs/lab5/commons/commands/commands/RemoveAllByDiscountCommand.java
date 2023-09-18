package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

@Command(
        value = "remove_all_by_discount",
        usage = "remove_all_by_discount <discount>",
        description = "Remove all elements from the collection by discount"
)
public final class RemoveAllByDiscountCommand extends BaseCommand {

    public RemoveAllByDiscountCommand() {
        super(RemoveAllByDiscountCommand.class);
    }

    @Override
    public String execute(final Object... args) throws IOException {
        if (args.length == 0) return "Invalid arguments. Try again.";

        float discount;

        if (args[0] instanceof String) discount = Float.parseFloat((String) args[0]);
        else discount = (float) args[0];

        if (discount <= 0) return "Command failed. Discount cannot be negative.";

        if (isClient()) return sendPacket(discount);
        else {
            int counter = 0;

            // без копии, будет ConcurrentModificationException
            for (Entry<Integer, Ticket> entry :
                        new HashMap<>(getCollection()).entrySet()) {
                if (entry.getValue().getDiscount() != discount) continue;

                counter++;
                getCollection().remove(entry.getKey());
            }

            return "Command executed. Removed " + counter + " elements.";
        }
    }
}
