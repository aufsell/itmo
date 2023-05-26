package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.ExecuteScriptAction;
import com.itmolabs.commands.interfaces.OneArgCommand;

public class ExecuteScriptCommand extends Command implements OneArgCommand {

    private final ExecuteScriptAction executeScriptAction;

    public ExecuteScriptCommand(ExecuteScriptAction executeScriptAction) {
        this.executeScriptAction = executeScriptAction;
    }

    public String execute(String filePath) {
        return this.executeScriptAction.doAction(filePath);
    }

}