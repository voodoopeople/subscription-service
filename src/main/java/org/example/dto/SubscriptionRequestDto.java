package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.helpers.SubscriptionType;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequestDto {
    private SubscriptionType service;
}
