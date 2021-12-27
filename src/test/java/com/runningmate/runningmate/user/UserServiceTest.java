package com.runningmate.runningmate.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.runningmate.runningmate.common.utils.BCryptUtil;
import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.image.domain.entity.ImageStatus;
import com.runningmate.runningmate.image.service.ImageUploadService;
import com.runningmate.runningmate.user.aop.LoginCheck.UserLevel;
import com.runningmate.runningmate.user.dto.Request.UserSignUpRequestDto;
import com.runningmate.runningmate.user.dto.Request.UserSkillSaveReqeustDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.repository.UserRepository;
import com.runningmate.runningmate.user.repository.UserSkillRepository;
import com.runningmate.runningmate.user.service.UserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    ImageUploadService awsS3ImageUploadService;

    @Mock
    UserRepository mybatisUserRepository;

    @Mock
    UserSkillRepository mybatisUserSkillRepository;

    @InjectMocks
    UserService userService;


    @Test
    @DisplayName("회원가입 성공")
    void testSuccessUserSignUp() {
        List<UserSkillSaveReqeustDto> userSkills = new ArrayList<>();
        userSkills.add( new UserSkillSaveReqeustDto(1) );
        userSkills.add( new UserSkillSaveReqeustDto(2) );

        UserSignUpRequestDto userSignUpRequestDto = UserSignUpRequestDto.builder()
            .email("testtest@test.test")
            .password(BCryptUtil.setEncrypt("test1234!@#$"))
            .level(UserLevel.MANAGER)
            .nickName("testNick")
            .positionId(5)
            .userSkills(userSkills)
            .build();
        MockMultipartFile multipartFile = new MockMultipartFile("fileTest", "originalFineName", "image/png", "byteFile".getBytes());

        when(awsS3ImageUploadService.upload(multipartFile)).thenReturn(new Image(1L, ImageStatus.BEING_USED, "originalFineName", "storeFileName", LocalDateTime.now(), LocalDateTime.now()));

        userService.userRegister(userSignUpRequestDto, multipartFile);

        verify(awsS3ImageUploadService, times(1)).upload(any(MultipartFile.class));
        verify(mybatisUserRepository, times(1)).save(any(User.class));
        verify(mybatisUserSkillRepository, times(1)).saveAll(anyList());
    }
}
