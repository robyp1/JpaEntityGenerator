package it.gen.io;

import it.gen.formats.SourceTypeEnum;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileSourceLoader {

    private final SourceTypeEnum typeOfSource;
    private final Path filePath;

    public FileSourceLoader(SourceTypeEnum typeOfSource, Path filePath) {
        this.typeOfSource = typeOfSource;
        this.filePath = filePath;
    }

    public Map<SourceTypeEnum, List<String>> loadData() {
        LinkedList<String> data = new LinkedList<>();
        if (filePath.toFile().isFile()) {
            try {
                data.addAll(Files.readAllLines(filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<SourceTypeEnum, List<String>>() {{
            put(typeOfSource, data);
        }};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileSourceLoader that = (FileSourceLoader) o;
        return typeOfSource == that.typeOfSource &&
                Objects.equals(filePath, that.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeOfSource, filePath);
    }

    @Override
    public String toString() {
        return "FileSourceLoader{" +
                "typeOfSource=" + typeOfSource +
                ", filePath=" + filePath +
                '}';
    }
}
