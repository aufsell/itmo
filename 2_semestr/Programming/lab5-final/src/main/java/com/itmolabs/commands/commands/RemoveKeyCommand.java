package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.RemoveKeyAction;
import com.itmolabs.commands.interfaces.OneArgCommand;

public class RemoveKeyCommand extends Command implements OneArgCommand {

    private RemoveKeyAction removeKeyAction;

    public RemoveKeyCommand(RemoveKeyAction removeKeyAction) {
        this.removeKeyAction = removeKeyAction;
    }

    public String execute(String argument) {
        try {
            Integer key = Integer.parseInt(argument);
            return this.removeKeyAction.doAction(key);
        } catch (NumberFormatException numberFormatException) {
            return "Incorrect argument.";
        }
    }

}
