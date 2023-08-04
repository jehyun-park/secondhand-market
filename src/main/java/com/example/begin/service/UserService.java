package com.example.begin.service;

import com.example.begin.dto.UserDto;
import com.example.begin.entity.User;
import com.example.begin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User loginConfirm(UserDto userDto) {
        Optional<User> userOptional = userRepository.findByUserId(userDto.getUserId());
        if (userOptional.isPresent() && userOptional.get().getUserPw().equals(userDto.getUserPw())) {
            return userOptional.get();
        } else {
            return null;
        }
    }

    public UserDto registerUser(UserDto userDto){
        User user = DtoToEntity(userDto);
        user = userRepository.save(user);
        return entityToDto(user);
    }

    public UserDto entityToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .userPw(user.getUserPw())
                .email(user.getEmail())
                .idx(user.getIdx())
                .nick(user.getNick())
                .build();
    }

    public User DtoToEntity(UserDto dto) {
        return User.builder()
                .userId(dto.getUserId())
                .userPw((dto.getUserPw()))
                .email(dto.getEmail())
                .idx(dto.getIdx())
                .nick(dto.getNick())
                .build();
    }

    public List<UserDto> findAllUser(){
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = new ArrayList<>();
        users.forEach(u ->{
            usersDto.add(entityToDto(u));
        });

        return usersDto;
    }

    public UserDto login(UserDto user){
        Optional<User> loggedinUser = userRepository.findByUserId(user.getUserId());
        if(loggedinUser.isPresent()) {
            User loggedinuserDto = loggedinUser.get();
            log.info("trying login user : {}", entityToDto(loggedinuserDto).toString());
            if(user.getUserId().equals(loggedinuserDto.getUserId()) && user.getUserPw().equals(loggedinuserDto.getUserPw())) {
                return entityToDto(loggedinuserDto);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
