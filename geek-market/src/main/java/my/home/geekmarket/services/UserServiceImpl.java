package my.home.geekmarket.services;

import my.home.geekmarket.entites.SystemUser;
import my.home.geekmarket.entites.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Override
    public User findByUserName(String username) {
        return null;
    }

    @Override
    public boolean save(SystemUser systemUser) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
