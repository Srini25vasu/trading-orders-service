package com.jusmore.trading.platform.idempotency.mapper;

import com.jusmore.trading.platform.idempotency.domain.IdempotencyKey;
import com.jusmore.trading.platform.idempotency.dto.IdempotencyKeyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IdempotencyKeyMapper {

    IdempotencyKeyDto toDto(IdempotencyKey idempotencyKey);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lockedAt", ignore = true)
    void toEntity(IdempotencyKeyDto idempotencyKeyDto, @MappingTarget IdempotencyKey idempotencyKey);
}

