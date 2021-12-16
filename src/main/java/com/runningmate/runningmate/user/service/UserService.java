package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.common.utils.BCryptUtil;
import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.image.service.ImageUploadService;
import com.runningmate.runningmate.position.domain.entity.Position;
import com.runningmate.runningmate.user.dto.UserSignUpRequestDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.entity.UserSkill;
import com.runningmate.runningmate.user.repository.UserRepository;
import com.runningmate.runningmate.user.repository.UserSkillRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;

    private final ImageUploadService awsS3ImageUploadService;


    /**
     * 회원가입
     *
     * @param userSignUpRequestDto
     * @param multipartFile
     *
     * @author junsoo
     */
    @Transactional
    public void userRegister(UserSignUpRequestDto userSignUpRequestDto, MultipartFile multipartFile) {
        Image image = awsS3ImageUploadService.upload(multipartFile);

        User insertUser = User.builder()
            .email(userSignUpRequestDto.getEmail())
            .password(BCryptUtil.setEncrypt(userSignUpRequestDto.getPassword()))
            .nickName(userSignUpRequestDto.getNickName())
            .position(Position.builder().positionId(userSignUpRequestDto.getPositionId()).build())
            .image(image)
            .createDate(LocalDateTime.now())
            .level(userSignUpRequestDto.getLevel())
            .build();
        userRepository.save(insertUser);

        List<UserSkill> userSkills = userSignUpRequestDto.getUserSkills().stream()
            .map(userSkill -> UserSkill.builder()
                .userId(insertUser.getUserId())
                .skillId(userSkill.getSkillId())
                .createDate(LocalDateTime.now())
                .build())
            .collect(Collectors.toList());

        userSkillRepository.saveAll(userSkills);
    }
}
