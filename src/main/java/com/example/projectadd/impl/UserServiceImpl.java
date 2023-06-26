package com.example.projectadd.impl;

import com.example.projectadd.DTO.NewPasswordDTO;
import com.example.projectadd.DTO.UserDTO;
import com.example.projectadd.exception.UserNotFoundException;
import com.example.projectadd.mapper.UserMapper;
import com.example.projectadd.model.User;
import com.example.projectadd.repository.UserRepository;
import com.example.projectadd.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    @Override
    public void createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("user already exist");
        }
        userRepository.save(user);
    }
    @Override
    public boolean setPassword(NewPasswordDTO newPasswordDto, String userName) {
        User user = checkUserByUsername(userName);
        if (user != null) {
            String password = newPasswordDto.getNewPassword();
            user.setPassword(encoder.encode(password));
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User getUser(String userName) {
        return userRepository.findByEmail(userName);
    }

    @Override
    public UserDTO getUser(Authentication authentication) {
        User user = getUser(authentication.getName());
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO updateUser(UserDTO userDto, String userName) {
        User user = checkUserByUsername(userName);
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhone(userDto.getPhone());
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public User checkUserByUsername(String username) {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UserNotFoundException(toString());
        }
        return user;
    }

    @Override
    public User updateUserImage(MultipartFile image, User currentUser) throws IOException {
        User user = userRepository.findByEmail(currentUser.getUsername());
        // Save the image to the server
        String fileName = image.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf("."));
        String filePath = "path/to/image/directory/" + user.getId() + fileExtension; // Update the file path as per your server setup
        File file = new File(filePath);
        image.transferTo(file);

        // Update the user's image URL

        userRepository.save(user);

        return user;
    }
}

