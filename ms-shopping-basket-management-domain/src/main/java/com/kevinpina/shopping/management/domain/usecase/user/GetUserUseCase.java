package com.kevinpina.shopping.management.domain.usecase.user;

import com.kevinpina.shopping.management.domain.model.User;

import java.util.List;

/**
 * GetUserUseCase.
 */
public interface GetUserUseCase {

    /**
     * Query user.
     *
     * @param userId userId
     * @return page Service
     */
    List<User> getUsers(final String userId);

}
