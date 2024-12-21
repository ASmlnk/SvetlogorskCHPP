package com.example.svetlogorskchpp.__domain.mapper.electrical_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.ElMotor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ElMotorMapper @Inject constructor() {

    fun toElMotor(elMotorEntity: ElMotorEntity): ElMotor {
        return with(elMotorEntity) {
            ElMotor(
                id = id,
                name = nam,
                powerSupplyId = powSuId,
                powerSupplyCell = powSuC,
                powerSupplyName = powSuNam,
                automation = automation,
                phaseProtection = if (phPr.isEmpty()) Json.decodeFromString("[]") else Json.decodeFromString(
                    phPr
                ),
                earthProtection = if (eaPr.isEmpty()) Json.decodeFromString("[]") else Json.decodeFromString(
                    eaPr
                ),
                additionallyRza = addRz,
                category = cat.let { ElCategory.valueOf(it) },
                generalCategory = gCat.let { ElGeneralCategory.valueOf(it) },
                typeSwitch = tSw,
                typeInsTr = tIT,
                additionally = addit,
                isRep = isRep,
                typeRep = tRep,
                controlCircuits = conCir,
                powerEl = powEl,
                voltage = vol.let { Voltage.valueOf(it) },
                i = i,
                n = n,
                typeEl = tEl,
                mechanismType = mecTyp,
                mechanismPerformance = mecPer,
                mechanismPressure = mecPr,
                mechanismN = mecN,
                mechanismH = mecH?:"",
                mechanismPowerN = mecPowN?:"",
                mechanismAdditionally = mecAddit?:"",
                mechanismInfoId = mecInfoId?:"",
                mechanismInfoName = mecInfoName?:""
            )
        }
    }

    fun toElMotorEntity(elMotor: ElMotor): ElMotorEntity {
        return with(elMotor) {
            ElMotorEntity(
                id = id,
                nam = name,
                powSuId = powerSupplyId,
                powSuC = powerSupplyCell,
                powSuNam = powerSupplyName,
                automation = automation,
                phPr = Json.encodeToString(phaseProtection),
                eaPr = Json.encodeToString(earthProtection),
                addRz = additionallyRza,
                cat = category.name,
                gCat = generalCategory.name,
                tSw = typeSwitch,
                tIT = typeInsTr,
                addit = additionally,
                isRep = isRep,
                tRep = typeRep,
                conCir = controlCircuits,
                powEl = powerEl,
                vol = voltage.name,
                i = i,
                n = n,
                tEl = typeEl,
                mecTyp = mechanismType,
                mecPer = mechanismPerformance,
                mecPr = mechanismPressure,
                mecN = mechanismN,
                mecH = mechanismH,
                mecPowN = mechanismPowerN,
                mecAddit = mechanismAdditionally,
                mecInfoId = mechanismInfoId,
                mecInfoName = mechanismInfoName
            )
        }
    }
}