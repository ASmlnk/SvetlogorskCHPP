package com.example.svetlogorskchpp.__domain.en

enum class JobTitle(val nameBD: String, val nameApp: String) {
    NSS("NSS", "Начальник смены электростанции"),
    NSE("NSE", "Начальник смены цеха электростанции (электрического)"),
    DEM_5R("DEM_5R", "Электромонтер по обслуживанию электрооборудования электростанций (5р)"),
    DEM_6R("DEM_6R", "Электромонтер по обслуживанию электрооборудования электростанций (6р)"),
    TITLE_NO("", "")
}