package com.realfinance.sofa.flow.facade;

import com.realfinance.sofa.flow.model.IdmGroupDto;
import com.realfinance.sofa.flow.model.IdmUserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public interface IdmFacade {

    Page<IdmUserDto> queryUsers(String filter, @NotNull Pageable pageable);

    Page<IdmUserDto> queryUsers(String filter, Set<String> userScope, Set<String> groupScope, @NotNull Pageable pageable);

    Page<IdmGroupDto> queryGroups(String filter, @NotNull Pageable pageable);
}
