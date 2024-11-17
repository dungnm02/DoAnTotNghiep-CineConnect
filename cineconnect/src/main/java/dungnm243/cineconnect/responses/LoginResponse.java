package dungnm243.cineconnect.responses;

import dungnm243.cineconnect.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginResponse {
    private String token;
    private User user;
}
