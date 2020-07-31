package org.flamemad.bilispider.constant;

public enum Zone {
    All(0),

    //动画区
    mad_amv(24),
    mmd_3d(25),
    anime_mixed(27),
    dubbing_of_short_script(47),
    tokusatsu(86),
    model(210),

    //生活区
    daily(21),
    foods(76),
    animals(75),
    hand_make(161),
    painting(162),
    sports(163),
    automobile(176),
    lives_others(174),
    fun(138),

    //游戏区
    game_stand_alone(17),
    e_sports(171),
    mobile_game(172),
    online_game(65),
    board_game(173),
    gmv(121),
    music_game(136),
    mugen(19),

    //鬼畜区
    kichiku(22),
    kichiku_mad(26),
    manual_vocaloid(126),
    kichiku_course(127),

    //娱乐区
    variety(71),
    star(137),

    //舞蹈区
    otaku_dance(20),
    hiphop_dance(198),
    star_dance(199),
    china_dance(200),
    dance_mix(154),
    dance_course(156),

    //影视区
    cine_cism(182),
    montage(183),
    shortflim(85),
    trailer_info(184),

    //国创区
    Chinese_anime(153),
    Chinese_anime_original(168),
    puppety(169),
    motion_comic(195),
    Chinese_anime_information(170),

    //数码区
    mobile(95),
    pc(189),
    photography(190),
    intelligence_av(191),

    //知识区
    science(201),
    tech_fun(124),
    finance(207),
    campus(208),
    career(209),
    wild_tech(122),

    //音乐区
    music_original(28),
    music_cover(31),
    vocaloid(30),
    electronic(194),
    perform(59),
    mv(193),
    live(29),
    music_other(130),

    ;


    private final int zoneNumber;

    Zone(int zoneNumber) {
        this.zoneNumber = zoneNumber;
    }

    public int getZoneNumber() {
        return zoneNumber;
    }

}
