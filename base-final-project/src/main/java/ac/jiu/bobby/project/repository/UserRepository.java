package ac.jiu.bobby.project.repository;

import ac.jiu.bobby.project.model.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.isDeleted = false")
    Page<User> findAllByIsDeletedFalse(Pageable pageable);

    List<User> findByRole_Id(UUID roleId);

}


