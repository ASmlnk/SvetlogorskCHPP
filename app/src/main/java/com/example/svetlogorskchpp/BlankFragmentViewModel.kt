package com.example.svetlogorskchpp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.svetlogorskchpp.model.ElectricalAssemblyFirebase
import com.example.svetlogorskchpp.model.electricMotor.ElectricMotor
import com.example.svetlogorskchpp.model.firebase.FirestoreRepository
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class BlankFragmentViewModel @Inject constructor(
    private val data: FirestoreRepository,
): ViewModel() {

    //val data = FirestoreRepository.get()
    val listData = mutableListOf<ElectricMotor>()
    val liveData = MutableLiveData<Int>()
    val listDataFilter = mutableListOf<ElectricMotor>()

    fun get() {
        val dataKa = data.getAll("Остальное").get()
        viewModelScope.launch {
            val listKa = dataKa.await()
            for (it in listKa) {
                listData.add(it.toObject<ElectricMotor>().apply { id = it.id })
            }
            /*listDataFilter.apply {
                 addAll(listData.filter { it.voltage == "6000" })
             }*/
            liveData.value = listData.size

        }
    }

    fun set() {


        listData.forEach {
            // val dataKa = data.getAll("Остальное").get()
            val p = it.characteristics.split("\\n").first()
            val schema = it.characteristics.split("\\n").last()
            val i = it.characteristics.split("\\n")[2]

            val dataEM = HashMap<String, Any>()
            dataEM["schema"] = schema
            dataEM["p"] = p
            dataEM["i"] = i

            data.remoteDB.collection("Остальное").document(it.id)
                .set(dataEM, SetOptions.merge())
        }
    }

    fun setNewEm() {

        val em104 = ElectricMotor(
            name = "Эксгаустер ТГ-1",
            characteristics = "P = 0,55 кВт\nU = 380 В\nI = 1,61 А\nАИМ-71А-4У2\nЗв\nn = 1350 об/мин",
            category = "ТГ-1",
            schema = "Сб. №2 ТГ-1",
            voltage = "380",
            i = "I = 1,61 А",
            p = "P = 0,55 кВт ",
            generalCategory = "ТГ",
            indexSection = "Р-",
            pump = ""
        )

        val em106 = ElectricMotor(
            name = "Эксгаустер 3А,Б",
            characteristics = "P = 1,5 кВт\nU = 380 В\nI = 3,3 А\nАИМ-71-А4\nЗв\nn = 2850 об/мин",
            category = "ТГ-3",
            schema = "Сб. тир. возб. ТГ-3",
            voltage = "380",
            i = "I = 3,3 А",
            p = "P = 1,5 кВт ",
            generalCategory = "ТГ",
            indexSection = "Р-",
            pump = ""
        )
        val em107 = ElectricMotor(
            name = "Эксгаустер ТГ4",
            characteristics = "P = 0,55 кВт\nU = 380 В\nI = 1,3 А\nАИМ-63-В242\nЗв\nn = 2700 об/мин",
            category = "ТГ-4",
            schema = "Сб. №2 ТГ-4",
            voltage = "380",
            i = "I = 1,3 А",
            p = "P = 0,55 кВт ",
            generalCategory = "ТГ",
            indexSection = "Р-",
            pump = ""
        )
        val em108 = ElectricMotor(
            name = "Эксгаутер 5А, 5Б",
            characteristics = "P = 1,5 кВт\nU = 380 В\nI = 3,3 А\nАИР80А2У3\nЗв\nn = 2950 об/мин",
            category = "ТГ-5",
            schema = "Сб тир. возб. ТГ-3",
            voltage = "380",
            i = "I = 3,3 А",
            p = "P = 1,5 кВт ",
            generalCategory = "ТГ",
            indexSection = "Р-",
            pump = ""
        )

        val dataList = listOf<ElectricMotor>(
            em104,
            em106,
            em107,
            em108
        )
        dataList.forEach {
            val dataElement = HashMap<String, Any>()
            dataElement["name"] = it.name
            dataElement["category"] = it.category
            dataElement["schemaState"] = false
            dataElement["characteristics"] = it.characteristics
            dataElement["rep"] = false
            dataElement["responsibly"] = false
            dataElement["voltage"] = it.voltage
            dataElement["generalCategory"] = it.generalCategory
            dataElement["indexSection"] = it.indexSection
            dataElement["schema"] = it.schema
            dataElement["p"] = it.p
            dataElement["i"] = it.i
            dataElement["pump"] = it.pump

            data.remoteDB.collection("Турбогенераторы")
                .add(dataElement)
        }
    }

    fun setNewElectricalAssembly() {
        val ea1 = ElectricalAssemblyFirebase(	nameAssembly = "КРУ-3,15 кВ 1С",
            nameDepartment = "КРУ",
            inputAssembly = "Раб. пит: яч.26, 28, 30\nРез. пит: яч.25, 27, 29",
            category = "Сборка",
            listItemAssembly = mapOf(
                "НРВ-1    (2А)" to true,
                "65Т    (2)" to false,
                "НДВ-5    (10)" to true,
                "41Т    (14)" to false,
                "РВ-1    (16)" to true,
                "ПМН-1    (18)" to true,
                "40Т    (22)" to false,
                "ДВ-1А    (3)" to true,
                "ДВ-1Б    (19)" to true,
                "Д-1А    (5)" to true,
                "Д-1Б    (15)" to true,
                "ВГДН-1    (7)" to true,))


                val ea2 = ElectricalAssemblyFirebase(	nameAssembly = "КРУ-3,15 кВ 3С",
            nameDepartment = "КРУ",
            inputAssembly = "Раб. пит: яч.68, 66, 64\nРез. пит: яч.69, 67, 65",
            category = "Сборка",
            listItemAssembly = mapOf(
                "43Т    (70)" to false,
                "60Т    (74)" to false,
                "ПЭН-3    (78)" to true,
                "КН-3А    (80)" to true,
                "ПМН-3    (84)" to true,
                "НРВ-3    (86)" to true,
                "МН-3    (79)" to true,))


                val ea3 = ElectricalAssemblyFirebase(	nameAssembly = "КРУ-3,15 кВ 4С",
            nameDepartment = "КРУ",
            inputAssembly = "Раб. пит: яч. 108, 110, 112\nРез. пит: яч. 107, 109, 111",
            category = "Сборка",
            listItemAssembly = mapOf(
                "ПЭН-4    (104)" to true,
                "КН-4А    (102)" to true,
                "КН-3Б    (98)" to true,
                "66Т    (94)" to false,
                "69Т    (92)" to false,
                "46Т    (97)" to false,
                "МН-1    (99)" to true,))


                val ea4 = ElectricalAssemblyFirebase(	nameAssembly = "КРУ-3,15 кВ 5С",
            nameDepartment = "КРУ",
            inputAssembly = "Раб. пит: яч. 114, 116, 118\nРез. пит: яч. 113, 115, 117",
            category = "Сборка",
            listItemAssembly = mapOf(
                "ПЭН-6    (122)" to true,
                "КН-4Б    (124)" to true,
                "ПМН-4    (126)" to true,
                "63Т    (132)" to false,
                "СМ-1    (134)" to true,
                "42Т    (127)" to false,))


                val ea5 = ElectricalAssemblyFirebase(	nameAssembly = "КРУ-3,15 кВ 6С",
            nameDepartment = "КРУ",
            inputAssembly = "Раб. пит: яч. 164, 166, 168\nРез. пит: яч. 163, 165, 167",
            category = "Сборка",
            listItemAssembly = mapOf(
                "ПЭН-7    (142)" to true,
                "ПН-5    (150)" to true,
                "ПМН-5    (156)" to true,
                "РВ-2    (158)" to true,
                "44Т    (161)" to false,
                "МН-2    (153)" to true,
                "ДВ-6АI    (143)" to true,
                "ДВ-6АII    (145)" to true,
                "ДВ-6БI    (157)" to true,
                "ДВ-6БII    (159)" to true,
                "Д-6А    (147)" to true,
                "Д-6Б    (155)" to true,
                "ВГДН-6    (149)" to true,))


                val ea6 = ElectricalAssemblyFirebase(	nameAssembly = "КРУ-3,15 кВ 7С",
            nameDepartment = "КРУ",
            inputAssembly = "Раб. пит: яч. 172, 170\nРез. пит: яч. 171, 169",
            category = "Сборка",
            listItemAssembly = mapOf(
                "ДВ-7АI    (173)" to true,
                "ДВ-7АII    (175)" to true,
                "ДВ-7БI    (187)" to true,
                "ДВ-7БII    (189)" to true,
                "Д-7А    (177)" to true,
                "Д-7Б    (185)" to true,
                "ВГДН-7    (181)" to true,
                "50Т    (176)" to false,
                "70Т    (186)" to false,
                "СМ-3    (182)" to true,
                "ПЭН-8    (190)" to true,
                "СН-4    (183)" to true,))


                val ea7 = ElectricalAssemblyFirebase(	nameAssembly = "КРУ-3,15 кВ 8С",
            nameDepartment = "КРУ",
            inputAssembly = "Раб. пит: яч. 222, 224\nРез. пит: яч. 221, 223",
            category = "Сборка",
            listItemAssembly = mapOf(
                "ДВ-8АI    (203)" to true,
                "ДВ-8АII    (205)" to true,
                "ДВ-8БI    (217)" to true,
                "ДВ-8БII    (219)" to true,
                "Д-8А    (207)" to true,
                "Д-8Б    (215)" to true,
                "ВГДН-8    (209)" to true,
                "68Т    (210)" to false,
                "ПЭН-10    (212)" to true,
                "СН-3    (216)" to true,
                "45Т    (218)" to false,
                "МН-4    (211)" to true,
                "Т-БЭРН    (213)" to false,))


                val ea8 = ElectricalAssemblyFirebase(	nameAssembly = "КРУ-3,15 кВ 9С",
            nameDepartment = "КРУ",
            inputAssembly = "Раб. пит: яч. 228, 226\nРез. пит: яч. 227, 225",
            category = "Сборка",
            listItemAssembly = mapOf(
                "ДВ-9АI    (229)" to true,
                "ДВ-9АII    (231)" to true,
                "ДВ-9БI    (243)" to true,
                "ДВ-9БII    (245)" to true,
                "Д-9А    (233)" to true,
                "Д-9Б    (241)" to true,
                "ВГДН-9    (235)" to true,
                "ПН-6    (232)" to true,
                "ПЭН-11    (238)" to true,
                "47Т    (237)" to false,))



                val ea9 = ElectricalAssemblyFirebase(	nameAssembly = "КРУ-6,3 кВ 1С",
            nameDepartment = "КРУ",
            inputAssembly = "Раб. пит: яч. 15\nРез. пит: яч. 13",
            category = "Сборка",
            listItemAssembly = mapOf(
                "НКП-1    (18)" to true,
                "71Т    (17)" to false,
                "ЦН-1    (16)" to true,
                "СН-6    (12)" to true,
                "СН-7    (10)" to true,
                "СН-8    (11)" to true,))


                val ea10 = ElectricalAssemblyFirebase(	nameAssembly = "КРУ-6,3 кВ 2С",
            nameDepartment = "КРУ",
            inputAssembly = "Раб. пит: яч. 35\nРез. пит: яч. 32",
            category = "Сборка",
            listItemAssembly = mapOf(
                "ЦН-2    (31)" to true,
                "СН-1    (29)" to true,
                "ПЖН-2    (28)" to true,
                "НКП-2    (27)" to true,
                "СН-5    (24)" to true,
                "72Т    (22)" to false,
                "СН-9    (21)" to true,))


                val ea11 = ElectricalAssemblyFirebase(	nameAssembly = "КРУ-6,3 кВ 3С",
            nameDepartment = "КРУ",
            inputAssembly = "Раб. пит: яч. 45\nРез. пит: яч. 44",
            category = "Сборка",
            listItemAssembly = mapOf(
                "ЦН-3    (48)" to true,
                "ЦН-4    (49)" to true,
                "ПМН-6    (50)" to true,
                "ПЖН-1    (51)" to true,))







                val dataList = listOf<ElectricalAssemblyFirebase>(
            ea1, ea2 ,ea3 ,ea4 ,ea5 ,ea6 ,ea7 ,ea8 ,ea9 ,ea10 ,ea11 /*,ea12 ,ea13 ,ea14 ,ea15 ,ea16,
            ea17 ,ea18 ,ea19 ,ea20 ,ea21 ,ea22 ,ea23 ,ea24 ,ea25 ,ea26 ,ea27 ,ea28 ,ea29 ,ea30 ,ea31 ,ea32 ,*/
        )
        data.remoteDB.enableNetwork()
        dataList.forEach {
            val dataElement = HashMap<String, Any>()
            dataElement["nameAssembly"] = it.nameAssembly
            dataElement["inputAssembly"] = it.inputAssembly
            dataElement["nameDepartment"] = it.nameDepartment
            dataElement["category"] = it.category
            dataElement["listItemAssembly"] = it.listItemAssembly

            data.remoteDB.collection("Сборки")
                .add(dataElement)
        }


    }


    fun setNewElement(nameChapter: String, dataElement: HashMap<String, Any>) {
        data.remoteDB.collection("$nameChapter")
            .add(dataElement)
    }
}