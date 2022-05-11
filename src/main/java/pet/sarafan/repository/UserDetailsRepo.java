package pet.sarafan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.sarafan.domain.User;

public interface UserDetailsRepo extends JpaRepository<User, String> {
}
