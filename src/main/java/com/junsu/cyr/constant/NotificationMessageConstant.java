package com.junsu.cyr.constant;

public class NotificationMessageConstant {
    public static final String CHAT_INVITATION = "%s님이 1대1 대화방에 초대했습니다.";
    public static final String NEW_ANNOUNCEMENT = "새로운 공지사항 '%s'가 등록되었습니다.";
    public static final String ACCOMPLISH_ACHIEVEMENT = "새로운 업적 '%s'을 달성했습니다.";
    public static final String NEW_POLL = "새로운 투표가 등록되었습니다.";
    public static final String ENTER_RANKING = "랭킹 10위 안에 들었습니다.";
    public static final String EVENT = "새로운 이벤트 '%s'가 등록되었습니다.";
    public static final String TEMPERATURE_MAXIMUM = "활동 온도가 최대치에 달성했습니다.";
    public static final String TEMPERATURE_MAXIMUM_AND_CRAFT_GLASS = "활동 온도가 최대치에 달성해서 유리 조각을 만들 수 있습니다.";

    public static String format(String template, String targetName) {
        return String.format(template, targetName);
    }
}
