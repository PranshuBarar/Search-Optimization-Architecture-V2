package com.searchoptimization.resourceservice.services;


import com.searchoptimization.reposervice.Dto.UserDto;
import com.searchoptimization.reposervice.entities.User;
import com.searchoptimization.reposervice.repo.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private HttpSession session;
//    private SessionRegistry sessionRegistry;

    public String hello(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        return "hello " + user.getUsername();
    }

    public String signup(UserDto userDto) {

        User user = userRepository.findByEmail(userDto.getEmail());

        if(user != null) {
            return "User already exists with this username";
        }

        user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setEmail(userDto.getEmail());

        userRepository.save(user);

        return "User Created Successfully";
    }

    @Transactional
    public String deleteAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        userRepository.deleteByEmail(email);
        SecurityContextHolder.getContext().setAuthentication(null);
        session.invalidate();
        // Remove the session from the session registry
//        List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication.getPrincipal(), false);
//        for (SessionInformation sessionInfo : sessions) {
//            sessionInfo.expireNow();
//        }
        return "User deleted successfully";
    }


}
