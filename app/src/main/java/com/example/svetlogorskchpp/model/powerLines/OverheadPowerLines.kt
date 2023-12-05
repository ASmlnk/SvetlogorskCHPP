package com.example.svetlogorskchpp.model.powerLines

import com.example.svetlogorskchpp.model.transformerNeeds.T

class OverheadPowerLines {

    fun getAllPowerLines() = listPowerLines
    val listPowerLines = mutableListOf<PowerLines>()
    init {
        val sziv = PowerLines(
            name = PL.SZIV.get,
            nameORY = PL.ORY_110.get,
            numberCells = 1,
            numberSH = 1,
            numberGHY = "33, 129",
            apv = PL.APV_15.get,
            key1SR = 1,
            key2SR = 2,
            keyLR = 2,
            keyOR = 2,
            madeApv = PL.APV_KNNSH.get,
            earthProtection = PL.EARTH_PROTECTION_1.get,
            phaseProtection = PL.PHASE_PROTECTION_1.get
        )

        val zavodskai1 = PowerLines(
            name = PL.ZAVODSKAI1.get,
            nameORY = PL.ORY_110.get,
            numberCells = 2,
            numberSH = 1,
            numberGHY = "33a, 133",
            apv = PL.APV_Z.get,
            key1SR = 1,
            key2SR = 1,
            keyLR = 2,
            keyOR = 2,
            madeApv = PL.APV_Z.get,
            earthProtection = PL.EARTH_PROTECTION_4.get,
            phaseProtection = PL.PHASE_PROTECTION_4.get
        )

         val lavsan = PowerLines(
            name = PL.LAVSAN.get,
            nameORY = PL.ORY_110.get,
            numberCells = 3,
            numberSH = 2,
            numberGHY = "33, 131",
            apv = PL.APV_15.get,
            key1SR = 4,
            key2SR = 2,
            keyLR = 4,
            keyOR = 4,
             madeApv = PL.APV_KNNSH.get,
             earthProtection = PL.EARTH_PROTECTION_1.get,
             phaseProtection = PL.PHASE_PROTECTION_1.get
        )

         val zavodskai2 = PowerLines(
            name = PL.ZAVODSKAI2.get,
            nameORY = PL.ORY_110.get,
            numberCells = 4,
            numberSH = 2,
            numberGHY = "33a, 93",
            apv = PL.APV_Z.get,
            key1SR = 1,
            key2SR = 1,
            keyLR = 1,
            keyOR = 1,
             madeApv = PL.APV_Z.get,
             earthProtection = PL.EARTH_PROTECTION_4.get,
             phaseProtection = PL.PHASE_PROTECTION_4.get
        )

        val volodarsk = PowerLines(
            name = PL.VOLODARSK.get,
            nameORY = PL.ORY_110.get,
            numberCells = 5,
            numberSH = 1,
            numberGHY = "31, 136, 94, 95",
            apv = PL.APV_15_20.get,
            key1SR = 2,
            key2SR = 2,
            keyLR = 2,
            keyOR = 3,
            madeApv = PL.APV_KONSH.get+"\n"+ PL.APV_KNNSH.get,
            earthProtection = PL.EARTH_PROTECTION_2.get,
            phaseProtection = PL.PHASE_PROTECTION_2.get
        )

        val zavodskai3 = PowerLines(
            name = PL.ZAVODSKAI3.get,
            nameORY = PL.ORY_110.get,
            numberCells = 8,
            numberSH = 1,
            numberGHY = "31, 98",
            apv = PL.APV_Z.get,
            key1SR = 1,
            key2SR = 2,
            keyLR = 2,
            keyOR = 4,
            madeApv = PL.APV_Z.get,
            earthProtection = PL.EARTH_PROTECTION_4.get,
            phaseProtection = PL.PHASE_PROTECTION_4.get
        )

        val zabrodie = PowerLines(
            name = PL.ZABRODIE.get,
            nameORY = PL.ORY_110.get,
            numberCells = 10,
            numberSH = 1,
            numberGHY = "29, 128",
            apv = PL.APV_2_20.get,
            key1SR = 1,
            key2SR = 1,
            keyLR = 4,
            keyOR = 4,
            madeApv = PL.APV_KNNSH.get,
            earthProtection = PL.EARTH_PROTECTION_1.get,
            phaseProtection = PL.PHASE_PROTECTION_1.get
        )

        val ovv = PowerLines(
            name = PL.OVV110.get,
            nameORY = PL.ORY_110.get,
            numberCells = 13,
            numberSH = 2,
            numberGHY = "32, 124, 126",
            apv = "",
            key1SR = 2,
            key2SR = 1,
            keyLR = 0,
            keyOR = 0
        )

        val parichi = PowerLines(
            name = PL.PARICHI.get,
            nameORY = PL.ORY_110.get,
            numberCells = 14,
            numberSH = 2,
            numberGHY = "29, 121",
            apv = PL.APV_2_20.get,
            key1SR = 1,
            key2SR = 2,
            keyLR = 2,
            keyOR = 4,
            madeApv = PL.APV_KNNSH.get,
            earthProtection = PL.EARTH_PROTECTION_1.get,
            phaseProtection = PL.PHASE_PROTECTION_1.get
        )

        val shsv = PowerLines(
            name = PL.SHSV1.get,
            nameORY = PL.ORY_110.get,
            numberCells = 15,
            numberSH = 2,
            numberGHY = "5, 108, 109",
            apv = PL.APV_15.get,
            key1SR = 1,
            key2SR = 1,
            keyLR = 0,
            keyOR = 3,
            madeApv = PL.APV_KS.get
        )

        val kozlovichi = PowerLines(
            name = PL.KOZLOVICHI.get,
            nameORY = PL.ORY_110.get,
            numberCells = 16,
            numberSH = 2,
            numberGHY = "30, 99, 100, 101",
            apv = PL.APV_K.get,
            key1SR = 1,
            key2SR = 1,
            keyLR = 4,
            keyOR = 4,
            madeApv = PL.APV_KS.get+"\n"+ PL.APV_KONL_KNNSH.get+"\n"+ PL.APV_KONSH_KNNL.get,
            earthProtection = PL.EARTH_PROTECTION_3.get,
            phaseProtection = PL.PHASE_PROTECTION_3.get
        )

        val svetlogorsk110 = PowerLines(
            name = PL.SVETLOGORSK110.get,
            nameORY = PL.ORY_110.get,
            numberCells = 17,
            numberSH = 1,
            numberGHY = "30, 130",
            apv = PL.APV_2.get,
            key1SR = 3,
            key2SR = 1,
            keyLR = 2,
            keyOR = 2,
            madeApv = PL.APV_KNNSH.get,
            earthProtection = PL.EARTH_PROTECTION_1.get,
            phaseProtection = PL.PHASE_PROTECTION_1.get
        )

        val svetlogorsk220 = PowerLines(
            name = PL.SVETLOGORSK220.get,
            nameORY = PL.ORY_110.get,
            numberCells = 18,
            numberSH = 2,
            numberGHY = "32, 122, 123",
            apv = "",
            key1SR = 3,
            key2SR = 1,
            keyLR = 1,
            keyOR = 4,
            earthProtection = PL.EARTH_PROTECTION_3.get,
            phaseProtection = PL.PHASE_PROTECTION_2.get
        )

        val tn1Sh110 = PowerLines(
            name = PL.TN110_1.get,
            nameORY = PL.ORY_110.get,
            numberCells = 17,
            numberSH = 1,
            numberGHY = "",
            key1SR = 4,
            key2SR = 0,
            keyLR = 0,
            keyOR = 0
        )

        val tn2Sh110 = PowerLines(
            name = PL.TN110_2.get,
            nameORY = PL.ORY_110.get,
            numberCells = 11,
            numberSH = 2,
            numberGHY = "",
            key1SR = 0,
            key2SR = 3,
            keyLR = 0,
            keyOR = 0
        )

        val tnOsh110 = PowerLines(
            name = PL.TN110_OSH.get,
            nameORY = PL.ORY_110.get,
            numberCells = 19,
            numberSH = 2,
            numberGHY = "",
            key1SR = 0,
            key2SR = 0,
            keyLR = 0,
            keyOR = 2
        )

        val opn110 = PowerLines(
            name = PL.OPN1_110.get,
            nameORY = PL.ORY_110.get,
            numberCells = 13,
            numberSH = 1,
            numberGHY = "",
            key1SR = 3,
            key2SR = 0,
            keyLR = 0,
            keyOR = 0
        )

        val tn220 = PowerLines(
            name = PL.TN220.get,
            nameORY = PL.ORY_220.get,
            numberCells = 1,
            numberSH = 2,
            numberGHY = "",
            key1SR = 2,
            key2SR = 2,
            keyLR = 0,
            keyOR = 0
        )

        val miradino = PowerLines(
            name = PL.MIRADINO.get,
            nameORY = PL.ORY_220.get,
            numberCells = 2,
            numberSH = 1,
            numberGHY = "28, 57, 58",
            apv = PL.APV_25.get,
            key1SR = 2,
            key2SR = 3,
            keyLR = 3,
            keyOR = 3,
            madeApv = PL.APV_KS.get+"\n"+ PL.APV_KONSH.get,
            earthProtection = PL.EARTH_PROTECTION_5.get,
            phaseProtection = PL.PHASE_PROTECTION_5.get
        )

        val svetlogorsk = PowerLines(
            name = PL.SVETLOGORSK.get,
            nameORY = PL.ORY_220.get,
            numberCells = 4,
            numberSH = 2,
            numberGHY = "28, 138, 140, 141",
            apv = PL.APV_2.get,
            key1SR = 5,
            key2SR = 0,
            keyLR = 3,
            keyOR = 3,
            madeApv = PL.APV_KS.get+"\n"+ PL.APV_KONSH.get+"\n"+ PL.APV_KONL.get,
            earthProtection = PL.EARTH_PROTECTION_3.get,
            phaseProtection = PL.PHASE_PROTECTION_6.get
        )

        val shsv220 = PowerLines(
            name = PL.SHSV2.get,
            nameORY = PL.ORY_220.get,
            numberCells = 6,
            numberSH = 2,
            numberGHY = "7, 105, 106",
            apv = PL.APV_2.get,
            key1SR = 0,
            key2SR = 0,
            keyLR = 0,
            keyOR = 0,
            madeApv = PL.APV_KS.get,
        )

        val ovv220 = PowerLines(
            name = PL.OVV220.get,
            nameORY = PL.ORY_220.get,
            numberCells = 7,
            numberSH = 2,
            numberGHY = "27, 125, 127",
            apv = "",
            key1SR = 0,
            key2SR = 0,
            keyLR = 0,
            keyOR = 0,
            madeApv = PL.APV_KS.get,
        )

        val t1 = PowerLines(
            name = PL.T_1.get,
            nameORY = PL.ORY_110.get,
            numberCells = 7,
            numberSH = 1,
            numberGHY = "3, 35",
            apv = "",
            key1SR = 4,
            key2SR = 1,
            keyLR = 4,
            keyOR = 0,
            earthProtection = T.TZNP.get,
            phaseProtection = T.DFZ.get +"\n"+ T.G_Z.get
        )

        val t2 = PowerLines(
            name = PL.T_2.get,
            nameORY = PL.ORY_110.get,
            numberCells = 11,
            numberSH = 2,
            numberGHY = "4, 36, 37",
            apv = "",
            key1SR = 1,
            key2SR = 1,
            keyLR = 3,
            keyOR = 0,
            earthProtection = T.TZNP.get,
            phaseProtection = T.DFZ.get +"\n"+ T.G_Z.get
        )

        val t3 = PowerLines(
            name = PL.T_3_110.get,
            nameORY = PL.ORY_110.get,
            numberCells = 20,
            numberSH = 1,
            numberGHY = "9, 41, 42, 87-92",
            apv = "",
            key1SR = 1,
            key2SR = 2,
            keyLR = 1,
            keyOR = 0,
            earthProtection = T.NTZNP.get + " I, II, III",
            phaseProtection = T.DFZ.get +"\n"+ T.G_Z.get+"\n"+  T.DZ.get,
            madeApv = PL.APV_KONSH.get
        )

        val t32 = PowerLines(
            name = PL.T_3_220.get,
            nameORY = PL.ORY_220.get,
            numberCells = 3,
            numberSH = 1,
            numberGHY = "9, 41, 42, 87-92",
            apv = "",
            key1SR = 0,
            key2SR = 0,
            keyLR = 0,
            keyOR = 0,
            earthProtection = T.NTZNP.get + " I, II, III",
            phaseProtection = T.DFZ.get +"\n"+ T.G_Z.get+"\n"+  T.DZ.get,
            madeApv = PL.APV_KONSH.get
        )

        val t4 = PowerLines(
            name = PL.T_4_110.get,
            nameORY = PL.ORY_110.get,
            numberCells = 19,
            numberSH = 2,
            numberGHY = "11, 43-45, 65",
            apv = "",
            key1SR = 4,
            key2SR = 2,
            keyLR = 1,
            keyOR = 0,
            earthProtection = T.NTZNP.get + " I, II",
            phaseProtection = T.DFZ.get +"\n"+ T.G_Z.get+"\n"+  T.DZ.get,
            madeApv = PL.APV_KONSH.get
        )

        val t42 = PowerLines(
            name = PL.T_4_220.get,
            nameORY = PL.ORY_220.get,
            numberCells = 5,
            numberSH = 2,
            numberGHY = "11, 43-45, 65",
            apv = "",
            key1SR = 0,
            key2SR = 0,
            keyLR = 0,
            keyOR = 0,
            earthProtection = T.NTZNP.get + " I, II",
            phaseProtection = T.DFZ.get +"\n"+ T.G_Z.get+"\n"+  T.NMTZ.get,
            madeApv = PL.APV_KONSH.get
        )

        val t5 = PowerLines(
            name = PL.T_5.get,
            nameORY = PL.ORY_110.get,
            numberCells = 21,
            numberSH = 1,
            numberGHY = "12. Пристройка ГЩУ: 1, 2, 3, 4",
            apv = "",
            key1SR = 1,
            key2SR = 1,
            keyLR = 0,
            keyOR = 0,
            earthProtection = T.TZNP.get+ " I, II, III",
            phaseProtection = T.DFZ.get +"\n"+ T.G_Z.get
        )

        val t6 = PowerLines(
            name = PL.T_6.get,
            nameORY = PL.ORY_220.get,
            numberCells = 8,
            numberSH = 2,
            numberGHY = "13. Пристройка ГЩУ: 5, 6, 7, 8",
            apv = "",
            key1SR = 3,
            key2SR = 3,
            keyLR = 3,
            keyOR = 3,
            earthProtection = T.TZNP.get,
            phaseProtection = T.DFZ.get +"\n"+ T.G_Z.get
        )

        val t20 = PowerLines(
            name = PL.T_20.get,
            nameORY = PL.ORY_110.get,
            numberCells = 9,
            numberSH = 1,
            numberGHY = "18, 75",
            apv = "",
            key1SR = 1,
            key2SR = 1,
            keyLR = 0,
            keyOR = 0,
            earthProtection = "",
            phaseProtection = T.DFZ.get +"\n"+ T.G_Z.get+"\n"+ T.MTZ.get
        )





        val listPower = listOf<PowerLines>(
            sziv,
            zavodskai1,
            lavsan,
            zavodskai2,
            volodarsk,
            zavodskai3,
            zabrodie,
            ovv,
            parichi,
            shsv,
            kozlovichi,
            svetlogorsk110,
            svetlogorsk220,
            tn1Sh110,
            tn2Sh110,
            tnOsh110,
            opn110,
            tn220,
            miradino,
            svetlogorsk,
            shsv220,
            ovv220,
            t1,
            t2,
            t3,
            t32,
            t4,
            t42,
            t5,
            t6,
            t20
        )
        listPowerLines.addAll(listPower)

    }



}