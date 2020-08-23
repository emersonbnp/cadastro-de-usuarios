package com.cadastrodeusuarios.scheduler.validator;

import java.util.List;

public abstract class CsvFileLineValidator {

    private CsvFileLineValidator next;

    public CsvFileLineValidator linkWith(CsvFileLineValidator csvFileLineValidator) {
        next = csvFileLineValidator;
        return next;
    }

    public abstract void check(List<String> errors, String line);

    protected void checkNext(List<String> errors, String line) {
        if (next == null) {
            return;
        } else {
            next.check(errors, line);
        }
    }
}
