package com.example.svetlogorskchpp.__domain.mapper.electrical_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TransformerOwnNeeds
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TransformerOwnNeedsMapper @Inject constructor() {

    fun toTransformerOwnNeeds (transformerOwnNeedsEntity: TransformerOwnNeedsEntity) : TransformerOwnNeeds {
        return with(transformerOwnNeedsEntity) {
            TransformerOwnNeeds(
                id = id,
                name = name,
                panelMcp = panelMcp,
                type = type,
                parameterType = parameterType,
                transcriptType = transcriptType,
                additionally = additionally,
                isSpare = isSpare,
                powerSupplyId = powerSupplyId,
                powerSupplyCell = powerSupplyCell,
                powerSupplyName = powerSupplyName,
                voltage = voltage.let { Voltage.valueOf(it) },
                typeSwitch = typeSwitch,
                typeInsTr = typeInsTr,
                automation = automation,
                apv = apv,
                phaseProtection = Json.decodeFromString(phaseProtection),
                earthProtection = Json.decodeFromString(earthProtection)
            )
        }
    }

    fun toTransformerOwnNeedsEntity (transformerOwnNeeds: TransformerOwnNeeds) : TransformerOwnNeedsEntity {
        return with(transformerOwnNeeds) {
            TransformerOwnNeedsEntity(
                id = id,
                name = name,
                panelMcp = panelMcp,
                type = type,
                parameterType = parameterType,
                transcriptType = transcriptType,
                additionally = additionally,
                isSpare = isSpare,
                powerSupplyId = powerSupplyId,
                powerSupplyCell = powerSupplyCell,
                powerSupplyName = powerSupplyName,
                voltage = voltage.name,
                typeSwitch = typeSwitch,
                typeInsTr = typeInsTr,
                automation = automation,
                apv = apv,
                phaseProtection = Json.encodeToString(phaseProtection),
                earthProtection = Json.encodeToString(earthProtection)
            )
        }
    }

}