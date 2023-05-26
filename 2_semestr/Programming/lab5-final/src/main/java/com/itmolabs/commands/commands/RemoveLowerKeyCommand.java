package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.RemoveKeyAction;
import com.itmolabs.commands.actions.RemoveLowerKeyAction;
import com.itmolabs.commands.interfaces.OneArgCommand;

public class RemoveLowerKeyCommand extends Command implements OneArgCommand {

    private RemoveLowerKeyAction removeLowerKeyAction;

    public RemoveLowerKeyCommand(RemoveLowerKeyAction removeKeyAction) {
        this.removeLowerKeyAction = removeKeyAction;
    }

    public String execute(String argument) {
        try {
            return this.removeLowerKeyAction.doAction(Integer.parseInt(argument));
        } catch (NumberFormatException numberFormatException) {
            return "Incorrect argument.";
        }
    }


}
