package pet.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.sarafan.domain.User;
import pet.sarafan.domain.UserSubscription;
import pet.sarafan.repository.UserDetailsRepo;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public ProfileService(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }


    public User changeSubscription(User channel, User subscriber) {
        List<UserSubscription> subscribtions = channel.getSubscribers()
                .stream()
                .filter(subscription -> subscription.getSubscriber().equals(subscriber))
                .collect(Collectors.toList());

        if(subscribtions.isEmpty()){
            UserSubscription subscription = new UserSubscription(channel, subscriber);
            channel.getSubscribers().add(subscription);
        }else {
            channel.getSubscribers().removeAll(subscribtions);
        }

        return userDetailsRepo.save(channel);
    }
}
