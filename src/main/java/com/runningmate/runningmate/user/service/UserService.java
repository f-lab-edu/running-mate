package com.runningmate.runningmate.user.service;

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

        mybatisUserSkillRepository.saveAll(userSkills);
    }

    /**
     * 유저 상세정보
     *
     * @param userId
     * @return
     */
    public Optional<User> getUserById(long userId) {
        return mybatisUserRepository.findByUserId(userId);
    }

    /**
     * 유저 상세정보
     * @param userEmail 
     * @return
     */
    public Optional<User> getUserByEmail(String userEmail) {
        return mybatisUserRepository.findByEmail(userEmail);
    }
    
    /**
     * 유저 정보 업데이트
     * nickname, position, level 정보만 업데이트
     *
     * @param userId
     * @param userUpdateRequestDto
     */
    public void updateUser(long userId, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userExistAndPasswordValid(userId, userUpdateRequestDto.getPassword());
        user.update(userUpdateRequestDto);
        mybatisUserRepository.update(user);
    }

    /**
     * 유저 삭제
     * DB 삭제를 하는 것이 아닌 user status 상태만 변경
     *
     * @param userId
     * @param password
     */
    public void deleteUser(long userId, String password) {
        User user = userExistAndPasswordValid(userId, password);
        user.delete();
        mybatisUserRepository.update(user);
    }


    public void updateUserPassword(UserUpdatePasswordRequestDto userUpdatePasswordRequestDto) {
        Optional<User> user = mybatisUserRepository.getUserByResetToken(userUpdatePasswordRequestDto.getResetToken());
        if(user.isEmpty()) throw new NotFoundUserException("유저 정보를 찾을 수 없습니다.");

        User updateUser = user.get();
        updateUser.updatePassword(userUpdatePasswordRequestDto.getPassword());
        mybatisUserRepository.update(updateUser);
    }

    /**
     * 유저 이미지 변경
     * 기존 이미지는 삭제
     *
     * @param userId
     * @param multipartFile
     */
    @Transactional
    public void updateUserImage(long userId, MultipartFile multipartFile) {
        Optional<User> user = getUserById(userId);
        if(user.isEmpty()) throw new NotFoundUserException("유저 정보를 찾을 수 없습니다.");
        User updateUser = user.get();

        if(multipartFile != null) {
            awsS3ImageUploadService.delete(user.get().getImage().getImageId());
            Image image = awsS3ImageUploadService.upload(multipartFile);
            updateUser.updateImage(image.getImageId());
            mybatisUserRepository.update(updateUser);
        }
    }

    /**
     * 유저 정보 찾기
     * resetToken 생성
     *
     * @param userEmail
     */
    public void findUserPassword(String userEmail){
        Optional<User> user = getUserByEmail(userEmail);
        if(user.isEmpty()) throw new NotFoundUserException("유저 정보를 찾을 수 없습니다.");

        // 중복을 막기위한 userId 추가
        user.get().findUserPassword(UUIDUtils.getUUID(String.valueOf(user.get().getUserId())));
        mybatisUserRepository.update(user.get());
    }

    /**
     * 유저 정보가 있는지 체크 후 비밀번호 체크
     *
     * @param userId
     * @param password
     * @return
     */
    public User userExistAndPasswordValid(long userId, String password){
        Optional<User> user = getUserById(userId);
        if(user.isEmpty()) throw new NotFoundUserException("유저 정보를 찾을 수 없습니다.");
        if(!BCryptUtil.comparePassword(password, user.get().getPassword())) throw new AuthenticationPasswordException("비밀번호가 맞지 않습니다.");
        return user.get();
    }
}
