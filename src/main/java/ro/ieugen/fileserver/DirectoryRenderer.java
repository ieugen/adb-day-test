package ro.ieugen.fileserver;

import static com.google.common.base.Preconditions.checkNotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Render a directory listing as HTML.
 */
public class DirectoryRenderer {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryRenderer.class);

    public static List<String> renderDirectory(File directory) {
        checkNotNull(directory, "Null value for directory");
        List<String> fileList;
        if (directory.isDirectory()) {
            fileList = getCanonicalPathForFiles(directory.listFiles());
        } else {
            throw new IllegalStateException("Not a directory.");
        }
        return fileList;
    }

    static List<String> getCanonicalPathForFiles(File[] files) {
        List<String> fileList = new ArrayList<String>();
        for (File f : files) {
            try {
                fileList.add(f.getCanonicalPath());
            } catch (IOException e) {
                LOG.debug("Exception getting canonical path for {}", f.getPath());
            }
        }
        return fileList;
    }

}
