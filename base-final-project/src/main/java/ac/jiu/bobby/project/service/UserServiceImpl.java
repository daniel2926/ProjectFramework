package ac.jiu.bobby.project.service;

import ac.jiu.bobby.project.exception.ResourceNotFoundException;
import ac.jiu.bobby.project.model.Role;
import ac.jiu.bobby.project.model.User;
import ac.jiu.bobby.project.repository.RoleRepository;
import ac.jiu.bobby.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAllByIsDeletedFalse(pageable);
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id)
                .filter(user -> !user.isDeleted());
    }

    public User createUser(User user) {
        UUID roleId = user.getRole().getId();
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));
        user.setRole(role);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UUID id, User userDetails) {
        return userRepository.findById(id)
                .filter(user -> !user.isDeleted())
                .map(user -> {
                    user.setFirstName(userDetails.getFirstName());
                    user.setLastName(userDetails.getLastName());
                    user.setDob(userDetails.getDob());
                    user.setGender(userDetails.getGender());
                    user.setAddress(userDetails.getAddress());
                    user.setPhoto(userDetails.getPhoto());
                    user.setUpdatedBy(userDetails.getUpdatedBy());

                    UUID roleId = userDetails.getRole().getId();
                    Role role = roleRepository.findById(roleId)
                            .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));
                    user.setRole(role);

                    return userRepository.save(user);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

    }

    @Override
    public void softDeleteUser(UUID id) {
        userRepository.findById(id)
                .filter(user -> !user.isDeleted())
                .ifPresent(user -> {
                    user.setDeleted(true);
                    userRepository.save(user);
                });
    }

    @Override
    public List<User> getUsersByRoleId(UUID roleId) {
        return userRepository.findByRole_Id(roleId)
                .stream()
                .filter(user -> !user.isDeleted())
                .toList();
    }
}
