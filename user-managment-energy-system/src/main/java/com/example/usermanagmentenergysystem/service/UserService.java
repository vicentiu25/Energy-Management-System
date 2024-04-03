package com.example.usermanagmentenergysystem.service;

import com.example.usermanagmentenergysystem.authentication.JWTokenCreator;
import com.example.usermanagmentenergysystem.converter.UserConverter;
import com.example.usermanagmentenergysystem.dto.UserDTO;
import com.example.usermanagmentenergysystem.exception.BusinessException;
import com.example.usermanagmentenergysystem.model.User;
import com.example.usermanagmentenergysystem.repository.UserRepository;
import com.example.usermanagmentenergysystem.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final JWTokenCreator jwTokenCreator;

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.stream()
                .map(user -> {
                    user.setPassword(null);
                    return user;
                })
                .collect(Collectors.toList());
        return UserConverter.convertEntitiesToDTOs(users);
    }

    public User saveUser(UserDTO userDTO)
            throws BusinessException, UnsupportedEncodingException {
        User user = UserConverter.convertFromDTOToEntity(userDTO);
        UserValidator.errorList.clear();
        userValidator.validate(user);
        if (UserValidator.errorList.isEmpty()) {
            return userRepository.save(user);
        } else {
            throw new BusinessException(UserValidator.errorList.toString());
        }
    }

    public String matchUser(String username, String password) throws BusinessException {
        User user = userRepository.matchUser(username);

        if(user == null){
            throw new BusinessException("This user does not exist");
        } else if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BusinessException("Incorrect password");
        }
        return jwTokenCreator.generateToken(user);
    }

    public User updateUser(UserDTO userDTO) throws BusinessException{
        Optional<User> user = userRepository.findById(userDTO.getIdUser());

        if(user.isEmpty()){
            throw new BusinessException("This user does not exist");
        } else {
            userDTO.setPassword(user.get().getPassword());
            return userRepository.save(UserConverter.convertFromDTOToEntity(userDTO));
        }
    }

    public void deleteUser(Long userId) throws BusinessException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new BusinessException("This user does not exist");
        } else {
            userRepository.deleteById(userId);
        }
    }

    public User getUserFromUsername(String username) throws BusinessException{
        User user = userRepository.matchUser(username);
        if(user == null){
            throw new BusinessException("This user does not exist");
        }
        return user;
    }

    public Long getAdminId() {
        return userRepository.getAdminId();
    }
}
