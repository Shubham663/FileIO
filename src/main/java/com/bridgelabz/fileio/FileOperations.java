package com.bridgelabz.fileio;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileOperations {
	/**
	 * @return returns true if directory is created
	 */
	public static boolean createDirectory(String containingDirectory, String toBeCreated) {
		boolean flag = false;
		Path path = Paths.get(containingDirectory);
		if (Files.exists(path)) {
			path = Paths.get(toBeCreated);
			if (Files.exists(path))
				return true;
			try {
				Files.createDirectory(path);
				flag = true;
			} catch (IOException e) {
				System.out.println("The path specified does not exist");
			}
		} else
			System.out.println("The conataining directory was not found");
		return flag;
	}

	/**
	 * @return returns true if file is created
	 */
	public static boolean createFile(String containingDirectory, String toBeCreated) {
		boolean flag = false;
		Path path = Paths.get(containingDirectory);
		if (Files.exists(path)) {
			path = Paths.get(toBeCreated);
			if (Files.exists(path))
				return true;
			try {
				Files.createFile(path);
				flag = true;
			} catch (IOException e) {
				System.out.println("The path specified does not exist");
			}
		} else
			System.out.println("The conataining directory was not found");
		return flag;
	}

	/**
	 * @return returns true if file deletion successful
	 */
	public static boolean deleteFile(String containingDirectory, String toBeDeleted) {
		boolean flag = false;
		Path path = Paths.get(containingDirectory);
		if (Files.exists(path)) {
			path = Paths.get(toBeDeleted);
			try {
				Files.delete(path);
				flag = true;
			} catch (IOException e) {
				System.out.println("The path specified does not exist");
			}
		} else
			System.out.println("The conataining directory was not found");
		return flag;
	}

	/**
	 * @return, returns true if directory contents printed successfully
	 */
	public static boolean listDirectoryContents() {
		Path path = Paths.get("F:/");
		try {
			Files.newDirectoryStream(path).forEach(System.out::println);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * @return returns true if file read successfully
	 */
	public static boolean noOfEntries() {
		Path path = Paths.get("C:/demo/demo.txt");
		try {
			String fileContent = Files.readString(path);
			String []words = fileContent.split(",");
			System.out.println("The number of entries in File: " + words.length );
			return true;
		} catch (IOException e) {
			System.out.println("The mentioned directory was not found");
		}
		return false;
	}

	public static boolean writeToFile(String filePath, Employee employee1) {
		Path path = Paths.get(filePath);
		try {
			Files.writeString(path,employee1.toString() + ",\n");
			return true;
		} catch (IOException e) {
			System.out.println("The file was not found");
		}
		return false;
	}
}