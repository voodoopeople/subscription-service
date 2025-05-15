package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.helpers.SubscriptionType;

@Getter
@AllArgsConstructor
public class TopSubscriptionDto {
    private final SubscriptionType service;
    private final long count;
}
