package pet.sarafan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.sarafan.domain.User;
import pet.sarafan.domain.UserSubscription;
import pet.sarafan.domain.UserSubscriptionId;

import java.util.List;

public interface UserSubscriptionRepo extends JpaRepository<UserSubscription, UserSubscriptionId> {
    List<UserSubscription> findBySubscriber(User user);
}
