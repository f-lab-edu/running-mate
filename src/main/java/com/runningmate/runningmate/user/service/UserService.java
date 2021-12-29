package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.common.utils.BCryptUtil;
import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.image.service.ImageUploadService;
import com.runningmate.runningmate.position.domain.entity.Position;
import com.runningmate.runningmate.skill.domain.entity.Skill;
import com.runningmate.runningmate.user.dto.Request.UserSkillAddReqeustDto;
import com.runningmate.runningmate.user.dto.Request.UserSkillSaveReqeustDto;
import com.runningmate.runningmate.user.dto.Request.UserSignUpRequestDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.entity.UserSkill;
import com.runningmate.runningmate.user.repository.UserRepository;
import com.runningmate.runningmate.user.repository.UserSkillRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository mybatisUserRepository;
    private final UserSkillRepository mybatisUserSkillRepository;

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
        mybatisUserRepository.save(insertUser);

        List<UserSkill> userSkills = userSignUpRequestDto.getUserSkills().stream()
            .map(userSkill -> UserSkill.builder()
                .user(insertUser)
                .skill(Skill.builder().skillId(userSkill.getSkillId()).build())
                .createDate(LocalDateTime.now())
                .build())
            .collect(Collectors.toList());

        insertUserSkills(userSkills);
    }

    public Optional<User> getUser(long userId) {
        return mybatisUserRepository.findByUserId(userId);
    }


    /**
     *  유저 스킬 변경
     *
     * @param userId
     * @param userSkillId
     * @param updateUserSkillReqeustDto
     */
    public void updateUserSkill(long userId, long userSkillId, UserSkillSaveReqeustDto updateUserSkillReqeustDto) {
        Optional<User> user =  mybatisUserRepository.findByUserId(userId);
        UserSkill updateUserSkill = UserSkill.builder()
            .userSkillId(userSkillId)
            .user(user.get())
            .skill(Skill.builder().skillId(updateUserSkillReqeustDto.getSkillId()).build())
            .updateDate(LocalDateTime.now())
            .build();
        mybatisUserSkillRepository.save(updateUserSkill);
    }

    /**
     * 유저 스킬 삭제
     * 
     * @param deleteUserSkillId 
     */
    public void deleteUserSkill(long deleteUserSkillId) {
        mybatisUserSkillRepository.delete(deleteUserSkillId);
    }

    public void insertUserSkills(List<UserSkill> userSkills){
        mybatisUserSkillRepository.saveAll(userSkills);
    }

    public void insertUserSkills(long userId, UserSkillAddReqeustDto userSkillAddReqeustDto) {
        Optional<User> user =  mybatisUserRepository.findByUserId(userId);

        List<UserSkill> userSkills = userSkillAddReqeustDto.getUserSkills().stream()
            .map(userSkill -> UserSkill.builder()
                .user(user.get())
                .skill(Skill.builder().skillId(userSkill.getSkillId()).build())
                .createDate(LocalDateTime.now())
                .build())
            .collect(Collectors.toList());
        insertUserSkills(userSkills);
    }
}
