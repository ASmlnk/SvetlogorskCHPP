package com.example.svetlogorskchpp.__domain.mapper.electrical_equipment

import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElAssembly
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.Switchgear
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class SwitchgearMapper @Inject constructor() {

    fun toSwitchgear(switchgearEntity: SwitchgearEntity): Switchgear {
        return with(switchgearEntity) {
            Switchgear(
                id = id,
                name = nam,
                typeSwitch = tSw,
                typeInsTr = tIT,
                additionally = addit,
                category = cat.let { ElAssembly.valueOf(it) },
                nameDepartment = namDep.let { NameDepartment.valueOf(it) },
                voltage = vol.let { Voltage.valueOf(it) },
                automation = aut,
                phaseProtection = if (phPr.isEmpty()) Json.decodeFromString("[]") else Json.decodeFromString(phPr),
                earthProtection = if (eaPr.isEmpty()) Json.decodeFromString("[]") else Json.decodeFromString(eaPr),
                additionallyRza = addRz,
                info = inf,
                powerSupplyId1 = powSuId1,
                powerSupplyCell1 = powSuC1,
                powerSupplyName1 = powSuNam1,
                powerSupplyId2 = powSuId2,
                powerSupplyCell2 = powSuC2,
                powerSupplyName2 = powSuNam2,
                powerSupplyReserveId1 = powSuRId1,
                powerSupplyReserveCell1 = powSuRC1,
                powerSupplyReserveName1 = powSuRNam1,
                powerSupplyReserveId2 = powSuRId2,
                powerSupplyReserveCell2 = powSuRC2,
                powerSupplyReserveName2 = powSuRNam2,
                powerSupplyReserveId3 = powSuRId3,
                powerSupplyReserveCell3 = powSuRC3,
                powerSupplyReserveName3 = powSuRNam3
            )
        }
    }

    fun toSwitchgearEntity (switchgear: Switchgear): SwitchgearEntity {
        return with(switchgear) {
            SwitchgearEntity(
                id = id,
                nam = name,
                tSw = typeSwitch,
                tIT = typeInsTr,
                addit = additionally,
                cat = category.name,
                namDep = nameDepartment.name,
                vol = voltage.name,
                aut = automation,
                phPr = Json.encodeToString(phaseProtection),
                eaPr = Json.encodeToString(earthProtection),
                addRz = additionallyRza,
                inf = info,
                powSuId1 = powerSupplyId1,
                powSuC1 = powerSupplyCell1,
                powSuNam1 = powerSupplyName1,
                powSuId2 = powerSupplyId2,
                powSuC2 = powerSupplyCell2,
                powSuNam2 = powerSupplyName2,
                powSuRId1 = powerSupplyReserveId1,
                powSuRC1 = powerSupplyReserveCell1,
                powSuRNam1 = powerSupplyReserveName1,
                powSuRId2 = powerSupplyReserveId2,
                powSuRC2 = powerSupplyReserveCell2,
                powSuRNam2 = powerSupplyReserveName2,
                powSuRId3 = powerSupplyReserveId3,
                powSuRC3 = powerSupplyReserveCell3,
                powSuRNam3 = powerSupplyReserveName3
            )
        }
    }

}