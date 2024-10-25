package com.example.svetlogorskchpp.__domain.mapper.electrical_equipment.open_switchgear

import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.KeyOry
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.VoltageOry
import com.example.svetlogorskchpp.__domain.model.electrical_equipment.OpenSwitchgearVl
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class OpenSwitchgearVlMapper @Inject constructor() {

    fun toOpenSwitchgearVl(openSwitchgearVlEntity: OpenSwitchgearVlEntity): OpenSwitchgearVl {
        return with(openSwitchgearVlEntity) {
            OpenSwitchgearVl(
                id =id,
                name=name,
                panelMcp=panelMcp,
                bysSystem=bysSystem,
                cell=cell,
                voltage = voltage.let { VoltageOry.valueOf(it) }?: VoltageOry.KV,
                isTransit=isTransit,
                isVl=isVl,
                typeSwitch=typeSwitch,
                typeInsTr=typeInsTr,
                automation=automation,
                apv=apv,
                keyShr1=keyShr1.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                keyShr2=keyShr2.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                keyLr=keyLr.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                keyOr=keyOr.let { KeyOry.valueOf(it) }?: KeyOry.KEY_0,
                phaseProtection=Json.decodeFromString(phaseProtection),
                earthProtection=Json.decodeFromString(earthProtection),
            )
        }
    }

    fun toOpenSwitchgearVlEntity(openSwitchgearVl: OpenSwitchgearVl): OpenSwitchgearVlEntity {
        return with(openSwitchgearVl) {
            OpenSwitchgearVlEntity(
                id =id,
                name=name,
                panelMcp=panelMcp,
                bysSystem=bysSystem,
                cell=cell,
                voltage = voltage.name,
                isTransit=isTransit,
                isVl=isVl,
                typeSwitch=typeSwitch,
                typeInsTr=typeInsTr,
                automation=automation,
                apv=apv,
                keyShr1=keyShr1.name,
                keyShr2=keyShr2.name,
                keyLr=keyLr.name,
                keyOr=keyOr.name,
                phaseProtection= Json.encodeToString(phaseProtection),
                earthProtection=Json.encodeToString(earthProtection),
            )
        }
    }
}