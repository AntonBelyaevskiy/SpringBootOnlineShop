package my.home.geekmarket.services;

import my.home.geekmarket.entites.SystemUser;
import my.home.geekmarket.entites.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    User findByUserName(String username);
    boolean save(SystemUser systemUser);
}
