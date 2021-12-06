package com.runningmate.runningmate.user.service.impl;

import com.runningmate.runningmate.common.utils.BCryptUtil;
import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.user.dto.UserLoginRequestDto;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.repository.UserRepository;
import com.runningmate.runningmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    private final ModelMapper modelMapper;
    /**
     * 로그인 체크
     *  - 아이디(email) 과 비밀번호 체크
     *
     * @param userLoginRequestDto
     * @return 로그인 성공시 로그인 데이터 리턴 / 실패시 null
     *
     * @author junsoo
     */
    public Optional<User> login(UserLoginRequestDto userLoginRequestDto) {
        String loginRequestEmail = userLoginRequestDto.getEmail();
        String loginRequestPassword = userLoginRequestDto.getPassword();

        Optional<User> userInfo = userRepository.findByEmail(loginRequestEmail);
        if(userInfo.isEmpty() || !BCryptUtil.comparePassword(loginRequestPassword, userInfo.get().getPassword())){
            return Optional.empty();
        }
        // 세션등록
        this.loginUser(userInfo.get().getUserId());
        return userInfo;
    }

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

    /**
     *  로그인 세션 등록
     *
     * @param userId
     * @author junsoo
     */
    public void loginUser(long userId) {
        SessionUtils.setLoginSessionUserId(userId);
    }

    /**
     * 로그아웃 세션 제거
     *
     * @author junsoo
     */
    public void logout() {
        SessionUtils.logoutSession();
    }
}
