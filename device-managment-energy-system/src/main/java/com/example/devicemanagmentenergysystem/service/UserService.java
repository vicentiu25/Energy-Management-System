package com.example.devicemanagmentenergysystem.service;

import com.example.devicemanagmentenergysystem.converter.UserConverter;
import com.example.devicemanagmentenergysystem.dto.UserDTO;
import com.example.devicemanagmentenergysystem.exception.BusinessException;
import com.example.devicemanagmentenergysystem.model.User;
import com.example.devicemanagmentenergysystem.repository.DeviceRepository;
import com.example.devicemanagmentenergysystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public User saveUser(UserDTO userDTO) {
        User user = UserConverter.convertFromDTOToEntity(userDTO);
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) throws BusinessException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new BusinessException("This user does not exist");
        } else {
            deviceRepository.mapDeleteUser(userId);
            userRepository.deleteById(userId);

        }
    }

    public User findUserById(Long userId) throws BusinessException{
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()){
            throw new BusinessException("This user does not exist");
        } else {
            return user.get();
        }
    }
}
