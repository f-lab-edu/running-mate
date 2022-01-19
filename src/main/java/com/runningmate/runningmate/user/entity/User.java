package com.runningmate.runningmate.user.entity;

import com.runningmate.runningmate.common.utils.BCryptUtil;
import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.position.domain.entity.Position;
import com.runningmate.runningmate.user.aop.LoginCheck.UserLevel;
import com.runningmate.runningmate.user.dto.Request.UserUpdateRequestDto;
import java.util.List;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    // 유저 고유 ID
    private long userId;

    // 이메일
    @NonNull
    private String email;

    // 비밀번호
    @NonNull
    private String password;

    // 닉네임
    @NonNull
    private String nickName;

    // 포지션
    private Position position;

    // 이미지
    private Image image;

    // 비밀번호 초기화 토큰
    private String resetToken;

    // 수정날짜
    private LocalDateTime updateDate;

    // 생성날짜
    private LocalDateTime createDate;

    // 권한
    private UserLevel level;
    
    // 유저스킬
    private List<UserSkill> userSkills;

    private UserStatus status;

    private List<UserProject> userProjects;


    public void delete(){
        status = UserStatus.DELETE;
        updateDate = LocalDateTime.now();
    }

    public void update(UserUpdateRequestDto userUpdateRequestDto){
        nickName = userUpdateRequestDto.getNickName();
        position = Position.builder().positionId(userUpdateRequestDto.getPositionId()).build();
        level = userUpdateRequestDto.getLevel();
    }

    public void updatePassword(String changePassword){
        password = BCryptUtil.setEncrypt(changePassword);
        updateDate = LocalDateTime.now();
    }

    public void updateImage(long imageId){
        image = Image.builder().imageId(imageId).build();
        updateDate = LocalDateTime.now();
    }

    public void findUserPassword(String findUserPasswordToken){
        resetToken = findUserPasswordToken;
        updateDate = LocalDateTime.now();
    }
}
