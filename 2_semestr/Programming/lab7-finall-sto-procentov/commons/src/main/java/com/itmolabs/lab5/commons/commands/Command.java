package com.itmolabs.lab5.commons.commands;

import java.lang.annotation.*;

/**
 * <br>Главная аннотация для команды
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {

    String value();

    String usage() default "";

    String description() default "";

}
