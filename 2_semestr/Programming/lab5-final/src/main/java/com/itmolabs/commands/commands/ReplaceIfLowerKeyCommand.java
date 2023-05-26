package com.itmolabs.commands.commands;

import com.itmolabs.application.InputReader;
import com.itmolabs.commands.actions.ReplaceIfLowerKeyAction;
import com.itmolabs.model.Ticket;

public class ReplaceIfLowerKeyCommand extends Command {

    private final ReplaceIfLowerKeyAction replaceIfLowerKeyCommand;
    private Ticket arg;

    public ReplaceIfLowerKeyCommand(ReplaceIfLowerKeyAction replaceIfLowerKeyAction) {
        this.replaceIfLowerKeyCommand = replaceIfLowerKeyAction;
    }

    public String execute(String strKey) {
        try {
            Integer key = Integer.parseInt(strKey);
            if (arg == null) {
                this.arg = new InputReader(this.replaceIfLowerKeyCommand.getCollectionManager()).receiveTicket();
            }
            String result = this.replaceIfLowerKeyCommand.doAction(key, arg);
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
