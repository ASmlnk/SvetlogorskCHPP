package com.example.svetlogorskchpp.__data.model

enum class FirebaseKey(val getString: String) {
    COLLECTION_REQUEST_WORK("Заявки"),
    DOCUMENT_REQUEST_WORK("requestWork"),
    COLLECTION_ELECTRICAL_EQUIPMENT("Электрооборудование"),
    COLLECTION_EQUIPMENT("Оборудование"),
    DOCUMENT_ORY("ОРУ"),
    DOCUMENT_TR("ОРУ Тр"),
    DOCUMENT_TSN("ТСН"),
    DOCUMENT_TG("ТГ"),
    DOCUMENT_EL_MOTOR("Электродвигатели"),
    DOCUMENT_SWITCHGEAR("Сборки"),
    DOCUMENT_LIGHTING_AND_OTHER("Освещение и др"),
    DOCUMENT_ORY_REZ("рез ОРУ"),
    DOCUMENT_TR_REZ("рез ОРУ Тр"),
    DOCUMENT_TSN_REZ("рез ТСН"),
    DOCUMENT_TG_REZ("рез ТГ"),
    DOCUMENT_EL_MOTOR_REZ("рез Электродвигатели"),
    DOCUMENT_SWITCHGEAR_REZ("рез Сборки"),
    DOCUMENT_LIGHTING_AND_OTHER_REZ("рез Освещение и др"),
    DOCUMENT_EDIT("Edit"),
    DOCUMENT_MECHANISM_INFO("Механизмы инфо"),
    DOCUMENT_MECHANISM_INFO_REZ("Механизмы инфо рез"),

}