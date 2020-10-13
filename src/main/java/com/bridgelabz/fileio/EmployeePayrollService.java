package com.bridgelabz.fileio;

import java.util.Scanner;

public class EmployeePayrollService {

	/**
	 * @return returns the Employee Object after getting input from console
	 */
	public static Employee getEmployee() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the id of the employee");
		int employeeID = input.nextInt();
		System.out.println("Enter the name of the Employee");
		input.nextLine(); // reads the next line character
		String name = input.nextLine();
		System.out.println("Enter the salary of the employee");
		double salary = input.nextDouble();
		return new Employee(employeeID, name, salary);
	}

	public static void main(String[] args) {
		Employee employee1 = getEmployee();
		System.out.println(employee1);
	}
}