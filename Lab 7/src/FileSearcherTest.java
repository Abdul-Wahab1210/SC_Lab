import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileSearcherTest {
    private static Path tempDir;

    @BeforeClass
    public static void setup() throws IOException {
        tempDir = Files.createTempDirectory("fileSearcherTestDir");

        Files.createDirectory(tempDir.resolve("subdir1"));
        Files.createDirectory(tempDir.resolve("subdir2"));

        Files.createFile(tempDir.resolve("file1.txt"));
        Files.createFile(tempDir.resolve("subdir1/file1.txt"));
        Files.createFile(tempDir.resolve("subdir2/file2.txt"));
        Files.createFile(tempDir.resolve("subdir2/FILE1.TXT")); 
    }

    @AfterClass
    public static void teardown() throws IOException {
        Files.walk(tempDir)
                .map(Path::toFile)
                .forEach(File::delete);
    }

    @Test
    public void testFileFound() {
        FileSearcher.resetFileCount(); 
        File directory = tempDir.toFile();
        FileSearcher.searchFile(directory, "file1.txt", false);

        assertEquals("Should find 2 occurrences of file1.txt with case-sensitive search", 2, FileSearcher.getFileCount());
    }

    @Test
    public void testFileNotFound() {
        FileSearcher.resetFileCount();
        File directory = tempDir.toFile();
        FileSearcher.searchFile(directory, "nonExistentFile.txt", false);

        assertEquals("Should not find any occurrences of nonExistentFile.txt", 0, FileSearcher.getFileCount());
    }

    @Test
    public void testCaseInsensitiveSearch() {
        FileSearcher.resetFileCount();
        File directory = tempDir.toFile();
        FileSearcher.searchFile(directory, "file1.txt", true);

        assertEquals("Should find 3 occurrences of file1.txt with case-insensitive search", 3, FileSearcher.getFileCount());
    }

    @Test
    public void testCaseSensitiveSearch() {
        FileSearcher.resetFileCount();
        File directory = tempDir.toFile();
        FileSearcher.searchFile(directory, "FILE1.TXT", false);

        assertEquals("Should find 1 occurrence of FILE1.TXT with case-sensitive search", 1, FileSearcher.getFileCount());
    }

    @Test
    public void testDirectoryNotExist() {
        File nonExistentDir = new File(tempDir.toFile(), "nonExistentDir");
        FileSearcher.resetFileCount();
        
        String output = captureConsoleOutput(() -> FileSearcher.searchFile(nonExistentDir, "file1.txt", false));

        assertTrue("Should output an error message for a non-existent directory", output.contains("Unable to access directory"));
    }

    private String captureConsoleOutput(Runnable task) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        task.run();
        System.setOut(originalOut);
        return outputStream.toString();
    }
}
