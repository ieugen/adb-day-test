package ro.ieugen.fileserver.files;

import com.google.common.base.Function;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Throwables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Transforms a {@link java.io.File File} into a {@link ro.ieugen.fileserver.files.FileInfo FileInfo} for rendering.
 */
public class FileToFileInfoFunction<T extends File, U extends FileInfo> implements Function<File, FileInfo> {

    private static final Logger LOG = LoggerFactory.getLogger(FileToFileInfoFunction.class);
    private final URI rootUri;

    public FileToFileInfoFunction(URI rootUri) {
        this.rootUri = checkNotNull(rootUri, "Can't relativize file path,base Url is null");
        LOG.info("Using {} as root URI", this.rootUri.getPath());
    }

    private static String FILE_TYPE = "File";
    private static String DIR_TYPE = "Dir";

    @Override
    public FileInfo apply(File input) {
        FileInfo fileInfo = new FileInfo();

        fileInfo.setName(relativePath(input));
        fileInfo.setLastModified(input.lastModified());
        fileInfo.setSizeInBytes(input.length());

        if (input.isFile()) {
            fileInfo.setType(FILE_TYPE);
        } else if (input.isDirectory()) {
            fileInfo.setType(DIR_TYPE);
        }

        return fileInfo;
    }

    /**
     * Compute a relative path to build the relative URL for Directory listing.
     *
     * @param child
     * @return
     */
    public String relativePath(File child) {
        try {
            String rootPath = rootUri.getPath();
            String childPath = child.getCanonicalPath();
            if (childPath.startsWith(rootPath)) {
                return appendTrailingSlashIfMissing(childPath.substring(rootPath.length()), child.isDirectory());
            } else {
                throw new IllegalStateException(String.format("File %s is not a child of %s", childPath, rootPath));
            }
        } catch (IOException e) {
            LOG.debug("Exception getting canonical path for {}", child.getAbsolutePath());
            throw Throwables.propagate(e);
        }
    }

    private String appendTrailingSlashIfMissing(String relativePath, boolean isDirectory) {
        if (isDirectory) {
            return relativePath.endsWith("/") ? relativePath : relativePath + "/";
        }
        return relativePath;
    }

}
