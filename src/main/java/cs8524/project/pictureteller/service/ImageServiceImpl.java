package cs8524.project.pictureteller.service;

import cs8524.project.pictureteller.domain.Image;
import cs8524.project.pictureteller.domain.User;
import cs8524.project.pictureteller.repository.ImageRepository;
import cs8524.project.pictureteller.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@PropertySource({"classpath:resources.properties"})
public class ImageServiceImpl implements ImageService {

    @Value("${imageService.file.path}")
    private String filePath;

    @Value("${imageService.python.path}")
    private String pythonPath;

    private String[] arguments = {"python", "", "--image", ""};


    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(UserRepository userRepository, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    /**
     * Persistent image to database, save image to a folder and send the name to python
     * @param userId
     * @param file
     */
    @Override
    @Transactional
    public void saveImageFile(Long userId, MultipartFile file) {
        try {
            User user = userRepository.findById(userId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            String line = null;
            String currentRelativePath = new java.io.File( "." ).getCanonicalPath();


            String fileName = file.getOriginalFilename();
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            File dest = new File( currentRelativePath + filePath + fileName);
            file.transferTo(dest);

            arguments[1] = currentRelativePath + pythonPath;
            arguments[3] = currentRelativePath + filePath + fileName;

            try {
                Process process = Runtime.getRuntime().exec(arguments);
                BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));

                line = in.readLine();
                in.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            Image image = Image.builder().image(byteObjects).user(user).sentence(line).build();
            imageRepository.save(image);


            user.getImages().add(image);
            userRepository.save(user);

        } catch (IOException e) {
            //todo handle better
            log.error("Error occurred", e);

            e.printStackTrace();
        }
    }

    /**
     * Find image by userId and imageId
     * @param userId
     * @param imageId
     * @return Image image
     */
    @Override
    public Image findImageByIdAndImageId(Long userId, Long imageId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found. Id: " + userId);
        }

        User user = userOptional.get();

        Optional<Image> imageOptional = user.getImages().stream()
                .filter(imageFound -> imageFound.getId().equals(imageId)).findFirst();

        if(!imageOptional.isPresent()){
            //todo impl error handling
            log.error("image id not found: " + imageId);
        }

        return imageOptional.get();
    }
}
