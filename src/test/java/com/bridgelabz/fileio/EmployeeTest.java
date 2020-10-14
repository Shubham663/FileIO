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
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		assertTrue(FileOperations.createFile("F:/demo", "F:/demo/demo.txt"));
		assertTrue(FileOperations.deleteFile("F:/demo", "F:/demo/demo.txt"));
	}

	/**
	 * cannot delete the file as the directory specified not present
	 */
	@Test
	public void fileDeletionTest_InCorrect() {
		assertFalse(FileOperations.deleteFile("F:/demo", "F:/demo/demo.txt"));
	}

	/**
	 * creates the file
	 */
	@Test
	public void fileCreationTest_Correct() {
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		assertTrue(FileOperations.createFile("F:/demo", "F:/demo/demo.txt"));
	}

	/**
	 * unable to create file because directory not present
	 */
	@Test
	public void fileCreationTest_InCorrect() {
		assertFalse(FileOperations.createFile("F:/demo", "F:/demo/demo.txt"));
	}

	/**
	 * Successful directory creation
	 */
	@Test
	public void directoryCreationTest_Correct() {
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
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
	
	@Test
	public void noOfEntriesTest_Correct() {
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		assertTrue(FileOperations.createFile("F:/demo", "F:/demo/demo.txt"));
		assertTrue(FileOperations.noOfEntries());
	}
	
	@Test
	public void writeDetailsToFileTest_Correct() {
		Employee employee1 = EmployeePayrollService.getEmployee();
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		assertTrue(FileOperations.createFile("F:/demo", "F:/demo/demo.txt"));
		assertTrue(FileOperations.writeToFile("F:/demo/demo.txt",employee1));
	}

	@Test
	public void createWatchlist_Correct() {
		 Path dir = Paths.get("F:/youtube/");
	     try {
			new Java8WatchServiceExample(dir).processEvents();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		FileOperations.createWatchService("F:/");
	}
	
	
	@Test
	public void countDetailsWrittenToFileTest_Correct() {
		Employee employee1 = EmployeePayrollService.getEmployee();
		Employee employee2 = employee1;
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		assertTrue(FileOperations.createFile("F:/demo", "F:/demo/demo.txt"));
		assertTrue(FileOperations.writeToFile("F:/demo/demo.txt",employee1));
		assertTrue(FileOperations.writeToFile("F:/demo/demo.txt",employee2));
		assertTrue(FileOperations.noOfEntries());
	}
	
	@Test
	public void readDetailsFromFileTest_Correct() {
		Employee employee1 = EmployeePayrollService.getEmployee();
		Employee employee2 = employee1;
		assertTrue(FileOperations.createDirectory("F:", "F:/demo"));
		assertTrue(FileOperations.createFile("F:/demo", "F:/demo/demo.txt"));
		assertTrue(FileOperations.writeToFile("F:/demo/demo.txt",employee1));
		assertTrue(FileOperations.writeToFile("F:/demo/demo.txt",employee2));
		assertTrue(FileOperations.readFile());
	}
	@After
	public void remove() {
		path = Paths.get("F:/demo");
		if (Files.exists(path)) {
			path = Paths.get("F:/demo/demo.txt");
			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					System.out.println("The file does not exist");
					e.printStackTrace();
				}
			}
			path = Paths.get("F:/demo");
			try {
				Files.delete(path);
			} catch (IOException e) {
				System.out.println("The directory does not exist");
				e.printStackTrace();
			}
		}
	}
}
