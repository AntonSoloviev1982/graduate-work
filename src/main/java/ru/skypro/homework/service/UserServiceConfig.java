//package ru.skypro.homework.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Scope;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import ru.skypro.homework.entity.User;
//import ru.skypro.homework.exception.UserNotFoundException;
//import ru.skypro.homework.repository.UserRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@Scope("request")
//@RequiredArgsConstructor
//public class UserServiceConfig implements UserDetailsService {
//
//    private final UserRepository userRepository;
//
//
//    public Optional<User> findByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    @Override
//    @Transactional
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
//        return new org.springframework.security.core.userdetails.User(
//                user.getUsername(),
//                user.getPassword(),
//                List.of(new SimpleGrantedAuthority(user.getRole().name()))
//        );
//    }
//}