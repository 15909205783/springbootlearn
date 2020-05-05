package com.yang.io.socket;

import java.io.*;

public class ObjectTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Employee harry = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        Manager carl = new Manager("Carl Cracker", 8000, 1987, 12, 15);
        carl.setSecretary(harry);
        Manager tony = new Manager("Tony Tester", 4000, 1990, 3, 15);
        tony.setSecretary(harry);
        Employee[] staff = new Employee[3];
        staff[0] = carl;
        staff[1] = harry;
        staff[2] = tony;
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("e:/employee.dat"))) {
            out.writeObject(staff);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("e:/employee.dat"))) {
            Employee[] newStaff = (Employee[]) in.readObject();
            newStaff[1].raiseSalary(10);
            for (Employee e : newStaff) {
                System.out.println(e);
            }
        }


    }
}
