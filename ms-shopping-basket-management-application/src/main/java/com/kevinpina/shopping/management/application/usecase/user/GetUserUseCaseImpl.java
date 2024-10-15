package com.kevinpina.shopping.management.application.usecase.user;

import java.util.List;

import com.kevinpina.shopping.management.domain.model.User;
import com.kevinpina.shopping.management.domain.repository.user.FetchUserRepository;
import com.kevinpina.shopping.management.domain.usecase.user.GetUserUseCase;

import lombok.RequiredArgsConstructor;

/**
 * The default implementation for {@link GetUserUseCase}.
 */
@RequiredArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {

	private final FetchUserRepository fetchUserRepository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<User> getUsers(final String userId) {
		return fetchUserRepository.fetch(userId);
	}

}
