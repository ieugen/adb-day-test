package ro.ieugen.fileserver;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Throwables;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.ieugen.fileserver.files.FileInfo;
import ro.ieugen.fileserver.files.FileToFileInfoFunction;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * Render a directory listing as HTML.
 */
public class DirectoryRenderer {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryRenderer.class);
    private final MustacheTemplate mustacheTemplate;
    private final FileToFileInfoFunction function;
    private final URI rootUri;

    public DirectoryRenderer(URI rootUri, MustacheTemplate mustacheTemplate) {
        checkNotNull(rootUri, " Root URI is null");
        this.rootUri = rootUri.normalize();
        this.function = new FileToFileInfoFunction(rootUri);
        this.mustacheTemplate = checkNotNull(mustacheTemplate, "MustacheTemplate instance is null");
    }

    public String renderDirectory(File directory) {
        checkNotNull(directory, "Null value for directory");
        if (!directory.isDirectory()) {
            throw new IllegalStateException("Not a directory.");
        }
        try {
            File newPath = directory.getCanonicalFile();
            LOG.info("Listing files for {}", newPath);
            List<FileInfo> fileInfos = Lists.newArrayList(Iterables.transform(Arrays.asList(newPath.listFiles()), function));
            return mustacheTemplate.render(new ListingContainer(fileInfos));
        } catch (IOException e) {
            LOG.info("Exception rendering directory {}", directory.getAbsolutePath());
            throw Throwables.propagate(e);
        }
    }

    static class ListingContainer {
        private List<FileInfo> files;

        ListingContainer(List<FileInfo> files) {
            this.files = files;
        }

        public List<FileInfo> getFiles() {
            return files;
        }
    }
}
