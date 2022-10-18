package edu.ib.webapp.user.service;

import edu.ib.webapp.user.model.request.PrescriptionRequest;
import edu.ib.webapp.user.model.response.FileUploadResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    FileUploadResponse uploadFile(MultipartFile multipartFile, Long visitId, PrescriptionRequest prescriptionRequest) throws IOException;

    ResponseEntity<?> downloadFile(String fileCode);
}
