package com.itmolabs.commands.commands;

import com.itmolabs.application.InputReader;
import com.itmolabs.commands.actions.ReplaceIfGreaterKeyAction;
import com.itmolabs.model.Ticket;

public class ReplaceIfGreaterKeyCommand extends Command {

    private ReplaceIfGreaterKeyAction replaceIfGreaterKeyCommand;
    private Ticket arg;

    public ReplaceIfGreaterKeyCommand(ReplaceIfGreaterKeyAction replaceIfGreaterKeyAction) {
        this.replaceIfGreaterKeyCommand = replaceIfGreaterKeyAction;
    }

    public String execute(String strKey) {
        try {
            Integer key = Integer.parseInt(strKey);
            if (arg == null) {
                this.arg = new InputReader(this.replaceIfGreaterKeyCommand.getCollectionManager()).receiveTicket();
            }
            String result = this.replaceIfGreaterKeyCommand.doAction(key, arg);
            this.arg = null;
            return result;

        } catch (NumberFormatException numberFormatException) {
            return "Incorrect argument.";
        }
    }

    public void setArg(Ticket arg) {
        this.arg = arg;
    }


}
