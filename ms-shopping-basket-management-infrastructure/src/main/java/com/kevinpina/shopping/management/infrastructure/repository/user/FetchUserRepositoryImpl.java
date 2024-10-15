package com.kevinpina.shopping.management.infrastructure.repository.user;

import com.kevinpina.shopping.management.domain.model.User;
import com.kevinpina.shopping.management.domain.repository.user.FetchUserRepository;
import com.kevinpina.shopping.management.infrastructure.repository.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * The default implementation for {@link FetchUserRepository}.
 */
@RequiredArgsConstructor
public class FetchUserRepositoryImpl  implements FetchUserRepository {

    private final UserRepository userRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> fetch(final String userId) {
        return userRepository.findUserById(userId).stream().map(u -> User.builder()
                .userId(u.getUserId())
                .email(u.getEmail())
                .firstName(u.getFirstName())
                .lastName(u.getLastName())
                .build())
                .toList();
    }

}
