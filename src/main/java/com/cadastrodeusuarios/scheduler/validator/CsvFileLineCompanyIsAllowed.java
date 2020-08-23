package com.cadastrodeusuarios.scheduler.validator;

import com.cadastrodeusuarios.constants.Company;

import java.util.Arrays;
import java.util.List;

public class CsvFileLineCompanyIsAllowed extends CsvFileLineValidator {

    @Override
    public void check(List<String> errors, String line) {

        String fieldCompanyId = line.split(";")[0];

        List<Integer> validCompanies = Arrays.asList(Company.ALLOWED_COMPANIES);
        if (!validCompanies.contains(Integer.valueOf(fieldCompanyId))) {
            errors.add("Company ID not allowed");
        }
        checkNext(errors, line);
    }
}
