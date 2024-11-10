import java.io.File;

public class FileSearcher {
    private static int fileCount = 0;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java FileSearcher <directory-path> <file-name1> <file-name2> ... [-i]");
            System.exit(1);
        }

        String dirpath = args[0];
        boolean caseInsen = args[args.length - 1].equalsIgnoreCase("-i");
        int fileNamesEnd = caseInsen ? args.length - 1 : args.length;
        String[] fileNames = new String[fileNamesEnd - 1];
        System.arraycopy(args, 1, fileNames, 0, fileNamesEnd - 1);

        File directory = new File(dirpath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("The specified directory does not exist or is not a directory.");
            System.exit(1);
        }

        for (String fileName : fileNames) {
            fileCount = 0; 
            System.out.printf("Searching for file: %s\n", fileName);
            searchFile(directory, fileName, caseInsen);

            if (fileCount == 0) {
                System.out.printf("File '%s' not found.\n", fileName);
            } else {
                System.out.printf("File '%s' found %d time(s).\n", fileName, fileCount);
            }
        }
    }

    public static void resetFileCount() {
        fileCount = 0;
    }

    public static int getFileCount() {
        return fileCount;
    }

    public static void searchFile(File dir, String fileName, boolean caseInsen) {
        File[] files = dir.listFiles(); // List all files and directories

        // Error handling if files are not accessible
        if (files == null) {
            System.out.println("Unable to access directory: " + dir.getAbsolutePath());
            return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                searchFile(file, fileName, caseInsen);
            } else {
                if ((caseInsen && file.getName().equalsIgnoreCase(fileName)) ||
                        (!caseInsen && file.getName().equals(fileName))) {
                    System.out.println("File found at: " + file.getAbsolutePath());
                    fileCount++;
                }
            }
        }
    }
}
