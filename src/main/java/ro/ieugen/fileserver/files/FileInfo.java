package ro.ieugen.fileserver.files;

import com.google.common.base.Objects;

/**
 * Store information about files and directories needed at rendering time.
 */
public class FileInfo {

    private String name;
    private Long lastModified;
    private Long sizeInBytes;
    private String type;

    public Long getLastModified() {
        return lastModified;
    }

    public void setLastModified(long lastModified) {
        this.lastModified = lastModified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSizeInBytes() {
        return sizeInBytes;
    }

    public void setSizeInBytes(long sizeInBytes) {
        this.sizeInBytes = sizeInBytes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, lastModified, sizeInBytes, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final FileInfo other = (FileInfo) obj;
        return Objects.equal(this.name, other.name) && Objects.equal(this.lastModified, other.lastModified) && Objects.equal(this.sizeInBytes, other.sizeInBytes) && Objects.equal(this.type, other.type);
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "lastModified=" + lastModified +
                ", name='" + name + '\'' +
                ", sizeInBytes=" + sizeInBytes +
                ", type='" + type + '\'' +
                '}';
    }
}
