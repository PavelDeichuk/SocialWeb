package com.pavel.socialweb.Mapper;

import com.pavel.socialweb.Dto.SubscriptionDto;
import com.pavel.socialweb.Entity.SubscriptionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionMapper {
    SubscriptionDto SUBSCRIPTION_DTO(SubscriptionEntity subscriptionEntity);
}
