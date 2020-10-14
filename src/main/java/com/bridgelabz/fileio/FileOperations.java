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
import java.util.HashMap;
import java.util.Map;

public class FileOperations {
	static Map<WatchKey,Path> keyToPath = new HashMap<>();
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
			String previous = Files.readString(path);
			Files.writeString(path,previous + "," + employee1.toString() + ",");
			return true;
		} catch (IOException e) {
			System.out.println("The file was not found");
		}
		return false;
	}

	public static boolean readFile() {
		Path path = Paths.get("C:/demo/demo.txt");
		try {
			String fileContent = Files.readString(path);
			String []employees = fileContent.split(",");
			for(String employee:employees)
				System.out.println(employee);
			return true;
		} catch (IOException e) {
			System.out.println("The mentioned directory was not found");
		}
		return false;
	}
	
//	@SuppressWarnings("unchecked")
//	public static void createWatchService(String filePath) {
//		Path path = Paths.get(filePath);
//		WatchService watchService = null;
//		WatchKey key = null;
//		try {
//			watchService = FileSystems.getDefault().newWatchService();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		final WatchService watchService2 = watchService;
//		try {
//			key = path.register(watchService,ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		keyToPath.put(key, path);
//		while(true) {
//			WatchKey queueKey = null;
//			try {
//				queueKey = watchService.take();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Path path2 = keyToPath.get(queueKey);
//			for(WatchEvent watchEvent : queueKey.pollEvents()) {
//				Path newPath = (Path)watchEvent.context();
//				System.out.println(watchEvent.kind() + " " + watchEvent.context() );
//				Path child = path2.resolve(newPath);
//				
//				if(watchEvent.kind() == ENTRY_CREATE && Files.isDirectory(child))
//				{
//					try {
//					Files.walkFileTree(child, new SimpleFileVisitor<Path>(){
//						@Override
//						public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
//								throws IOException {
////							if(Files.isDirectory(child)) 
////							path = child;
////							if(Files.isDirectory(child)){
////							WatchKey key = dir.register(watchService2, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
////							keyToPath.put(key, dir);
//							return CONTINUE;
////							}
////							return FileVisitResult.SKIP_SUBTREE;
//						}
//					});
//				} catch (IOException e) {
////					e.printStackTrace();
//					System.out.println("yoyo");
//				}
//				}
//			boolean valid = queueKey.reset();
//			if(!valid)
//				break;
//		}
//	}
	
//}
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
     * Register the given directory with the WatchService; This function will be called by FileVisitor
     */
    private void registerDirectory(Path dir) throws IOException 
    {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        keys.put(key, dir);
    }
 
    /**
     * Register the given directory, and all its sub-directories, with the WatchService.
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
                Path name = ((WatchEvent<Path>)event).context();
                Path child = dir.resolve(name);
 
                // print out event
                System.out.format("%s: %s\n", event.kind().name(), child);
 
                // if directory is created, and watching recursively, then register it and its sub-directories
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