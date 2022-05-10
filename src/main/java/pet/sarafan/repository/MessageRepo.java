package pet.sarafan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.sarafan.domain.Message;

public interface MessageRepo extends JpaRepository<Message, Long> {
}
