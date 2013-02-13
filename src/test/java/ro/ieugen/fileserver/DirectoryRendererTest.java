package ro.ieugen.fileserver;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

import java.io.File;

public class DirectoryRendererTest {

    private MustacheTemplate mustacheTemplate = new MustacheTemplate("src/main/resources/index-template.html");
    private DirectoryRenderer directoryRenderer = new DirectoryRenderer(new File(".").toURI(), mustacheTemplate);

    @Test
    public void testListingFailsWithExceptionOnFileList() throws Exception {
        File file = new File("pom.xml");
        assertTrue(file.isFile());
        try {
            String result = directoryRenderer.renderDirectory(file);
            fail("A file was supplied instead of directory");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testListDirectoryWorks() throws Exception {
        File currentWorkingDir = new File(".");
        assertTrue(currentWorkingDir.isDirectory());
        String result = directoryRenderer.renderDirectory(currentWorkingDir);
        System.out.println(result);
        assertThat(result).contains("<html>")
                .contains("src/").contains("pom.xml");
    }
}
