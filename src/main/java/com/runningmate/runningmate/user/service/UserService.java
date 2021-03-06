package com.runningmate.runningmate.user.service;

import com.runningmate.runningmate.common.annotation.SessionLoginUser;
import com.runningmate.runningmate.common.exception.AuthenticationPasswordException;
import com.runningmate.runningmate.common.exception.DuplicateUserException;
import com.runningmate.runningmate.common.exception.NotFoundUserException;
import com.runningmate.runningmate.common.utils.BCryptUtil;
import com.runningmate.runningmate.common.utils.UUIDUtils;
import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.image.service.ImageUploadService;
import com.runningmate.runningmate.position.domain.entity.Position;
import com.runningmate.runningmate.user.dto.Request.UserUpdatePasswordRequestDto;
import com.runningmate.runningmate.skill.domain.entity.Skill;
import com.runningmate.runningmate.user.dto.Request.UserSkillAddReqeustDto;
import com.runningmate.runningmate.user.dto.Request.UserSkillSaveReqeustDto;
import com.runningmate.runningmate.user.dto.Request.UserSignUpRequestDto;
import com.runningmate.runningmate.user.dto.Request.UserUpdateRequestDto;
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
        Optional<User> user = mybatisUserRepository.findByEmail(userSignUpRequestDto.getEmail());
        if(user.isPresent()) throw new DuplicateUserException("이미 등록된 이메일이 있습니다.");

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

    /**
     * 유저 상세정보
     *
     * @param userId
     * @return user
     */
    public User getUserById(long userId) {
        return mybatisUserRepository.findByUserId(userId).orElseThrow(() -> new NotFoundUserException("유저 정보를 찾을 수 없습니다."));
    }

    /**
     * 유저 상세정보
     * @param userEmail
     * @return user
     */
    public User getUserByEmail(String userEmail) {
        return mybatisUserRepository.findByEmail(userEmail).orElseThrow(() -> new NotFoundUserException("유저 정보를 찾을 수 없습니다."));
    }
    
    /**
     * 유저 정보 업데이트
     * nickname, position, level 정보만 업데이트
     *
     * @param user
     * @param userUpdateRequestDto
     */
    public void updateUser(@SessionLoginUser User user, UserUpdateRequestDto userUpdateRequestDto) {
        userPasswordValid(user, userUpdateRequestDto.getPassword());
        user.update(userUpdateRequestDto);
        mybatisUserRepository.update(user);
    }

    /**
     * 유저 삭제
     * DB 삭제를 하는 것이 아닌 user status 상태만 변경
     *
     * @param user
     * @param password
     */
    public void deleteUser(@SessionLoginUser User user, String password) {
        userPasswordValid(user, password);
        user.delete();
        mybatisUserRepository.update(user);
    }


    public void updateUserPassword(UserUpdatePasswordRequestDto userUpdatePasswordRequestDto) {
        User updateUser = mybatisUserRepository.getUserByResetToken(userUpdatePasswordRequestDto.getResetToken()).orElseThrow(() -> new NotFoundUserException("유저 정보를 찾을 수 없습니다."));
        updateUser.updatePassword(userUpdatePasswordRequestDto.getPassword());
        mybatisUserRepository.update(updateUser);
    }

    /**
     * 유저 이미지 변경
     * 기존 이미지는 삭제
     *
     * @param loginUser
     * @param multipartFile
     */
    @Transactional
    public void updateUserImage(@SessionLoginUser User loginUser, MultipartFile multipartFile) {
        User updateUser = getUserById(loginUser.getUserId());

        awsS3ImageUploadService.delete(updateUser.getImage().getImageId());
        Image image = awsS3ImageUploadService.upload(multipartFile);
        updateUser.updateImage(image.getImageId());
        mybatisUserRepository.update(updateUser);
    }

    /**
     * 유저 정보 찾기
     * resetToken 생성
     *
     * @param userEmail
     */
    public void findUserPassword(String userEmail){
        User user = getUserByEmail(userEmail);

        // 중복을 막기위한 userId 추가
        user.findUserPassword(UUIDUtils.getUUID(String.valueOf(user.getUserId())));
        mybatisUserRepository.update(user);
    }

    /**
     * 유저 비밀번호 체크
     *
     * @param user
     * @param password
     */
    public void userPasswordValid(User user, String password) {
        if(!BCryptUtil.comparePassword(password, user.getPassword())) throw new AuthenticationPasswordException("비밀번호가 맞지 않습니다.");
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
