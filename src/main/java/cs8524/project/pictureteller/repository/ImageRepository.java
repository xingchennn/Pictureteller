package cs8524.project.pictureteller.repository;

import cs8524.project.pictureteller.domain.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
