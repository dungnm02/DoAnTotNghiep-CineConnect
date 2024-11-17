package dungnm243.cineconnect.services;

import dungnm243.cineconnect.models.User;

import java.util.List;

public interface UserService {
    User checkLogin(String username, String password);
    List<String> register(User user);
}
