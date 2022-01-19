package com.runningmate.runningmate.user.dto.Response;

import com.runningmate.runningmate.position.domain.entity.Position;
import com.runningmate.runningmate.user.aop.LoginCheck.UserLevel;
import com.runningmate.runningmate.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {
    private long userId;
    private String email;
    private String nickName;
    private String resetToken;
    private Position position;
    private String imagePath;
    private LocalDateTime updateDate;
    private LocalDateTime createDate;
    private UserLevel level;
    private List<UserSkillInfoResponseDto> skills;
    private List<UserProjectResponseDto> userProjects;

    public static UserInfoResponseDto of(User user) {
        return UserInfoResponseDto.builder()
            .userId(user.getUserId())
            .email(user.getEmail())
            .nickName(user.getNickName())
            .resetToken(user.getResetToken())
            .position(user.getPosition())
            .level(user.getLevel())
            .createDate(user.getCreateDate())
            .updateDate(user.getUpdateDate())
            .imagePath(user.getImage().getStorageFileName())
            .skills(user.getUserSkills().stream()
                .map(UserSkillInfoResponseDto::of)
                .collect(Collectors.toList()))
            .userProjects(user.getUserProjects().stream()
                .map(UserProjectResponseDto::of)
                .collect(Collectors.toList()))
            .build();
    }
}