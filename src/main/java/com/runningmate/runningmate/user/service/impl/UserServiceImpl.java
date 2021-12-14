package com.runningmate.runningmate.user.service.impl;

import com.runningmate.runningmate.common.utils.BCryptUtil;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.repository.UserRepository;
import com.runningmate.runningmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    /**
     * 회원가입 로직 ( 미완 )
     *
     * @param userSaveDto
     *
     * @author junsoo
     */
    public void insertUser(UserSaveDto userSaveDto) {
        User insertUser = User.builder()
                .email(userSaveDto.getEmail())
                .password(BCryptUtil.setEncrypt(userSaveDto.getPassword()))
                .nickName(userSaveDto.getNickName())
//                .resetToken(user.getResetToken())
                .build();

        userRepository.save(insertUser);
    }
}
