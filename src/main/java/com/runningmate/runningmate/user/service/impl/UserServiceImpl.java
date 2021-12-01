package com.runningmate.runningmate.user.service.impl;

import com.runningmate.runningmate.common.utils.BCryptUtil;
import com.runningmate.runningmate.user.dto.UserSaveDto;
import com.runningmate.runningmate.user.entity.User;
import com.runningmate.runningmate.user.entity.UserSkill;
import com.runningmate.runningmate.user.repository.UserRepository;
import com.runningmate.runningmate.user.repository.UserSkillRepository;
import com.runningmate.runningmate.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final UserSkillRepository userSkillRepository;


    /**
     * 회원가입
     *
     * @param userSaveDto
     *
     * @author junsoo
     */
    @Transactional
    public void insertUser(UserSaveDto userSaveDto) {
        modelMapper.typeMap(UserSaveDto.class, User.class).addMappings(
            mapper->{
                mapper.map(src->src.getUserPostition().getPositionId() ,User::setPositionId);
                mapper.map(src->BCryptUtil.setEncrypt(userSaveDto.getPassword()) ,User::setPassword );
            });
        User user = modelMapper.map(userSaveDto, User.class);

        // 유저 삽입
        userRepository.save(user);

        // 스킬 삽입
        List<UserSkill> skillList = modelMapper.map(userSaveDto.getUserSkill(), new TypeToken<List<UserSkill>>(){}.getType());
        skillList.forEach(e -> e.setUserId(user.getUserId()));
        userSkillRepository.skillSaveAll(skillList);
    }
    /**
     *  아이디 체크
     *
     * @param email
     * @return User
     * @author junsoo
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

}
