package org.example.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.TopSubscriptionDto;
import org.example.entity.Subscription;
import org.example.entity.User;
import org.example.helpers.SubscriptionType;
import org.example.repository.SubscriptionRepository;
import org.example.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    /**
     * Добавляет одну подписку для указанного пользователя.
     *
     * @param userId  UUID пользователя
     * @param service тип подписки
     * @return сохраненная подписка
     * @throws IllegalArgumentException если пользователь не найден
     */
    @Override
    public Subscription addSubscription(UUID userId, SubscriptionType service) {
        log.debug("Adding subscription for user {}: {}", userId, service);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
        Subscription subscription = Subscription.of(user, service);
        Subscription saved = subscriptionRepository.save(subscription);

        log.info("Subscription added: {}", saved.getId());
        return saved;
    }

    /**
     * Получает все подписки пользователя по его идентификатору.
     *
     * @param userId UUID пользователя
     * @return список Subscription, может быть пустым
     */
    @Override
    public List<Subscription> getUserSubscriptions(UUID userId) {
        log.debug("Fetching subscriptions for user: {}", userId);
        List<Subscription> list = subscriptionRepository.findAllByUserId(userId);
        log.info("Found {} subscriptions for user {}", list.size(), userId);
        return list;
    }

    /**
     * Удаляет указанную подписку пользователя.
     * Проверяет принадлежность, чтобы избежать удаления чужих подписок.
     *
     * @param userId UUID пользователя
     * @param subId  UUID подписки
     */
    @Override
    public void deleteSubscription(UUID userId, UUID subId) {
        log.debug("Deleting subscription {} for user {}", subId, userId);
        subscriptionRepository.findById(subId).ifPresentOrElse(sub -> {
            if (sub.getUser().getId().equals(userId)) {
                subscriptionRepository.delete(sub);
                log.info("Subscription deleted: {}", subId);
            } else {
                log.warn("Subscription {} does not belong to user {}", subId, userId);
            }
        }, () -> log.warn("Subscription not found: {}", subId));
    }

    /**
     * Находит подписку по идентификатору.
     *
     * @param id UUID подписки
     * @return Optional со значением Subscription или пустой, если не найдено
     */
    @Override
    public Optional<Subscription> findById(UUID id) {
        log.debug("Finding subscription by id: {}", id);
        Optional<Subscription> opt = subscriptionRepository.findById(id);
        opt.ifPresentOrElse(
                sub -> log.info("Subscription found: {}", id),
                () -> log.warn("Subscription not found: {}", id)
        );
        return opt;
    }

    /**
     * Возвращает три самых популярных типа подписок.
     *
     * @return список TopSubscriptionDto с типом сервиса и количеством подписок
     */
    @Override
    public List<TopSubscriptionDto> getTop3Subscriptions() {
        log.debug("Fetching top 3 subscriptions");
        List<TopSubscriptionDto> top3 = subscriptionRepository.findTop3Services(PageRequest.of(0, 3));
        log.info("Top 3 subscriptions: {}", top3);
        return top3;
    }
}

