package com.cadastrodeusuarios.scheduler.validator;

import java.util.List;

public class CsvFileLineHasCompanyId extends CsvFileLineValidator {

    @Override
    public void check(List<String> errors, String line) {

        String fieldCompanyId = line.split(";")[0];

        if (fieldCompanyId == null || Integer.valueOf(fieldCompanyId) < 0) {
            errors.add("Invalid company ID");
        }
        checkNext(errors, line);
    }
}
