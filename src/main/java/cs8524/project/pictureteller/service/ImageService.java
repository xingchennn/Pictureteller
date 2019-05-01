package cs8524.project.pictureteller.service;

import cs8524.project.pictureteller.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Long userId, MultipartFile file);

    Image findImageByIdAndImageId(Long userId, Long imageId);
}
