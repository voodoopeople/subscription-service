package org.example.helpers;

import java.util.Arrays;

/**
 * Перечисления для управления работы с подписками
 */
public enum SubscriptionType {
    YOUTUBE_PREMIUM("YouTube Premium"),
    VK_MUSIC("VK Music"),
    YANDEX_PLUS("Yandex Plus"),
    NETFLIX("Netflix");

    private final String displayName;

    SubscriptionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static SubscriptionType from(String value) {
        return Arrays.stream(values())
                .filter(s -> s.name().equalsIgnoreCase(value) || s.displayName.equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown subscription type: " + value));
    }
}
