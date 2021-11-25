package com.runningmate.runningmate.user.service.impl;

import com.runningmate.runningmate.common.utils.BCryptUtil;
import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.repository.UserRepository;
import com.runningmate.runningmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    /**
     * 로그인 체크
     *  - 아이디(email) 과 비밀번호 체크
     *
     * @param email
     * @param password
     * @return 로그인 성공시 로그인 데이터 리턴 / 실패시 null
     * 
     * @author junsoo
     */
    public UserSaveDto loginCheck(String email, String password){
        Optional<User> userInfo = Optional.ofNullable(userRepository.findByEmail(email));

        // 가입된 아이디가 없을 경우
        if(userInfo.isEmpty()){
            return null;
        }
        
        // 비밀번호가 다를 경우
        if(!BCryptUtil.comparePassword(password, userInfo.get().getPassword())){
            return null;
        }

        UserSaveDto userSaveDto = com.runningmate.runningmate.user.dto.UserSaveDto.builder()
                .email(userInfo.get().getEmail())
                .nickName(userInfo.get().getNickName())
                .resetToken(userInfo.get().getResetToken())
                .build();

        return userSaveDto;
    }

    /**
     * 회원가입 로직 ( 미완 )
     *
     * @param userSaveDto
     *
     * @author junsoo
     */
    public void insertUser(UserSaveDto userSaveDto){
        User insertUser = User.builder()
                .email(userSaveDto.getEmail())
                .password(BCryptUtil.setEncrypt(userSaveDto.getPassword()))
                .nickName(userSaveDto.getNickName())
//                .resetToken(user.getResetToken())
                .build();

        userRepository.saveUser(insertUser);
    }

    /**
     *  로그인 세션 등록
     *
     * @param session
     * @param userEmail
     * @author junsoo
     */
    public void loginUser(HttpSession session, String userEmail) {
        SessionUtils.setLoginSessionEmail(session, userEmail);
    }

    /**
     * 로그아웃 세션 제거
     *
     * @param session
     * @author junsoo
     */
    public void logout(HttpSession session) {
        SessionUtils.logoutSession(session);
    }
}
