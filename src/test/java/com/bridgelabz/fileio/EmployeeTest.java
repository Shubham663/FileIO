package com.bridgelabz.fileio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class EmployeeTest {
	Path path;

	/**
	 * Checks that the method returns an Employee Object
	 */

	@Test
	public void getEmployeeDetailsTest() {
		Employee employee1 = EmployeePayrollService.getEmployee();
		assertNotNull("\nMethod does not return Employee OBject\n", employee1);
	}

	/**
	 * Test for checking successful file creation
	 */
	@Test
	public void fileExistsTest_Correct() {
		path = Paths.get("C:/Users/desktop.ini");
		assertTrue(Files.exists(path));
	}

	/**
	 * Fails as directory not present
	 */
	@Test
	public void fileExistsTest_InCorrect() {
		path = Paths.get("C:/Users/desktop");
		assertFalse(Files.exists(path));
	}

	/**
	 * Deletes the file
	 */
	@Test
	public void fileDeletionTest_Correct() {
		assertTrue(FileOperations.createDirectory("C:", "C:/demo"));
		assertTrue(FileOperations.createFile("C:/demo", "C:/demo/demo.txt"));
		assertTrue(FileOperations.deleteFile("C:/demo", "C:/demo/demo.txt"));
	}

	/**
	 * cannot delete the file as the directory specified not present
	 */
	@Test
	public void fileDeletionTest_InCorrect() {
		assertFalse(FileOperations.deleteFile("C:/demo", "C:/demo/demo.txt"));
	}

	/**
	 * creates the file
	 */
	@Test
	public void fileCreationTest_Correct() {
		assertTrue(FileOperations.createDirectory("C:", "C:/demo"));
		assertTrue(FileOperations.createFile("C:/demo", "C:/demo/demo.txt"));
	}

	/**
	 * unable to create file because directory not present
	 */
	@Test
	public void fileCreationTest_InCorrect() {
		assertFalse(FileOperations.createFile("C:/demo", "C:/demo/demo.txt"));
	}

	/**
	 * Successful directory creation
	 */
	@Test
	public void directoryCreationTest_Correct() {
		assertTrue(FileOperations.createDirectory("C:", "C:/demo"));
	}

	/**
	 * unsuccessful directory creation as parent directory not found
	 */
	@Test
	public void directoryCreationTest_InCorrect() {
		assertFalse(FileOperations.createFile("Z:", "Z:/demo"));
	}

	/**
	 * Lists the files of a particular directory
	 */
	@Test
	public void listFilesTest_Correct() {
		assertTrue(FileOperations.listDirectoryContents());
	}

	@After
	public void remove() {
		path = Paths.get("C:/demo");
		if (Files.exists(path)) {
			path = Paths.get("C:/demo/demo.txt");
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					System.out.println("The file does not exist");
					e.printStackTrace();
				}
			}
			path = Paths.get("C:/demo");
			try {
				Files.delete(path);
			} catch (IOException e) {
				System.out.println("The directory does not exist");
				e.printStackTrace();
			}
		}
	}
}
