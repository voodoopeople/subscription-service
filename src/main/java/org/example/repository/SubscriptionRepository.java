package org.example.repository;


import org.example.dto.TopSubscriptionDto;
import org.example.entity.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    /**
     * Топ-3 подписок по количеству. Возвращает DTO с типом сервиса и количеством.
     */
    @Query("SELECT new org.example.dto.TopSubscriptionDto(s.service, COUNT(s)) " +
            "FROM Subscription s GROUP BY s.service ORDER BY COUNT(s) DESC")
    List<TopSubscriptionDto> findTop3Services(Pageable pageable);

    /**
     * Находит все подписки пользователя по его UUID.
     * @param userId UUID пользователя
     * @return список подписок, может быть пустым
     */
    List<Subscription> findAllByUserId(UUID userId);
}
