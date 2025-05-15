package org.example.dto.mapper;

import org.example.dto.SubscriptionDto;
import org.example.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    @Mapping(source = "service", target = "service")
    @Mapping(source = "id", target = "id")
    SubscriptionDto toDto(Subscription entity);

    List<SubscriptionDto> toDtoList(List<Subscription> entities);
}
