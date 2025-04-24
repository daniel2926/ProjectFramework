package ac.jiu.bobby.project.controller;

import ac.jiu.bobby.project.dto.UserRequestDto;
import ac.jiu.bobby.project.dto.UserResponseDto;
import ac.jiu.bobby.project.model.Role;
import ac.jiu.bobby.project.model.User;
import ac.jiu.bobby.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public Page<UserResponseDto> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userService.getAllUsers(pageable);
        return users.map(this::convertToDTO);
    }

    @GetMapping("/users/{id}")
    public UserResponseDto getUserById(@PathVariable UUID id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
        return convertToDTO(user);
    }

    @PostMapping("/users")
    public UserResponseDto createUser(@RequestBody UserRequestDto dto) {
        User user = convertToEntity(dto);
        return convertToDTO(userService.createUser(user));
    }

    @PutMapping("/users/{id}")
    public UserResponseDto updateUser(@PathVariable UUID id, @RequestBody UserRequestDto dto) {
        User user = convertToEntity(dto);
        return convertToDTO(userService.updateUser(id, user));
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.softDeleteUser(id);
    }

    // ============================
    // DTO Mapper Helpers
    // ============================

    private UserResponseDto convertToDTO(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setDob(user.getDob());
        dto.setGender(user.getGender());
        dto.setAddress(user.getAddress());
        dto.setPhoto(user.getPhoto());
        dto.setRoleName(user.getRole() != null ? user.getRole().getRoleName() : null);
        return dto;
    }

    private User convertToEntity(UserRequestDto dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDob(dto.getDob());
        user.setGender(dto.getGender());
        user.setAddress(dto.getAddress());
        user.setPhoto(dto.getPhoto());

        // Set role by ID only (will be fetched in service)
        if (dto.getRoleId() != null) {
            var role = new Role();
            role.setId(dto.getRoleId());
            user.setRole(role);
        }

        return user;
    }
}
