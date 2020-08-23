package com.cadastrodeusuarios.scheduler.validator;

import java.text.SimpleDateFormat;
import java.util.List;

public class CsvFileLineHasValidBirthdate extends CsvFileLineValidator {

    @Override
    public void check(List<String> errors, String line) {

        String fieldBirthdate = line.split(";")[2];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);

        try {
            sdf.parse(fieldBirthdate);
        } catch (Exception e) {
            errors.add("Invalid birthdate");
        }
        checkNext(errors, line);
    }
}
