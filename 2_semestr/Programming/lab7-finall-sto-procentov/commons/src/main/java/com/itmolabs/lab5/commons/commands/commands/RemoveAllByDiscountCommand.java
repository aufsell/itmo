package com.itmolabs.lab5.commons.commands.commands;

import com.itmolabs.lab5.auth.AuthUser;
import com.itmolabs.lab5.commons.commands.BaseCommand;
import com.itmolabs.lab5.commons.commands.Command;

import java.io.IOException;

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
    public String execute(final AuthUser authUser, final Object... args) throws IOException {
        if (args.length == 0) return "Invalid arguments. Try again.";

        float discount;

        if (args[0] instanceof String) discount = Float.parseFloat((String) args[0]);
        else discount = (float) args[0];

        if (discount <= 0) return "Command failed. Discount cannot be negative.";

        if (isClient()) return sendPacket(authUser, discount);
        else {
            var tickets = getDatabase().getCache().values()
                    .stream()
                    .filter(ticket -> ticket.getOwner() == authUser.getId())
                    .toList();

            if (tickets.isEmpty()) return "You not owner of any ticket!";

            int counter = 0;

            // без копии, будет ConcurrentModificationException
            for (final var ticket : tickets) {
                if (ticket.getDiscount() != discount) continue;

                counter++;
                getDatabase().deleteTicket(ticket.getId());
            }

            return "Command executed. Removed " + counter + " elements.";
        }
    }
}
