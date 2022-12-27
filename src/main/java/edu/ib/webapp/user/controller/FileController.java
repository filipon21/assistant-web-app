package edu.ib.webapp.user.controller;

import edu.ib.webapp.user.model.request.PrescriptionRequest;
import edu.ib.webapp.user.model.response.FileUploadResponse;
import edu.ib.webapp.user.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Klasa do obsługi e-Recept i zapisu pliku (kontroler Springowy)
 */
@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * Metoda służąca do odebrania zapytania na serwer dot. zapisu e-Recepty
     * @param multipartFile - plik przesłany na serwer
     * @param visitId - id wizyty (bazodanowe)
     * @param prescriptionRequest - dane dot. e-recepty
     * @return zwraca dane zapisane w bazie danych dot. e-Recepty
     * @throws IOException - wyjątek występujący przy zapisie pliku
     */
    @PostMapping("/upload/{visitId}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'ASSISTANT')")
    public FileUploadResponse uploadFile(
            @RequestPart("file") MultipartFile multipartFile,
            @PathVariable Long visitId,
            @RequestPart("prescriptionRequest") PrescriptionRequest prescriptionRequest)
            throws IOException {

        return fileService.uploadFile(multipartFile, visitId, prescriptionRequest);
    }

    /**
     * Metoda służąca do odebrania zapytania na serwer dot. pobrania pliku e-Recepty
     * @param fileCode - kod e-Recepty
     * @return zapisany plik
     */
    @GetMapping("/download/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
        return fileService.downloadFile(fileCode);
    }
}
