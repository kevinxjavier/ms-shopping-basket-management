package com.kevinpina.shopping.management.domain.repository.user;

import com.kevinpina.shopping.management.domain.model.User;

import java.util.List;

/**
 * FetchUserRepository.
 */
public interface FetchUserRepository {

    /**
     * Query user.
     *
     * @param userId userId
     * @return the Users
     */
    List<User> fetch(final String userId);

}
