package com.ddalkkak.splitting.board.infrastructure.entity;

public enum Category {
    정치("politics"),
    
    축구("soccer"),
    야구("baseball"),
    농구("basketball"),
    기타스포츠("etc-sports"),
    
    리그오브레전드("lol"),
    스타크래프트("starcraft"),
    FPS("fps"),
    RPG("rpg"),
    모바일("mobile"),
    기타_게임("etc-games"),


    연예인("celebrity"),
    여자아이돌("girl-group"),
    남자아이돌("boy-group"),
    애니("cartoon"),
    웹툰("webtoon"),
    캐릭터("character"),


    패션("fashion"),

    운동("health"),

    영화("movie"),

    음식("food"),

    여행("travel"),

    애완동물("pet"),

    음악("music"),


    인터넷방송("internet"),
    유튜버("youtuber"),
    버튜버("vtuber");

    public final String name;

    private Category(String name){
        this.name = name;
    }

    public static Category fromValue(String value) {
        for (Category type : Category.values()) {
            if (type.name.equals(value)) {
                return type;
            }
        }
        // 일치하는 값이 없을 경우 예외 처리
        throw new IllegalArgumentException("Unknown game type: " + value);
    }

}
