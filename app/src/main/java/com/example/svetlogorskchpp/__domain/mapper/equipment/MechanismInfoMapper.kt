package com.example.svetlogorskchpp.__domain.mapper.equipment

import com.example.svetlogorskchpp.__data.database.equipment.mechanism_info.MechanismInfoEntity
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.model.equipment.MechanismInfo
import javax.inject.Inject

class MechanismInfoMapper @Inject constructor() {
    fun toMechanismInfo (mechanismInfoEntity: MechanismInfoEntity): MechanismInfo {
        return with(mechanismInfoEntity) {
            MechanismInfo(
                id = id,
                name = name,
                info = info,
                category = category.let { ElGeneralCategory.valueOf(it) },
                additionally = additionally,
                compositeMechanismId = genMechId,
                compositeMechanismName = genMechName
            )
        }
    }

    fun toMechanismInfoEntity (mechanismInfo: MechanismInfo): MechanismInfoEntity {
        return with(mechanismInfo) {
            MechanismInfoEntity(
                id = id,
                name = name,
                info = info,
                category = category.name,
                additionally = additionally,
                genMechId = compositeMechanismId,
                genMechName = compositeMechanismName
            )
        }
    }
}