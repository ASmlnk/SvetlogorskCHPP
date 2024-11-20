package com.example.svetlogorskchpp.__domain.mapper.electrical_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.LightingAndOther
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class LightingAndOtherMapper @Inject constructor() {

    fun toLightingAndOtherEntity(lightingAndOther: LightingAndOther): LightingAndOtherEntity {
        return with(lightingAndOther) {
            LightingAndOtherEntity(
                id = id,
                nam = name,
                powSuId = powerSupplyId,
                powSuC = powerSupplyCell,
                powSuNam = powerSupplyName,
                tSw = typeSwitch,
                addit = additionally,
                isLi = isLighting,
                loc = location
            )
        }
    }

    fun toLightingAndOther(lightingAndOtherEntity: LightingAndOtherEntity): LightingAndOther {
        return with(lightingAndOtherEntity) {
            LightingAndOther(
                id = id,
                name = nam,
                powerSupplyId = powSuId,
                powerSupplyCell = powSuC,
                powerSupplyName = powSuNam,
                typeSwitch = tSw,
                additionally = addit,
                isLighting = isLi,
                location = loc
            )
        }
    }
}