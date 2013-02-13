package ro.ieugen.fileserver.files;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.io.File;

public class FileToFileInfoPredicateTest {

    private FileToFileInfoFunction function = new FileToFileInfoFunction(new File("src/").toURI());

    @Test
    public void testRelativizePathForDirectory() throws Exception {
        File srcMainJava = new File("src/main/java/ro");
        String result = function.relativePath(srcMainJava);
        assertThat(result).isEqualToIgnoringCase("main/java/ro/");
    }

    @Test
    public void testRelativizePathForFile() throws Exception {
        File unixXml = new File("src/main/assembly/unix.xml");
        String result = function.relativePath(unixXml);
        assertThat(result).isEqualTo("main/assembly/unix.xml");
    }

    @Test
    public void testRelativizePathWithImproperRelativeUrl() throws Exception {
        File validBadFilePath = new File("../pom.xml");
        try {
            String result = function.relativePath(validBadFilePath);
            fail("Should throw IllegalStateException");
        } catch (IllegalStateException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testRelativizePathWithGoodRelativeUrl() throws Exception {
        File validFilePath = new File("src/main/../test");
        String result = function.relativePath(validFilePath);
        assertThat(result).isEqualTo("test/");
    }
}
