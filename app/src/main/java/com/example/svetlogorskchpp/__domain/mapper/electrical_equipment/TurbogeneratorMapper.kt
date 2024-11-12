package com.example.svetlogorskchpp.__domain.mapper.electrical_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.TurboGenerator
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class TurbogeneratorMapper @Inject constructor()  {

    fun toTurbogenerator (turbogeneratorEntity: TurboGeneratorEntity) : TurboGenerator {
        return with(turbogeneratorEntity) {
            TurboGenerator(
                id = id,
                name = name,
                panelMcp = panelMcp,
                typeSwitch = typeSwitch,
                typeInsTr = typeInsTr,
                typeGenerator = typeGenerator,
                transcriptTypeGenerator = transcriptTypeGenerator,
                volumeTg = volumeTg,
                volumeReceiver = volumeReceiver,
                additionallyGenerator = additionallyGenerator,
                sourceExcitation = sourceExcitation,
                generatorStarted = generatorStarted,
                translationIntoRv = translationIntoRv,
                translationFromRv = translationFromRv,
                typeTurbin = typeTurbin,
                transcriptTypeTurbin = transcriptTypeTurbin,
                powerEl = powerEl,
                powerThermal = powerThermal,
                steamConsumption = steamConsumption,
                additionallyTurbin = additionallyTurbin,
                powerSupplyId = powerSupplyId,
                powerSupplyCell = powerSupplyCell,
                powerSupplyName = powerSupplyName,
                automation = automation,
                phaseProtection = Json.decodeFromString(phaseProtection),
                earthProtection = Json.decodeFromString(earthProtection),
                additionallyRza1 = additionallyRza1,
                additionallyRza2 = additionallyRza2
            )
        }
    }

    fun toTurbogeneratorEntity (turbogenerator: TurboGenerator) : TurboGeneratorEntity {
        return with(turbogenerator) {
            TurboGeneratorEntity(
                id = id,
                name = name,
                panelMcp = panelMcp,
                typeSwitch = typeSwitch,
                typeInsTr = typeInsTr,
                typeGenerator = typeGenerator,
                transcriptTypeGenerator = transcriptTypeGenerator,
                volumeTg = volumeTg,
                volumeReceiver = volumeReceiver,
                additionallyGenerator = additionallyGenerator,
                sourceExcitation = sourceExcitation,
                generatorStarted = generatorStarted,
                translationIntoRv = translationIntoRv,
                translationFromRv = translationFromRv,
                typeTurbin = typeTurbin,
                transcriptTypeTurbin = transcriptTypeTurbin,
                powerEl = powerEl,
                powerThermal = powerThermal,
                steamConsumption = steamConsumption,
                additionallyTurbin = additionallyTurbin,
                powerSupplyId = powerSupplyId,
                powerSupplyCell = powerSupplyCell,
                powerSupplyName = powerSupplyName,
                automation = automation,
                phaseProtection = Json.encodeToString(phaseProtection),
                earthProtection = Json.encodeToString(earthProtection),
                additionallyRza1 = additionallyRza1,
                additionallyRza2 = additionallyRza2
            )
        }
    }

}