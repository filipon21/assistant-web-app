package edu.ib.webapp.common.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    @NotBlank
    private String userName;

    @NotBlank
    private String userPassword;

}
