package cs8524.project.pictureteller.service;

import cs8524.project.pictureteller.domain.User;
import cs8524.project.pictureteller.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
//
//        if (!userOptional.isPresent()) {
//            throw new NotFoundException("User not found for ID: " + id.toString());
//        }

        return userOptional.get();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
