package com.example.svetlogorskchpp.__presentation.electrical_equipment.model

enum class DeepLink(val link: String) {
    TR("myapp://myDialogTrDeepLink/"),
    TSN("myapp://myDialogTsnDeepLink/"),
    TG("myapp://myDialogTgDeepLink/"),
    SWITCHGEAR("myapp://myDialogSwitchgearDeepLink/"),
    EL_MOTOR("myapp://myDialogElMotorDeepLink/"),
    LIGHTING_AND_OTHER("myapp://myDialogLightingAndOtherDeepLink/")
}