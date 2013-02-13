package ro.ieugen.fileserver;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;

import java.io.File;
import java.util.List;

public class DirectoryRendererTest {

    private MustacheTemplate mustacheTemplate = new MustacheTemplate("src/main/resources/index-template.html");
    private DirectoryRenderer directoryRenderer = new DirectoryRenderer(new File(".").toURI(), mustacheTemplate);

    @Test
    public void testListingFailsWithExceptionOnFileList() throws Exception {
        File file = new File("pom.xml");
        assertTrue(file.isFile());
        try {
            List<String> listing = directoryRenderer.renderDirectory(file);
            fail("A file was supplied instead of directory");
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testListDirectoryWorks() throws Exception {
        File currentWorkingDir = new File(".");
        assertTrue(currentWorkingDir.isDirectory());
        List<String> listing = directoryRenderer.renderDirectory(currentWorkingDir);
        System.out.println(listing);
        boolean pomXmlExists = Iterables.any(listing, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.endsWith("pom.xml");
            }
        });
        assertTrue(pomXmlExists);

        boolean srcFolderExists = Iterables.any(listing, new Predicate<String>() {
            @Override
            public boolean apply(String input) {
                return input.endsWith("src");
            }
        });
        assertTrue(srcFolderExists);

    }
}
