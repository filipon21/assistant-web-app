package edu.ib.webapp.user.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {
    private String fileName;
    private String fileCode;
    private long size;

    // getters and setters are not shown for brevity

}