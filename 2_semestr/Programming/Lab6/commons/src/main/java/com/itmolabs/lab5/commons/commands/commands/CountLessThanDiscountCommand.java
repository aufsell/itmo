package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;
import com.itmolabs.lab5.model.ticket.Ticket;

import java.io.IOException;
import java.util.Map;

@Command(
        value = "count_less_than_discount",
        usage = "count_less_than_discount <discount>",
        description = "Count items, field discount of which is lower than a given"
)
public final class CountLessThanDiscountCommand extends BaseCommand {

    public CountLessThanDiscountCommand() {
        super(CountLessThanDiscountCommand.class);
    }

    @Override
    public String execute(final Object... args) throws IOException {
        if (args.length == 0) return "Invalid arguments. Try again.";

        float discount;

        if (args[0] instanceof String) discount = Float.parseFloat((String) args[0]);
        else discount = (float) args[0];

        if (discount < 0) return "Command failed. Discount cannot be negative.";

        if (isClient()) return sendPacket(discount);
        else {
            int count = 0;

            for (Map.Entry<Integer, Ticket> entry : getCollection()
                    .entrySet()) {
                if (entry.getValue().getDiscount() == discount) count++;
            }

            return "Command completed successfully. Count: " + count + " items";
        }
    }
}
