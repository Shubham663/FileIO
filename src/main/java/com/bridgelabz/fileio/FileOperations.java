package com.bridgelabz.fileio;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileOperations {
	static Map<WatchKey, Path> keyToPath = new HashMap<>();

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
			System.out.println("The containing directory was not found");
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
		Path path = Paths.get("F:/demo/demo.txt");
		try {
			String fileContent = Files.readString(path);
			String[] words = fileContent.split("\n");
			System.out.println("The number of entries in File: " + words.length);
			return true;
		} catch (IOException e) {
			System.out.println("The mentioned directory was not found");
		}
		return false;
	}

	public static boolean writeToFile(String filePath, Employee employee1) {
		Path path = Paths.get(filePath);
		try {
			String previous = Files.readString(path);
			Files.writeString(path, previous + employee1.toString());
			return true;
		} catch (IOException e) {
			System.out.println("The file was not found");
		}
		return false;
	}

	public static List<Employee> readFile() {
		Path path = Paths.get("F:/demo/demo.txt");
		List<Employee> employeesInFile = new ArrayList<>();
		try {
			String fileContent = Files.readString(path);
			String[] employees = fileContent.split("\n");
			for (String employee : employees) {
				String[] details = employee.split(",");
				int employeeId = Integer.parseInt(details[0].split(":")[1]);
				String name = details[1].split(":")[1];
				double salary = Double.parseDouble(details[2].split(":")[1]);
				employeesInFile.add(new Employee(employeeId, name, salary));
			}
			return employeesInFile;
		} catch (IOException e) {
			System.out.println("The mentioned directory was not found");
		}
		return null;
	}

	class Java8WatchServiceExample {

		private final WatchService watcher;
		private final Map<WatchKey, Path> keys;

		/**
		 * Creates a WatchService and registers the given directory
		 */
		Java8WatchServiceExample(Path dir) throws IOException {
			this.watcher = FileSystems.getDefault().newWatchService();
			this.keys = new HashMap<WatchKey, Path>();

			walkAndRegisterDirectories(dir);
		}

		/**
		 * Register the given directory with the WatchService; This function will be
		 * called by FileVisitor
		 */
		private void registerDirectory(Path dir) throws IOException {
			WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
			keys.put(key, dir);
		}

		/**
		 * Register the given directory, and all its sub-directories, with the
		 * WatchService.
		 */
		private void walkAndRegisterDirectories(final Path start) throws IOException {
			// register directory and sub-directories
			Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
					registerDirectory(dir);
					return FileVisitResult.CONTINUE;
				}
			});
		}

		/**
		 * Process all events for keys queued to the watcher
		 */
		void processEvents() {
			for (;;) {

				// wait for key to be signalled
				WatchKey key;
				try {
					key = watcher.take();
				} catch (InterruptedException x) {
					return;
				}

				Path dir = keys.get(key);
				if (dir == null) {
					System.err.println("WatchKey not recognized!!");
					continue;
				}

				for (WatchEvent<?> event : key.pollEvents()) {
					@SuppressWarnings("rawtypes")
					WatchEvent.Kind kind = event.kind();

					// Context for directory entry event is the file name of entry
					@SuppressWarnings("unchecked")
					Path name = ((WatchEvent<Path>) event).context();
					Path child = dir.resolve(name);

					// print out event
					System.out.format("%s: %s\n", event.kind().name(), child);

					// if directory is created, and watching recursively, then register it and its
					// sub-directories
					if (kind == ENTRY_CREATE) {
						try {
							if (Files.isDirectory(child)) {
								walkAndRegisterDirectories(child);
							}
						} catch (IOException x) {
							// do something useful
						}
					}
				}

				// reset key and remove from set if directory no longer accessible
				boolean valid = key.reset();
				if (!valid) {
					keys.remove(key);

					// all directories are inaccessible
					if (keys.isEmpty()) {
						break;
					}
				}
			}
		}
	}
}