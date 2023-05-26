package com.itmolabs.commands.commands;

import com.itmolabs.application.InputReader;
import com.itmolabs.commands.actions.UpdateAction;
import com.itmolabs.model.Ticket;

public class UpdateCommand extends Command {

    private UpdateAction updateAction;
    private Ticket arg;

    public UpdateCommand(UpdateAction updateAction) {
        this.updateAction = updateAction;
    }

    public String execute(String strKey) {
        try {
            Integer key = Integer.parseInt(strKey);
            if (arg == null) {
                this.arg = new InputReader(this.updateAction.getCollectionManager()).receiveTicket();
            }
            String result = this.updateAction.doAction(key, arg);
            this.arg = null;
            return result;
        } catch (NumberFormatException numberFormatException) {
            return "Incorrect arguments";
        }
    }

}

