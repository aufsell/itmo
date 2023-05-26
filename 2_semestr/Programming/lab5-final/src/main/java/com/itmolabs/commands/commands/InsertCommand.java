package com.itmolabs.commands.commands;

import com.itmolabs.application.InputReader;
import com.itmolabs.commands.actions.InsertAction;
import com.itmolabs.model.Ticket;

public class InsertCommand extends Command {

    private final InsertAction insertAction;
    private Ticket arg;

    public InsertCommand(InsertAction insertAction) {
        this.insertAction = insertAction;
    }

    public String execute(String strKey) {
        try {
            Integer key = Integer.parseInt(strKey);
            if (arg == null) {
                this.arg = new InputReader(this.insertAction.getCollectionManager()).receiveTicket();
            }
            String result = this.insertAction.doAction(key, arg);
            this.arg = null;
            return result;

        } catch (NumberFormatException numberFormatException) {
            return "Incorrect arguments";
        }
    }

    public void setArg(Ticket arg) {
        this.arg = arg;
    }
}
