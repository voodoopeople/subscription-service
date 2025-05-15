package org.example.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.SubscriptionDto;
import org.example.dto.SubscriptionRequestDto;
import org.example.dto.TopSubscriptionDto;
import org.example.dto.mapper.SubscriptionMapper;
import org.example.entity.Subscription;
import org.example.service.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST-контроллер для управления подписками.
 * Предоставляет CRUD-операции над ресурсом Subscription.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    /**
     * Добавляет подписку для пользователя.
     * POST /users/{userId}/subscriptions
     */
    @PostMapping("/users/{userId}/subscriptions")
    public ResponseEntity<SubscriptionDto> addSubscriptions(
            @PathVariable UUID userId,
            @RequestBody SubscriptionRequestDto sbsRequest) {

        log.debug("Request to add subscription '{}' for user {}", sbsRequest.getService(), userId);
        Subscription created = subscriptionService.addSubscription(userId, sbsRequest.getService());
        log.info("Subscription created: {} for user {}", created.getId(), userId);

        SubscriptionDto response = subscriptionMapper.toDto(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Получает все подписки пользователя.
     * GET /users/{userId}/subscriptions
     */
    @GetMapping("/users/{userId}/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> getUserSubscriptions(@PathVariable UUID userId) {

        log.debug("Request to fetch subscriptions for user {}", userId);
        List<Subscription> list = subscriptionService.getUserSubscriptions(userId);
        log.info("Fetched {} subscriptions for user {}", list.size(), userId);

        List<SubscriptionDto> dtos = subscriptionMapper.toDtoList(list);
        return ResponseEntity.ok(dtos);
    }

    /**
     * Удаляет указанную подписку.
     * DELETE /users/{userId}/subscriptions/{subId}
     */
    @DeleteMapping("/users/{userId}/subscriptions/{subId}")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable UUID userId,
            @PathVariable UUID subId) {

        log.debug("Request to delete subscription {} for user {}", subId, userId);
        subscriptionService.deleteSubscription(userId, subId);
        log.info("Subscription deleted: {} for user {}", subId, userId);

        return ResponseEntity.noContent().build();
    }

    /**
     * Топ-3 популярных подписок.
     * GET /subscriptions/top
     */
    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<TopSubscriptionDto>> getTop3Subscriptions() {

        log.debug("Request to fetch top 3 subscriptions");
        List<TopSubscriptionDto> top3 = subscriptionService.getTop3Subscriptions();
        log.info("Top 3 subscriptions returned: {}", top3);

        return ResponseEntity.ok(top3);
    }
}
