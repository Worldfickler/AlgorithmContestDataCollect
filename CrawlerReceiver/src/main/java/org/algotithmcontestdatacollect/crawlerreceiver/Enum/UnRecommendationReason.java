package org.algotithmcontestdatacollect.crawlerreceiver.Enum;

public enum UnRecommendationReason {

    DIFFICULTY_IS_ZERO(1),
    SPECIAL_LANGUAGE_COMPETITION(2),
    FORBIDDEN(3);

    private final Integer value;
    public int getValue() {
        return value;
    }
    UnRecommendationReason(Integer value) {
        this.value = value;
    }

}
