package com.ddalkkak.splitting.board.infrastructure.entity;

public enum Category {
    POLITICS("정치"),
    
    SOCCER("축구"),
    BASEBALL("야구"),
    BASKETBALL("농구"),
    ETC_SPORTS("기타 스포츠"),
    
    LOL("lol"),
    STARCRAFT("스타크래프트"),
    FPS("FPS"),
    RPG("RPG"),
    MOBILE("모바일"),
    ETC_GAMES("기타 게임"),


    CELEBRITY("연예인"),
    GIRL_GROUP("여자아이돌"),
    BOY_GROUP("남자아이돌"),
    CARTOON("애니"),
    WEBTOON("웹툰"),
    CHARACTER("캐릭터"),


    FASHION("패션"),

    HEALTH("운동"),

    MOVIE("영화"),

    FOOD("음식"),

    TRAVEL("여행"),

    PET("애완동물"),

    MUSIC("음악"),


    INTERNET("인터넷방송"),
    YOUTUBER("유튜버"),
    VTUBER("버튜버");

    public final String name;

    private Category(String name){
        this.name = name;
    }

    public static Category fromValue(String value) {
        for (Category type : Category.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        // 일치하는 값이 없을 경우 예외 처리
        throw new IllegalArgumentException("Unknown game type: " + value);
    }

}
