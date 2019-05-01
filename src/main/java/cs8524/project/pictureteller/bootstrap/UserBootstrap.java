package cs8524.project.pictureteller.bootstrap;

import cs8524.project.pictureteller.domain.User;
import cs8524.project.pictureteller.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class UserBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    public UserBootstrap(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        userRepository.saveAll(getUsers());
        log.info("Loading Bootstrap Data");
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();

        User user1 = User.builder().firstName("Kevin").lastName("Sullivan").build();

        User user2 = User.builder().firstName("Yaodong").lastName("Yu").build();

        users.add(user1);
        users.add(user2);

        log.info("add two user");
        return users;
    }
}
