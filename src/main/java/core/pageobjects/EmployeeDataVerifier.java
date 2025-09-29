package core.pageobjects;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

public class EmployeeDataVerifier {

    public Map<Integer, Employee> readExcelData(String excelFilePath) {
        Map<Integer, Employee> excelData = new HashMap<>();
        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(1);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header

                int empId = (int) row.getCell(0).getNumericCellValue();
                String empName = row.getCell(1).getStringCellValue();
                int deptId = (int) row.getCell(2).getNumericCellValue();
                int salary = (int) row.getCell(3).getNumericCellValue();

                Employee emp = new Employee(empId, empName, deptId, salary);
                excelData.put(empId, emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return excelData;
    }

    public Map<Integer, Employee> readDatabaseData(String jdbcUrl, String dbUser, String dbPass) {
        Map<Integer, Employee> dbData = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(jdbcUrl, dbUser, dbPass);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT empid, empname, departmentid, salary FROM employee")) {

            while (rs.next()) {
                int empId = rs.getInt("empid");
                String empName = rs.getString("empname");
                int deptId = rs.getInt("departmentid");
                int salary = rs.getInt("salary");

                Employee emp = new Employee(empId, empName, deptId, salary);
                dbData.put(empId, emp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbData;
    }

    public void compareData(Map<Integer, Employee> excelData, Map<Integer, Employee> dbData) {
        for (Integer empId : excelData.keySet()) {
            Employee excelEmp = excelData.get(empId);
            Employee dbEmp = dbData.get(empId);

            if (dbEmp == null) {
                System.out.println("Employee ID " + empId + " NOT FOUND in database.");
            } else if (excelEmp.equals(dbEmp)) {
                System.out.println("Employee ID " + empId + " MATCHES.");
            } else {
                System.out.println("Employee ID " + empId + " DATA MISMATCH!");
                System.out.println("Excel: " + excelEmp);
                System.out.println("DB:    " + dbEmp);
            }
        }
    }

    public static class Employee {
        int empId;
        String empName;
        int departmentId;
        int salary;

        public Employee(int empId, String empName, int departmentId, int salary) {
            this.empId = empId;
            this.empName = empName;
            this.departmentId = departmentId;
            this.salary = salary;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Employee)) return false;
            Employee emp = (Employee) o;
            return empId == emp.empId &&
                    departmentId == emp.departmentId &&
                    salary == emp.salary &&
                    Objects.equals(empName, emp.empName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(empId, empName, departmentId, salary);
        }

        @Override
        public String toString() {
            return "[empId=" + empId + ", empName=" + empName + ", departmentId=" + departmentId + ", salary=" + salary + "]";
        }
    }
}

