package com.yang.io.socket;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class TextFieldTest {

    public static void main(String[] args) throws IOException {
        Employee[] staff = new Employee[3];
        staff[0] = new Employee("car cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 4000, 1990, 3, 15);
        File file = new File("e:/employee.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        PrintWriter out = new PrintWriter(file, "UTF-8");
        writeData(staff, out);

    }

    private static void writeData(Employee[] employees, PrintWriter out) {
        out.println(employees.length);
        for (Employee e : employees) {
            writeEmployee(out, e);
        }
    }

    private static void writeEmployee(PrintWriter out, Employee employee) {
        out.println(employee.getName() + "|" + employee.getSalary() + "|" + employee.getYear());
        out.flush();
    }
}
