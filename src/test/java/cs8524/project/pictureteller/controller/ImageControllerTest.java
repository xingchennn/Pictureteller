package cs8524.project.pictureteller.controller;

import cs8524.project.pictureteller.domain.Image;
import cs8524.project.pictureteller.domain.User;
import cs8524.project.pictureteller.service.ImageService;
import cs8524.project.pictureteller.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ImageControllerTest {

    public static final String LAST_NAME = "abc";
    @Mock
    ImageService imageService;

    @Mock
    UserService userService;

    @InjectMocks
    ImageController imageController;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();

    }

    @Test
    public void showUploadForm() throws Exception {
        // given
        User user = User.builder().lastName(LAST_NAME).id(1L).build();

        when(userService.findById(anyLong())).thenReturn(user);

        mockMvc.perform(get("/user/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("user/imageuploadform"));

        verify(userService, times(1)).findById(anyLong());
    }

    @Test
    public void handleImagePost() throws Exception {
        MockMultipartFile multipartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "test".getBytes());

        mockMvc.perform(multipart("/user/1/image").file(multipartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/user/1/show"));

        verify(imageService, times(1)).saveImageFile(anyLong(), any());
    }

    @Test
    public void renderImageFromDB() throws Exception {
        // given
        User user = User.builder().lastName(LAST_NAME).id(1L).build();
        Image image = Image.builder().user(user).id(1L).build();

        String fakeImage = "I am a fake image";

        Byte[] bytes = new Byte[fakeImage.getBytes().length];

        int i = 0;
        for (Byte b : fakeImage.getBytes()) {
            bytes[i++] = b;
        }

        image.setImage(bytes);
        Set<Image> images = new HashSet<>();
        images.add(image);
        user.setImages(images);

        when(imageService.findImageByIdAndImageId(anyLong(), anyLong())).thenReturn(image);

        // when
        MockHttpServletResponse response = mockMvc.perform(get("/user/1/image/1"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        byte[] responseBytes = response.getContentAsByteArray();

        assertEquals(fakeImage.getBytes().length, responseBytes.length);
    }
}