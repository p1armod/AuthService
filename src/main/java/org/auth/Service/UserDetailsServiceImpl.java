package org.auth.Service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.auth.DTO.SignUp.SignUpRequestDTO;
import org.auth.DTO.SignUp.SignUpResponseDTO;
import org.auth.Entity.Role;
import org.auth.Entity.Type.RoleType;
import org.auth.Entity.UserInfo;
import org.auth.Repository.RoleRepository;
import org.auth.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(), // must be the hashed password
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                        .collect(Collectors.toList())
        );
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) throws Exception {
        if(userRepository.findByUserName(signUpRequestDTO.getUsername()) != null){
            throw new Exception("Username already exists");
        }
        UserInfo user = modelMapper.map(signUpRequestDTO, UserInfo.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roleEntities = signUpRequestDTO.getRoles()
                .stream()
                .map(roleName -> roleRepository.findByRoleName(RoleType.valueOf(roleName)))
                .collect(Collectors.toSet());

        user.setRoles(roleEntities);
        UserInfo savedUser = userRepository.save(user);
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        if(savedUser == null){
            signUpResponseDTO.setStatus("Failed");
            signUpResponseDTO.setMessage("Error in signing up, Please try again");
        }
        else {
            signUpResponseDTO.setStatus("success");
            signUpResponseDTO.setMessage("User signed up successfully");
        }
        return signUpResponseDTO;
    }


}
