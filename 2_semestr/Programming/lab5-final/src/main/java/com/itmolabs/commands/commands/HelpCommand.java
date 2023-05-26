package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.HelpAction;
import com.itmolabs.commands.interfaces.NoArgCommand;

public class HelpCommand extends Command implements NoArgCommand {

    private HelpAction helpAction;

    public HelpCommand(HelpAction helpAction) {
        this.helpAction = helpAction;
    }

    public String execute() {
        return this.helpAction.doAction();
    }

}
