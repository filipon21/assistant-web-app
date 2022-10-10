package edu.ib.webapp.common.model.response;

import edu.ib.webapp.user.model.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private UserResponse user;

    private String jwtToken;

}
