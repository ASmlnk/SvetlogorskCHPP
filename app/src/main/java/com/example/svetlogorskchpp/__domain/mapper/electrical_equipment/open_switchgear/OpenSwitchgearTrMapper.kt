package com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearTr
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class OpenSwitchgearTrMapper @Inject constructor() {

    fun toOpenSwitchgearTr(openSwitchgearTrEntity: OpenSwitchgearTrEntity): OpenSwitchgearTr {
        return with(openSwitchgearTrEntity) {
            OpenSwitchgearTr(
                id = id,
                name = name,
                panelMcp = panelMcp,
                type = type,
                parameterType = parameterType,
                transcriptType = transcriptType,
                additionally = additionally,
                isSpare = isSpare,
                isThreeWinding = isThreeWinding,
                bysSystemVn = bysSystemVn,
                cellVn = cellVn,
                voltageVn = voltageVn.let { Voltage.valueOf(it) }?: Voltage.KV,
                typeSwitchVn = typeSwitchVn,
                typeInsTrVn = typeInsTrVn,
                keyShr1Vn = keyShr1Vn.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                keyShr2Vn = keyShr2Vn.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                keyLrVn = keyLrVn.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                keyOrVn = keyOrVn.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                bysSystemSn = bysSystemSn,
                cellSn = cellSn,
                voltageSn = voltageSn.let { Voltage.valueOf(it) }?: Voltage.KV,
                typeSwitchSn = typeSwitchSn,
                typeInsTrSn = typeInsTrSn,
                keyShr1Sn = keyShr1Sn.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                keyShr2Sn = keyShr2Sn.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                keyLrSn = keyLrSn.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                keyOrSn = keyOrSn.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                automation = automation,
                apv = apv,
                phaseProtection = Json.decodeFromString(phaseProtection),
                earthProtection = Json.decodeFromString(earthProtection)
            )
        }
    }

    fun toOpenSwitchgearTrEntity(openSwitchgearTr: OpenSwitchgearTr): OpenSwitchgearTrEntity {
        return with(openSwitchgearTr) {
            OpenSwitchgearTrEntity(
                id = id,
                name = name,
                panelMcp = panelMcp,
                type = type,
                parameterType = parameterType,
                transcriptType = transcriptType,
                additionally = additionally,
                isSpare = isSpare,
                isThreeWinding = isThreeWinding,
                bysSystemVn = bysSystemVn,
                cellVn = cellVn,
                voltageVn = voltageVn.name,
                typeSwitchVn = typeSwitchVn,
                typeInsTrVn = typeInsTrVn,
                keyShr1Vn = keyShr1Vn.name,
                keyShr2Vn = keyShr2Vn.name,
                keyLrVn = keyLrVn.name,
                keyOrVn = keyOrVn.name,
                bysSystemSn = bysSystemSn,
                cellSn = cellSn,
                voltageSn = voltageSn.name,
                typeSwitchSn = typeSwitchSn,
                typeInsTrSn = typeInsTrSn,
                keyShr1Sn = keyShr1Sn.name,
                keyShr2Sn = keyShr2Sn.name,
                keyLrSn = keyLrSn.name,
                keyOrSn = keyOrSn.name,
                automation = automation,
                apv = apv,
                phaseProtection = Json.encodeToString(phaseProtection),
                earthProtection = Json.encodeToString(earthProtection)
            )
        }
    }
}