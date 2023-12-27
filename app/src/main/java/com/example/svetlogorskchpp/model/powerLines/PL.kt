package com.example.svetlogorskchpp.model.powerLines

enum class PL(val get: String) {
    SZIV("ВЛ-110 СЗИВ"),
    ZAVODSKAI1("КВЛ-110 №1 Заводская"),
    LAVSAN("ВЛ-110 Лавсан"),
    ZAVODSKAI2("КВЛ-110 №2 Заводская"),
    VOLODARSK("ВЛ-110 Володарск"),
    ZAVODSKAI3("КВЛ-110 №3 Заводская"),
    ZABRODIE("ВЛ-110 Забродье"),
    OVV110("ОВВ-110"),
    PARICHI("ВЛ-110 Паричи"),
    SHSV1("ШСВ-110"),
    KOZLOVICHI("ВЛ-110 Козловичи"),
    SVETLOGORSK110("ВЛ-110 Светлогорск 110"),
    SVETLOGORSK220("ВЛ-110 Светлогорск 220"),
    TN110_1("ТН IСШ-110"),
    TN110_2("ТН IIСШ-110"),
    TN220("ТН-220"),
    TN220_2("ТН IIСШ-220"),
    TN110_OSH("ТН ОСШ-110"),
    MIRADINO("ВЛ-220 Мирадино"),
    SVETLOGORSK("ВЛ-220 Светлогорск"),
    SHSV2("ШСВ-220"),
    OVV220("ОВВ-220"),
    OPN1_110("ОПН-110 IСШ"),
    T_1("Т-1"),
    T_2("Т-2"),
    T_3_110("АТ-3 110"),
    T_3_220("АТ-3 220"),
    T_4_110("АТ-4 110"),
    T_4_220("АТ-4 220"),
    T_5("Т-5"),
    T_6("Т-6"),
    T_20("20Т"),
    ORY_110("ОРУ-110"),
    ORY_220("ОРУ-220"),
    APV_15_20("1.5, 20"),
    APV_2_20("2, 20"),
    APV_15("1.5"),
    APV_2("2"),
    APV_25("2.5"),
    APV_Z("без АПВ"),
    APV_K("1.5, 20(короткий); 1.5(длинный)"),
    APV_KONSH("КОНШ"),
    APV_KNNSH("КННШ"),
    APV_KONL_KNNSH("КОНЛ с КННШ"),
    APV_KONSH_KNNL("КОНШ с КННЛ"),
    APV_KS("КС"),
    APV_KONL("КОНЛ"),
    EARTH_PROTECTION_1(
        "ТЗНП\nНТЗНП - I, II"
    ),
    EARTH_PROTECTION_2(
        "ДЗ - I, II\nТНОП - I\nМТЗНП - II, III, IV"
    ),
    EARTH_PROTECTION_3(
        "НТЗНП - I, II, III, IV"
    ),
    EARTH_PROTECTION_4(
        "ТЗНП-I\nНТЗНП - II, III, IV"
    ),
    EARTH_PROTECTION_5(
        "ТЗНП - I, II, III, IV"
    ),
    PHASE_PROTECTION_1(
        "ТО\nМТЗ - I, II\nобрыв фазы"
    ),
    PHASE_PROTECTION_2(
        "ДЗ - I, II, III\nТО"
    ),
    PHASE_PROTECTION_3(
        "ДЗ - I, II, III, IV, V\nТО\nМТЗ"
    ),
    PHASE_PROTECTION_4(
        "ДЗ - I, II, III, IV\nМТЗ\nобрыв фазы"
    ),
    PHASE_PROTECTION_5(
        "ДЗ - I, II, III"
    ),
    PHASE_PROTECTION_6(
        "ДЗ - I, II, III\nТО\nДФЗ"
    )


}