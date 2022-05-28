package pet.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.sarafan.domain.User;
import pet.sarafan.repository.UserDetailsRepo;

import java.util.Arrays;
import java.util.Set;

@Service
public class ProfileService {
    private final UserDetailsRepo userDetailsRepo;

    @Autowired
    public ProfileService(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }


    public User changeSubscription(User channel, User subscriber) {
        Set<User> subscribers = channel.getSubscribers();

        System.out.println(subscriber);
        System.out.println(subscribers);


        if(subscribers.contains(subscriber)){
            subscribers.remove(subscriber);
        }else {
            System.out.println("Add");
            subscribers.add(subscriber);
        }

        return userDetailsRepo.save(channel);
    }
}
