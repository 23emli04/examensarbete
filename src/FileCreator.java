
import java.io.IOException;
import java.util.List;

public abstract class FileCreator {
    private String filename;

    public FileCreator(String filename) {
        this.filename = filename;
    }

    public abstract void writeToFile(List<Candidate> pool) throws IOException;

    public void setFileName(String filename) {
        this.filename = filename;
    
    }
    public String getFilename() {
        return filename;
    }
    
}
