package com.runningmate.runningmate.user.dto.Request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillAddReqeustDto {
    @NotNull
    @Valid
    @Size(min = 1, message = "1개 이상의 스킬이 있어야 합니다.")
    private List<UserSkillSaveReqeustDto> userSkills;
}
