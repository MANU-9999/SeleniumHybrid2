package com.ui;


import core.pageobjects.EmployeeDataVerifier;
import core.testutils.ListenerUtility;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Map;

@Listeners(ListenerUtility.class)
public class EmployeeDataTest extends TestBase {

    @Test
    public void verifyEmployeeDataFromExcelAndDB() {
        String excelFilePath = "C:/Users/manoj/Downloads/employee.xlsx";
        String jdbcUrl = "jdbc:mysql://localhost:3306/test";
        String dbUser = "root";
        String dbPass = "";

        EmployeeDataVerifier verifier = new EmployeeDataVerifier();

        Map<Integer, EmployeeDataVerifier.Employee> excelData = verifier.readExcelData(excelFilePath);
        Map<Integer, EmployeeDataVerifier.Employee> dbData = verifier.readDatabaseData(jdbcUrl, dbUser, dbPass);

        verifier.compareData(excelData, dbData);
    }
}
