package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.RemoveAllByDiscountAction;
import com.itmolabs.commands.interfaces.OneArgCommand;

public class RemoveAllByDiscountCommand extends Command implements OneArgCommand {

    private RemoveAllByDiscountAction removeAllByDiscountAction;

    public RemoveAllByDiscountCommand(RemoveAllByDiscountAction removeAllByDiscountAction) {
        this.removeAllByDiscountAction = removeAllByDiscountAction;
    }

    public String execute(String argument) {
        try {
            Float discount = Float.parseFloat(argument);
            return this.removeAllByDiscountAction.execute(discount);
        } catch (NumberFormatException numberFormatException) {
            return "Incorrect argument.";
        }
    }

}
