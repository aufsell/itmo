package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.ShowAction;
import com.itmolabs.commands.interfaces.NoArgCommand;

public class ShowCommand extends Command implements NoArgCommand {

    private final ShowAction showAction;

    public ShowCommand(ShowAction showAction) {
        this.showAction = showAction;
    }

    public String execute() {
        return this.showAction.doAction();
    }

}
