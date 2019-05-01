package cs8524.project.pictureteller.repository;

import cs8524.project.pictureteller.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
