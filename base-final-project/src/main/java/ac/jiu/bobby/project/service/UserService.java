package ac.jiu.bobby.project.service;

import ac.jiu.bobby.project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    Page<User> getAllUsers(Pageable pageable);
    Optional<User> getUserById(UUID id);
    List<User> getUsersByRoleId(UUID roleId);
    User createUser(User user);
    User updateUser(UUID id, User userDetails);
    void softDeleteUser(UUID id);


}
