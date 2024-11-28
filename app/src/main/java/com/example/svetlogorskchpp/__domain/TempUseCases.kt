package com.example.svetlogorskchpp.__domain

import android.util.Log
import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.ElMotorRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.LightingAndOtherRepositoryImpl
import com.example.svetlogorskchpp.__data.repository.equipment.electrical.SwitchgearRepositoryImpl
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.ElGeneralCategory
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.NameDepartment
import com.example.svetlogorskchpp.__domain.en.electrical_equipment.Voltage
import com.example.svetlogorskchpp.model.ElectricalAssemblyFirebase
import com.example.svetlogorskchpp.model.ElectricalAssemblySearch
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor
import com.example.svetlogorskchpp.model.firebase.ElectricMotorType
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.gson.Gson
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class TempUseCases @Inject constructor(
    private val elRepository: ElMotorRepositoryImpl,
    private val swRepository: SwitchgearRepositoryImpl,
    private val liRepository: LightingAndOtherRepositoryImpl,
    private val firebase: FirebaseFirestore,
    private val gson: Gson,
) {
    suspend fun download() {
        firebase.enableNetwork()
        val regex = "\\s*\\(\\d+\\)".toRegex()
        val listAssembly = mutableListOf<ElectricalAssemblyFirebase>()  //Сборки из ФБ
        val data = firebase.collection(ElectricMotorType.ASSEMBLY.nameCategoryFirebase).get()
        val listData = data.await()
        for (it in listData) {
            val electricalAssemblyFirebase =
                it.toObject<ElectricalAssemblyFirebase>().apply { id = it.id }
            listAssembly.add(electricalAssemblyFirebase)
        }

        val listElementAssembly = mutableListOf<ElectricalAssemblySearch>() // элементы из сборок

        for (electricalAssembly in listAssembly) {
            val listItem = electricalAssembly.listItemAssembly   //lowercase().indexOf(text)
            for ((i, b) in listItem) {
                val index = if (b) 1 else 0
                listElementAssembly.add(
                    ElectricalAssemblySearch(
                        name = i,
                        index = index,
                        electricalAssembly = electricalAssembly
                    )
                )
            }
        }

        val listElectricMotor = mutableListOf<ElectricMotor>()   //эл двигатели из ФБ


        listElectricMotor.apply {
            addAll(getListCollection(ElectricMotorType.BOILESRS.nameCategoryFirebase))
            addAll(getListCollection(ElectricMotorType.TURBOGENERATORS.nameCategoryFirebase))
            addAll(getListCollection(ElectricMotorType.ANOTHER.nameCategoryFirebase))
        }.map { it.name.replace(regex, "").trim() }

        val elmotor =
            listElementAssembly.filter { it.index == 1 }//.map { it.name.replace(regex, "").trim() }  //
        val other =
            listElementAssembly.filter { it.index == 0 }//.map { it.name.replace(regex, "").trim() }
        val switchgearLocale = swRepository.getAll()

        val elMotorEntitys = mutableListOf<ElMotorEntity>()
        var iii = 0
        var ps = 0


        elmotor.forEach { el ->
            val nameEl = el.name.replace(regex, "").trim()
            val elMotorFB = listElectricMotor.find { it.name == nameEl }
            if (elMotorFB != null) iii += 1
            val powerSupply = switchgearLocale.find { it.nam == el.electricalAssembly.nameAssembly }
            if (powerSupply != null) ps++
            val elChar = elMotorFB?.characteristics?.trimIndent()?.lines()
            // val pump = elMotorFB?.pump?.trimIndent()?.lines()
            val pump =
                elMotorFB?.pump?.replace("\\n", System.getProperty("line.separator"))?.split("\n")
                    ?: emptyList()

            val powSuId = powerSupply?.id ?: ""
            val powSuC = ""
            val powSuNam = powerSupply?.nam ?: ""
            val cat = toCategory(elMotorFB?.category)
            val gCat = toGeneralCategory(elMotorFB?.generalCategory)
            val tSw = elChar?.getOrNull(4)?.trim() ?: ""
            val isRep = elMotorFB?.rep ?: false
            val powEl = elMotorFB?.p?.substringAfter("P = ")?.substringBefore(" кВт")?.trim() ?: ""
            val vol = toVoltage(elMotorFB?.voltage)
            val i = elMotorFB?.i?.substringAfter("I = ")?.substringBefore(" А")?.trim() ?: ""
            val n = elChar?.getOrNull(5)?.substringAfter("n = ")?.substringBefore(" об/мин")?.trim()
                ?: ""
            val tEl = elChar?.getOrNull(3)?.trim() ?: ""
            val mecTyp = pump.getOrNull(0)?.trim() ?: ""
            val mecPer = toInt(pump.getOrNull(1) ?: "").trim()
            val mecPr = toInt(pump.getOrNull(2) ?: "").trim()
            val mecN = toInt(pump.getOrNull(3) ?: "").trim()


            val e = ElMotorEntity(
                id = UUID.randomUUID().toString(),
                nam = nameEl,
                powSuId = powSuId,
                powSuC = powSuC,
                powSuNam = powSuNam,
                automation = "",
                phPr = "",
                eaPr = "",
                addRz = "",
                cat = cat,
                gCat = gCat,
                tSw = tSw,
                tIT = "",
                addit = "",
                isRep = isRep,
                tRep = "",
                conCir = "",
                powEl = powEl,
                vol = vol,
                i = i,
                n = n,
                tEl = tEl,
                mecTyp = mecTyp,
                mecPer = mecPer,
                mecPr = mecPr,
                mecN = mecN,
                mecH = TODO(),
                mecPowN = TODO(),
                mecAddit = TODO(),
            )
            elMotorEntitys.add(e)
        }

        val lightEntitys = mutableListOf<LightingAndOtherEntity>()
        var li = 0
        var n = 0

        other.forEach { el ->
            val powerSupply = switchgearLocale.find { it.nam == el.electricalAssembly.nameAssembly }

            val powSuId = powerSupply?.id ?: ""
            val powSuC = ""
            val powSuNam = powerSupply?.nam ?: ""
            val isLi = findWordsByPrefix(el.name, "осв")
            if (isLi) li++
            n++
            val e = LightingAndOtherEntity(
                id = UUID.randomUUID().toString(),
                nam = el.name,
                powSuId = powSuId,
                powSuC = powSuC,
                powSuNam = powSuNam,
                tSw = "",
                addit = "",
                isLi = isLi,
                loc = ""
            )
            if (!findWordsByPrefix(el.name, "вво") && !findWordsByPrefix(el.name,"сб.")&& !findWordsByPrefix(el.name,"сбо") && !findWordsByPrefix(el.name, "сил") ) lightEntitys.add(e)
        }

        liRepository.saveAllEquipment(lightEntitys)
        elRepository.saveAllEquipment(elMotorEntitys)


    }

    fun findWordsByPrefix(words: String, prefix: String): Boolean {
        return words.lowercase().startsWith(prefix, ignoreCase = true)
    }

    private fun toInt(string: String): String {
        val regex = "\\d+".toRegex()
        val match = regex.find(string)
        return match?.value ?: ""
    }

    private fun toNameDep(str: String): String {
        val enumCat = when (str) {
            "БНС" -> NameDepartment.BNS
            "Градирня" -> NameDepartment.COOLING_TOWER
            "КРУ" -> NameDepartment.KRY
            "КТЦ к/о" -> NameDepartment.KTC_KO
            "КТЦ т/о" -> NameDepartment.KTC_TO
            "РУ" -> NameDepartment.RY
            "ХЦ" -> NameDepartment.HC
            "Щит. блок" -> NameDepartment.SHIELD_BLOCK
            else -> NameDepartment.OTHER
        }
        return enumCat.name
    }

    private fun toGeneralCategory(str: String?): String {
        if (str == null) return ElGeneralCategory.OTHER.name
        val cat = when (str) {
            "ХОВ" -> ElGeneralCategory.HOV
            "КТЦ т/о" -> ElGeneralCategory.KTC_TO
            "КТЦ к/о" -> ElGeneralCategory.KTC_KO
            "ТУ" -> ElGeneralCategory.TY
            "ЭЦ" -> ElGeneralCategory.EC
            "К/А" -> ElGeneralCategory.KA
            "ТГ" -> ElGeneralCategory.RG
            else -> ElGeneralCategory.OTHER
        }
        return cat.name
    }

    private fun toCategory(str: String?): String {
        if (str == null) return ElCategory.OTHER.name
        val enumCat = when (str) {
            "Остальное" -> ElCategory.OTHER
            "Оч. сооружения" -> ElCategory.TREATMENT_FACILITIES
            "Обессоливание" -> ElCategory.DESALTING
            "БНТ" -> ElCategory.BNT
            "Багерная" -> ElCategory.Bagernaya
            "К/О" -> ElCategory.KO
            "КТЦ к/о" -> ElCategory.KTC_KO
            "КТЦ т/о" -> ElCategory.KTC_TO
            "НДВ" -> ElCategory.NDV
            "ПН" -> ElCategory.PN
            "ПЭН" -> ElCategory.PEN
            "Предочистка" -> ElCategory.PRETREATMENT
            "СН" -> ElCategory.SN
            "ТУ" -> ElCategory.TY
            "ЦН" -> ElCategory.CN
            "ЦЦР" -> ElCategory.CCR
            "Аммиачное х-во" -> ElCategory.AMMONIA
            "Гидрозийное х-во" -> ElCategory.GIDROZIYNOE
            "Кислотное х-во" -> ElCategory.ACIDIC
            "Коагулянтное х-во" -> ElCategory.COAGULANT
            "Насосная 1 оч. т/с" -> ElCategory.N_TS
            "Солевое х-во" -> ElCategory.SOVEVOE
            "Сх. известкового х-во" -> ElCategory.LIMESTONE
            "Сх. фосфатного х-во" -> ElCategory.PHOSPHATE
            "Узел НВК промывки" -> ElCategory.NVK
            "Щелочное х-во" -> ElCategory.ALKALINE
            "ТГ-1" -> ElCategory.TG_1
            "ТГ-3" -> ElCategory.TG_3
            "ТГ-4" -> ElCategory.TG_4
            "ТГ-5" -> ElCategory.TG_5
            "ТГ-6" -> ElCategory.TG_6
            "К/А-1" -> ElCategory.KA_1
            "К/А-6" -> ElCategory.KA_6
            "К/А-7" -> ElCategory.KA_7
            "К/А-8" -> ElCategory.KA_8
            "К/А-9" -> ElCategory.KA_9
            else -> ElCategory.OTHER
        }
        return enumCat.name
    }

    private fun toVoltage(str: String?): String {
        if (str == null) return Voltage.KV.name
        return when (str) {
            "3000" -> Voltage.KV_3
            "6000" -> Voltage.KV_6
            "380" -> Voltage.KV_380
            else -> Voltage.KV
        }.name
    }

    private suspend fun getListCollection(nameCollection: String): List<ElectricMotor> {
        val list = mutableListOf<ElectricMotor>()
        val data = firebase.collection(nameCollection).get()
        val listData = data.await()
        for (it in listData) {
            list.add(it.toObject<ElectricMotor>().apply { id = it.id })
        }
        return list
    }


}