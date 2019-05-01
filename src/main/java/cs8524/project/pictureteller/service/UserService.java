package cs8524.project.pictureteller.service;

import cs8524.project.pictureteller.domain.User;

public interface UserService {

    User findById(Long id);

    User saveUser(User user);
}
