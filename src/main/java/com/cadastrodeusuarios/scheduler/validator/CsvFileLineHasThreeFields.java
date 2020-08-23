package com.cadastrodeusuarios.scheduler.validator;

import java.util.List;

public class CsvFileLineHasThreeFields extends CsvFileLineValidator {

    @Override
    public void check(List<String> errors, String line) {

        Integer fields = line.split(";").length;

        if (fields != 3) {
            errors.add("Invalid number of fields!");
            return;
        }
        checkNext(errors, line);
    }
}
