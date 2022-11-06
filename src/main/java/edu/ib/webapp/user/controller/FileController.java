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

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload/{visitId}")
    public FileUploadResponse uploadFile(
            @RequestPart("file") MultipartFile multipartFile,
            @PathVariable Long visitId,
            @RequestPart("prescriptionRequest") PrescriptionRequest prescriptionRequest)
            throws IOException {

        return fileService.uploadFile(multipartFile, visitId, prescriptionRequest);
    }

    @GetMapping("/download/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode) {
        return fileService.downloadFile(fileCode);
    }
}
