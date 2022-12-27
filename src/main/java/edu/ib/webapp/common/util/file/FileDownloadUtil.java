package edu.ib.webapp.common.util.file;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Klasa do obsługi pobierania pliku
 */
public class FileDownloadUtil {
    private Path foundFile;

    /**
     * Metoda służąca do znajdowania pliku i zwracania go jako typ Resource
     * @param fileCode - kod pliku (String)
     * @return null w przypadku nie znalezienia pliku lub plik jako Resource
     * @throws IOException błąd input/output przy wczytywania pliku z pamięci komputera
     */
    public Resource getFileAsResource(String fileCode) throws IOException {
        Path dirPath = Paths.get("Files-Upload");

        Files.list(dirPath).forEach(file -> {
            if (file.getFileName().toString().startsWith(fileCode)) {
                foundFile = file;
                return;
            }
        });

        if (foundFile != null) {
            return new UrlResource(foundFile.toUri());
        }

        return null;
    }
}
