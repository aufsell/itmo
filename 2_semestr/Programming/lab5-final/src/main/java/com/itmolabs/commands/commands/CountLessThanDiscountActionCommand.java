package com.itmolabs.commands.commands;

import com.itmolabs.commands.actions.CountLessThanDiscountAction;
import com.itmolabs.commands.interfaces.OneArgCommand;

public class CountLessThanDiscountActionCommand extends Command implements OneArgCommand {

    private final CountLessThanDiscountAction countLessThanDiscountAction;

    public CountLessThanDiscountActionCommand(CountLessThanDiscountAction countLessThanDiscountAction) {
        this.countLessThanDiscountAction = countLessThanDiscountAction;
    }

    public String execute(String argument) {
        try {
            Float discount = Float.parseFloat(argument);
            return this.countLessThanDiscountAction.execute(discount);
        } catch (NumberFormatException numberFormatException) {
            return "Incorrect argument. Try again.";
        }
    }
}
