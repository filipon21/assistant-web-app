package edu.ib.webapp.user.service.implementation;

import edu.ib.webapp.common.exception.ExceptionMessage;
import edu.ib.webapp.common.exception.UserException;
import edu.ib.webapp.user.entity.Prescription;
import edu.ib.webapp.user.entity.Visit;
import edu.ib.webapp.user.model.request.PrescriptionRequest;
import edu.ib.webapp.user.model.response.FileUploadResponse;
import edu.ib.webapp.user.repository.PrescriptionRepository;
import edu.ib.webapp.user.repository.VisitRepository;
import edu.ib.webapp.user.service.FileService;
import edu.ib.webapp.common.util.file.FileDownloadUtil;
import edu.ib.webapp.common.util.file.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final PrescriptionRepository prescriptionRepository;

    private final VisitRepository visitRepository;

    @Override
    @Transactional
    public FileUploadResponse uploadFile(MultipartFile multipartFile, Long visitId, PrescriptionRequest prescriptionRequest) throws IOException {
        Visit visit = visitRepository.findById(visitId).orElse(null);

        if (visit == null){
            throw new UserException(HttpStatus.NOT_FOUND, ExceptionMessage.VISIT_NOT_FOUND);
        }

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        long size = multipartFile.getSize();

        String filecode = FileUploadUtil.saveFile(fileName, multipartFile);

        FileUploadResponse response = new FileUploadResponse();
        response.setFileName(fileName);
        response.setSize(size);
        response.setFileCode(filecode);

        Prescription prescription = new Prescription();
        prescription.setFileCode(filecode);
        prescription.setCode(prescriptionRequest.getCode());
        prescription.setVisit(visit);
        prescriptionRepository.save(prescription);

        return response;
    }

    @Override
    public ResponseEntity<?> downloadFile(String fileCode) {
        FileDownloadUtil downloadUtil = new FileDownloadUtil();

        Resource resource;
        try {
            resource = downloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (resource == null) {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }
}
