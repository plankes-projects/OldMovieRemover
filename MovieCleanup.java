import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class App {

	public static void main(String[] args) throws NumberFormatException, IOException {
		if (args.length != 4) {
			throw new RuntimeException(
					"Usage: app <whitelist_file> <clean_directory> <max_dirs> <del|safe>     - Test with safe flag!");
		}

		new App().run(args[0], args[1], Integer.parseInt(args[2]),  "del".equals(args[3]));
	}

	private void run(String whiteListFile, String cleanDirectory, int maxDirs, boolean del) throws IOException {
		if (!del) {
			System.out.println("Del flag not set, NO DELETE WILL BE PERFORMED!");
		}

		List<String> whiteListDirs = this.loadWhiteListDirs(whiteListFile);
		// oldest is first
		File[] files = retrieveSortedFileList(cleanDirectory);
		int numToClean = files.length - maxDirs;
		System.out.println("Going to delete " + numToClean);
		for (int i = 0; i < files.length && numToClean > 0; i++) {
			File file = files[i];
			if (whiteListDirs.contains(file.getName())) {
				System.out.println("Whitelist dir: " + file.getName());
			} else {
				System.out.println("Deleting " + file.getName());
				numToClean--;
				if (del) {
					deleteDir(file);
				}
			}
		}
	}

	private void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				deleteDir(f);
			}
		}
		file.delete();
	}

	private File[] retrieveSortedFileList(String cleanDirectory) {
		File[] files = Paths.get(cleanDirectory).toFile().listFiles();

		Arrays.sort(files, new Comparator<File>() {
			public int compare(File f1, File f2) {
				return getCompareValue(f1).compareTo(getCompareValue(f2));
			}
		});
		return files;
	}

	private Long getCompareValue(File f1) {
		try {
			BasicFileAttributes attr = Files.readAttributes(f1.toPath(), BasicFileAttributes.class);
			return attr.creationTime().toMillis();
		} catch (IOException e) {
			return f1.lastModified();
		}
	}

	private List<String> loadWhiteListDirs(String whiteListFile) throws IOException {
		List<String> whiteListDirs = Files.readAllLines(Paths.get(whiteListFile));
		for (int i = 0; i < whiteListDirs.size(); i++) {
			Path current = Paths.get(whiteListDirs.get(i));
			whiteListDirs.set(i, current.getParent().getFileName().toString());
		}
		return whiteListDirs;
	}
}
