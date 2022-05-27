package pet.sarafan.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import pet.sarafan.domain.User;

import java.util.Optional;

public interface UserDetailsRepo extends JpaRepository<User, String> {
    @EntityGraph(
            attributePaths = {"subscriptions", "subscribers"}
    )
    Optional<User> findAllById(String s);
}
