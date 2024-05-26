package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@Slf4j
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    private final TrainingServiceImpl trainingService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/basic")
    public List<BasicUser> getAllBasicUser() {
        return userService.findAllUsers().stream()
            .map(UserMapper::basicToDTO)
            .toList();
    }

    @GetMapping("/{userId}")
    public Optional<User> getUserByUserId(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/createUser")
    public User createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userMapper.toEntity(userDto));
    }

    @DeleteMapping("/deleteUser/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        trainingService.deleteTrainingByUserId(userId);
        userService.deleteUser(userId);
        log.info("Deleted user: {}", userId);
    }

    @PutMapping("/updateUser/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        return userService.editUser(userId, userDto);
    }

    @GetMapping("/findByEmail/{email}")
    public Optional<User> getUserByMail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/findByFragmentEmail/{email}")
    public Optional<User> getUserByFragmentEmail(@PathVariable String email) {
        return userService.findByFragmentEmail(email);
    }

}