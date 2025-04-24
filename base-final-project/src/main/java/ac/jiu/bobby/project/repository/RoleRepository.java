package ac.jiu.bobby.project.repository;

import ac.jiu.bobby.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
}
