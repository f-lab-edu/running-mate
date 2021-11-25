package com.runningmate.runningmate.user.service.impl;

import com.runningmate.runningmate.common.utils.BCryptUtil;
import com.runningmate.runningmate.common.utils.SessionUtils;
import com.runningmate.runningmate.user.dto.User;
import com.runningmate.runningmate.user.entity.UserInfo;
import com.runningmate.runningmate.user.repository.UserRepository;
import com.runningmate.runningmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;


/**
 *
 * RequiredArgsConstructor
 * - final 이나 NonNull인 필드들만 파라미터로 받는 생성자를 만들어 준다.
 * - 빈 생성자가 하나만 있고, 생성자의 파라미터 타입이 빈으로 등록 가능할 때
 * - @Autowire 없이 DI가 가능하다.
 *
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;


    /*
    * Optional
    * null이 올 수 있는 값을 감싸는 Wrapper 클래스로, 참조하더라도 NullPointerException을 발생하지 않도록 도와준다
    *
    * */

    /**
     * 로그인 체크
     *  - 아이디(email) 과 비밀번호 체크
     *
     * @param email
     * @param password
     * @return
     * 
     * @author junsoo
     */
    public User loginCheck(String email, String password){
        Optional<UserInfo> userInfo = Optional.ofNullable( userRepository.selectUserByEmail(email) );

        // 가입된 아이디가 없을 경우
        if(userInfo.isEmpty()){
            return null;
        }
        
        // 비밀번호가 다를 경우
        if( !BCryptUtil.comparePassword(password, userInfo.get().getPassword()) ){
            return null;
        }

        User user = User.builder()
                .email(userInfo.get().getEmail())
                .nickName(userInfo.get().getNickName())
                .resetToken(userInfo.get().getResetToken())
                .build();


        return user;
    }

    /**
     * 회원가입 로직 ( 미완 )
     *
     * @param user
     *
     * @author junsoo
     */
    public void insertUser(User user){
        UserInfo insertUserInfo = UserInfo.builder()
                .email(user.getEmail())
                .password( BCryptUtil.setEncrypt( user.getPassword() ) )
                .nickName(user.getNickName())
//                .resetToken(user.getResetToken())
                .build();
        ;
        userRepository.insertUser(insertUserInfo);
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
