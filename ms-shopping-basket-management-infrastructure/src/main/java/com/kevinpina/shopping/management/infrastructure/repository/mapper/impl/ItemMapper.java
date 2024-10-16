package com.kevinpina.shopping.management.infrastructure.repository.mapper.impl;

import com.kevinpina.shopping.management.domain.model.Item;
import com.kevinpina.shopping.management.infrastructure.repository.db.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

	ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

	ItemEntity convertToEntity(Item item);
	Item convertToDto(ItemEntity item);

}
