package com.cadastrodeusuarios.scheduler.validator;

import io.micrometer.core.instrument.util.StringUtils;

import java.util.List;

public class CsvFileLineHasValidEmail extends CsvFileLineValidator {

    @Override
    public void check(List<String> errors, String line) {

        String fieldEmail = line.split(";")[1];

        if (StringUtils.isBlank(fieldEmail) || !fieldEmail.matches(".+@.+\\..+")) {
            errors.add("Invalid email");
        }
        checkNext(errors, line);
    }
}
