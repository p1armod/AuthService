package org.auth.Service;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.auth.DTO.SignUp.SignUpRequestDTO;
import org.auth.DTO.SignUp.SignUpResponseDTO;
import org.auth.Entity.UserInfo;
import org.auth.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Data
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return modelMapper.map(user, UserDetails.class);
    }

    public SignUpResponseDTO signUp(SignUpRequestDTO signUpRequestDTO) throws Exception {
        if(userRepository.findByUserName(signUpRequestDTO.getUsername()) != null){
            throw new Exception("Username already exists");
        }
        UserInfo user = modelMapper.map(signUpRequestDTO, UserInfo.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
