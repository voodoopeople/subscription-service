package org.example.service;

import org.example.dto.TopSubscriptionDto;
import org.example.entity.Subscription;
import org.example.helpers.SubscriptionType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubscriptionService {
    Subscription addSubscription(UUID userId, SubscriptionType service);
    List<Subscription> getUserSubscriptions(UUID userId);
    void deleteSubscription(UUID userId, UUID subId);
    Optional<Subscription> findById(UUID id);
    List<TopSubscriptionDto> getTop3Subscriptions();
}
