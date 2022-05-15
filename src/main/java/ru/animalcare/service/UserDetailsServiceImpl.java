package ru.animalcare.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.animalcare.domain.User;
import ru.animalcare.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Optional;
import java.util.stream.Collectors;

@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepo;

    private PasswordEncoder passwordEncoder;
    public UserDetailsServiceImpl(UserRepository userRepo,PasswordEncoder passwordEncoder) {

        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = userRepo.findByUsername(username);
        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        User appUser = optUser.get();
        return org.springframework.security.core.userdetails.User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .disabled(!appUser.isEnabled())
                .authorities(appUser.getAuthorities().stream()
                        .map(authority -> new SimpleGrantedAuthority("ROLE_" + authority.getAuthority())).collect(Collectors.toList()))
                .build();
    }

    public void save(User user) {
         user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setPassword(user.getPassword());
        userRepo.save(user);
    }

    public boolean checkByEmail(String email) {
        return userRepo.findByEmail(email).isPresent();
    }
}