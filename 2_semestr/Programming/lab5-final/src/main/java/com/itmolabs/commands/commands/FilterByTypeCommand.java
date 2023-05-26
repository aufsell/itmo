package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.FilterByTypeAction;
import com.itmolabs.commands.interfaces.OneArgCommand;
import com.itmolabs.model.TicketType;

public class FilterByTypeCommand  extends Command implements OneArgCommand {

    private FilterByTypeAction filterByTypeAction;

    public FilterByTypeCommand(FilterByTypeAction filterByTypeAction) {
        this.filterByTypeAction = filterByTypeAction;
    }

    public String execute(String argument) {
        try {
            TicketType ticketType = TicketType.valueOf(argument.toUpperCase());
            return this.filterByTypeAction.doAction(ticketType);
        } catch (IllegalArgumentException illegalArgumentException) {
            return "Incorrect argument.";
        }
    }

}
