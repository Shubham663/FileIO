package com.bridgelabz.fileio;

/**
 * Hello world!
 *
 */
public class Employee {
	private int employeeId;
	private String name;
	private double salary;

	/**
	 * Parameterized Constructor
	 */
	public Employee(int employeeId, String name, double salary) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.salary = salary;
	}

	/**
	 * Prints details of Employee on console
	 */
	@Override
	public String toString() {
		return ("EmployeeID:" + employeeId + ",Name:" + name + ",Salary:"
				+ salary + "\n");
	}
}
