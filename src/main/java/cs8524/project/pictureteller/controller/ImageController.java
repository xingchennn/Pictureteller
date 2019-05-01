package cs8524.project.pictureteller.controller;

import cs8524.project.pictureteller.domain.Image;
import cs8524.project.pictureteller.service.ImageService;
import cs8524.project.pictureteller.service.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class ImageController {
    private final ImageService imageService;
    private final UserService userService;

    public ImageController(ImageService imageService, UserService userService) {
        this.imageService = imageService;
        this.userService = userService;
    }

    @GetMapping("user/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("user", userService.findById(Long.valueOf(id)));

        return "user/imageuploadform";
    }

    @PostMapping("user/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){

        imageService.saveImageFile(Long.valueOf(id), file);

        return "redirect:/user/" + id + "/show";
    }

    @GetMapping("user/{id}/image/{imageId}")
    public void renderImageFromDB(@PathVariable String id, @PathVariable String imageId, HttpServletResponse response) throws IOException {
        //User user = userRepository.findById(Long.valueOf(id)).get();

        Image image = imageService.findImageByIdAndImageId(Long.valueOf(id), Long.valueOf(imageId));

        if (image != null) {
            byte[] byteArray = new byte[image.getImage().length];
            int i = 0;

            for (Byte wrappedByte : image.getImage()){
                byteArray[i++] = wrappedByte; //auto unboxing
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }

}
