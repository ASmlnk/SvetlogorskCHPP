package com.example.svetlogorskchpp.model.transformerNeeds

import com.example.svetlogorskchpp.model.powerLines.PowerLines

class TsnData {

    val listTsn = mutableListOf<Tsn>()

    init {
        val t41 = Tsn(
            nameTsn = "41T",
            typeTsn = T.TSG.get,
            powerTsn = T.KVA1000.get,
            lowVoltage = T.V04.get,
            lowVoltageCell = "8",
            highVoltage = T.v3_15.get,
            highVoltageCell = "14 (1)",
            controlPanel = T.GSY.get,
            controlPanelCell = "19(л), 77(л)",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.EProt_04.get+ "\n" + T.AVR.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_SG.get
        )

        val t42 = Tsn(
            nameTsn = "42T",
            typeTsn = T.TM.get,
            powerTsn = T.KVA1000.get,
            lowVoltage = T.V04.get,
            lowVoltageCell = "31",
            highVoltage = T.v3_15.get,
            highVoltageCell = "127 (5)",
            controlPanel = T.GSY.get,
            controlPanelCell = "19(п), 77(п)",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.EProt_04.get + "\n" + T.AVR.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_M.get
        )

        val t43 = Tsn(
            nameTsn = "43T",
            typeTsn = T.TM.get,
            powerTsn = T.KVA560.get,
            lowVoltage = T.V04.get,
            lowVoltageCell = "48",
            highVoltage = T.v3_15.get,
            highVoltageCell = "70 (3)",
            controlPanel = T.GSY.get,
            controlPanelCell = "20(л), 78(л)",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.EProt_04.get+ "\n" + T.AVR.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_M.get
        )

        val t44 = Tsn(
            nameTsn = "44T",
            typeTsn = T.TAM.get,
            powerTsn = T.KVA750.get,
            lowVoltage = T.V04.get,
            lowVoltageCell = "71",
            highVoltage = T.v3_15.get,
            highVoltageCell = "161 (6)",
            controlPanel = T.GSY.get,
            controlPanelCell = "20(п), 78(п)",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.EProt_04.get+ "\n" + T.AVR.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_M.get + "\n" + T.TR_A.get
        )

        val t45 = Tsn(
            nameTsn = "45T",
            typeTsn = T.TM.get,
            powerTsn = T.KVA1000.get,
            lowVoltage = T.V04.get,
            lowVoltageCell = "127",
            highVoltage = T.v3_15.get,
            highVoltageCell = "218 (8)",
            controlPanel = T.GSY.get,
            controlPanelCell = "21(л), 68(л)",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.EProt_04.get+ "\n" + T.AVR.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_M.get
        )

        val t46 = Tsn(
            nameTsn = "46T",
            typeTsn = T.TSZ.get,
            powerTsn = T.KVA630.get,
            lowVoltage = T.V04.get,
            lowVoltageCell = "31",
            highVoltage = T.v3_15.get,
            highVoltageCell = "97 (4)",
            controlPanel = T.GSY.get,
            controlPanelCell = "21(п), 68(п)",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.MTZ_04.get + "\n" + T.MTZ_04_RP.get
                    + "\n" + T.NP_WP.get + "\n" + T.NP_RP.get + "\n" + T.NP_Z_TR.get + "\n" +T.AVR.get + "\n" +T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_SZ.get
        )

        val t47 = Tsn(
            nameTsn = "47T",
            typeTsn = T.TSZ.get,
            powerTsn = T.KVA630.get,
            lowVoltage = T.V04.get,
            lowVoltageCell = "44",
            highVoltage = T.v3_15.get,
            highVoltageCell = "237 (9)",
            controlPanel = T.GSY.get,
            controlPanelCell = "22(л)",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.MTZ_04.get + "\n" + T.MTZ_04_RP.get
                    + "\n" + T.NP_WP.get + "\n" + T.NP_RP.get + "\n" + T.NP_Z_TR.get+ "\n" +T.AVR.get + "\n" +T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_SZ.get
        )

        val t40 = Tsn(
            nameTsn = "40T",
            typeTsn = T.TSGL.get,
            powerTsn = T.KVA1000.get,
            lowVoltage = T.V04.get,
            lowVoltageCell = "19",
            highVoltage = T.v3_15.get,
            highVoltageCell = "22 (1)",
            controlPanel = T.GSY.get,
            controlPanelCell = "18(п), 76",
            shutdownProtection = T.MTO.get + "\n" + T.MO.get + "\n" + T.EProt_04.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_GL.get
        )

        val t50 = Tsn(
            nameTsn = "50T",
            typeTsn = T.TSGL.get,
            powerTsn = T.KVA1000.get,
            lowVoltage = T.V04.get,
            lowVoltageCell = "90",
            highVoltage = T.v3_15.get,
            highVoltageCell = "176 (7)",
            controlPanel = T.GSY.get,
            controlPanelCell = "18(п), 67",
            shutdownProtection = T.MTO.get + "\n" + T.MO.get + "\n" + T.EProt_04.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_GL.get
        )

        val t63 = Tsn(
            nameTsn = "63T",
            typeTsn = T.TM.get,
            powerTsn = T.KVA560.get,
            lowVoltage = T.V04Tai.get,
            lowVoltageCell = "2",
            highVoltage = T.v3_15.get,
            highVoltageCell = "132 (5)",
            controlPanel = T.V04Tai.get,
            controlPanelCell = "",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_M.get
        )

        val t65 = Tsn(
            nameTsn = "65T",
            typeTsn = T.TSZSY.get,
            powerTsn = T.KVA1000.get,
            lowVoltage = T.V04SchHVO.get,
            lowVoltageCell = "7",
            highVoltage = T.v3_15.get,
            highVoltageCell = "2 (1)",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get ,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_SZ.get + "\n" + T.TR_S2.get + "\n" + T.TR_Y.get
        )

        val t66 = Tsn(
            nameTsn = "66T",
            typeTsn = T.TSZ.get,
            powerTsn = T.KVA630.get,
            lowVoltage = T.V04HVO.get,
            lowVoltageCell = "12, 24",
            highVoltage = T.v3_15.get,
            highVoltageCell = "94 (4)",
            controlPanel = T.V04HVO.get,
            controlPanelCell = "",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.ZProt_04.get + "\n" + T.MTZ_04.get
                    + "\n" +T.AVR.get + "\n" +T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_SZ.get
        )

        val t60 = Tsn(
            nameTsn = "60T",
            typeTsn = T.TSZ.get,
            powerTsn = T.KVA630.get,
            lowVoltage = T.V04HVO.get,
            lowVoltageCell = "1, 2, 3",
            highVoltage = T.v3_15.get,
            highVoltageCell = "74 (3)",
            controlPanel = T.V04HVO.get,
            controlPanelCell = "",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + "Защита от КЗ 0,4кВ"
                    + "\n" +T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_SZ.get
        )

        val t68 = Tsn(
            nameTsn = "68T",
            typeTsn = T.TSZ.get,
            powerTsn = T.KVA630.get,
            lowVoltage = T.V04TY.get,
            lowVoltageCell = "1, 14",
            highVoltage = T.v3_15.get,
            highVoltageCell = "210 (8)",
            controlPanel = T.V04TY.get,
            controlPanelCell = "",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.MTZ_04.get+ "\n" + T.ZProt_04.get
                    + "\n" +T.AVR.get + "\n" +T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_SZ.get
        )

        val t69 = Tsn(
            nameTsn = "69T",
            typeTsn = T.TSZ.get,
            powerTsn = T.KVA630.get,
            lowVoltage = T.V04TY.get,
            lowVoltageCell = "16, 29",
            highVoltage = T.v3_15.get,
            highVoltageCell = "92 (4)",
            controlPanel = T.V04TY.get,
            controlPanelCell = "",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.MTZ_04.get+ "\n" + T.ZProt_04.get
                    + "\n" +T.AVR.get + "\n" +T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_SZ.get
        )

        val t70 = Tsn(
            nameTsn = "70T",
            typeTsn = T.TSZ.get,
            powerTsn = T.KVA630.get,
            lowVoltage = T.V04TY.get,
            lowVoltageCell = "4, 11, 19, 26",
            highVoltage = T.v3_15.get,
            highVoltageCell = "186 (7)",
            controlPanel = T.V04TY.get,
            controlPanelCell = "",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.MTZ_04.get+ "\n" + T.ZProt_04.get
                    + "\n" +T.AVR.get + "\n" +T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_SZ.get
        )

        val t71 = Tsn(
            nameTsn = "71T",
            typeTsn = T.TSZSY.get,
            powerTsn = T.KVA1000.get,
            lowVoltage = T.V04_VK.get,
            lowVoltageCell = "12",
            highVoltage = T.v6_3.get,
            highVoltageCell = "17 (1)",
            controlPanel = T.V04_VK.get,
            controlPanelCell = "",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.MTZ_04.get + "\n" + T.NP_Z_TR.get
                    + "\n" +  T.AVR.get + "\n" +T.OVERLOAD.get + "\n" +T.YROV.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_SZ.get + "\n" + T.TR_S2.get + "\n" + T.TR_Y.get
        )

        val t72 = Tsn(
            nameTsn = "72T",
            typeTsn = T.TSZSY.get,
            powerTsn = T.KVA1000.get,
            lowVoltage = T.V04.get,
            lowVoltageCell = "25",
            highVoltage = T.v3_15.get,
            highVoltageCell = "22 (2)",
            controlPanel = T.V04_VK.get,
            controlPanelCell = "",
            shutdownProtection = T.TO.get + "\n" + T.MTZ.get + "\n" + T.MTZ_04.get + "\n" + T.NP_Z_TR.get
                    + "\n" +  T.AVR.get + "\n" +T.OVERLOAD.get + "\n" +T.YROV.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_S.get  + "\n" + T.TR_SZ.get + "\n" + T.TR_S2.get + "\n" + T.TR_Y.get
        )

        val t22 = Tsn(
            nameTsn = "22T",
            typeTsn = T.TDN.get,
            powerTsn = T.KVA10000.get,
            lowVoltage = T.v3_15.get,
            lowVoltageCell = "32",
            highVoltage = "Т2-10,5 кВ",
            highVoltageCell = "-",
            controlPanel = T.GSY.get,
            controlPanelCell = "17, 73",
            shutdownProtection = T.DFZ.get + "\n" + T.MTZ_10.get + "\n" + T.G_Z.get
                    + "\n" +T.AVR.get + "\n" +T.ZMN.get + "\n" +T.BLOWING.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_D.get  + "\n" + T.TR_N.get
        )

        val t23 = Tsn(
            nameTsn = "23T",
            typeTsn = T.TDN.get,
            powerTsn = T.KVA10000.get,
            lowVoltage = T.v3_15.get,
            lowVoltageCell = "64",
            highVoltage = "Блок 3ГАТ-10,5 кВ",
            highVoltageCell = "-",
            controlPanel = T.GSY.get,
            controlPanelCell = "16, 72",
            shutdownProtection = T.DFZ.get + "\n" + T.MTZ_10.get + "\n" + T.G_Z.get
                    + "\n" +T.AVR.get + "\n" +T.ZMN.get + "\n" +T.BLOWING.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_D.get  + "\n" + T.TR_N.get
        )

        val t24 = Tsn(
            nameTsn = "24T",
            typeTsn = T.TDN.get,
            powerTsn = T.KVA10000.get,
            lowVoltage = T.v3_15.get,
            lowVoltageCell = "112, 114",
            highVoltage = "Блок 4ГАТ-10,5 кВ",
            highVoltageCell = "-",
            controlPanel = T.GSY.get,
            controlPanelCell = "15, 71",
            shutdownProtection = T.DFZ.get + "\n" + T.MTZ_10.get + "\n" + T.G_Z.get + "\n" + T.MTZ_3.get
                    + "\n" +  T.AVR.get + "\n" +T.ZMN.get + "\n" +T.BLOWING.get + "\n" + T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_D.get  + "\n" + T.TR_N.get
        )

        val t25 = Tsn(
            nameTsn = "25T",
            typeTsn = T.TDN.get,
            powerTsn = T.KVA10000.get,
            lowVoltage = T.v3_15.get,
            lowVoltageCell = "168, 170",
            highVoltage = "Блок 5ГТ-10,5 кВ",
            highVoltageCell = "-",
            controlPanel = T.GSY.get,
            controlPanelCell = "14, 70",
            shutdownProtection = T.DFZ.get + "\n" + T.MTZ_10.get + "\n" + T.G_Z.get + "\n" + T.MTZ_3.get
                    + "\n" +  T.AVR.get + "\n" +T.ZMN.get + "\n" +T.BLOWING.get + "\n" + T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_D.get  + "\n" + T.TR_N.get
        )

        val t26 = Tsn(
            nameTsn = "26T",
            typeTsn = T.TDN.get,
            powerTsn = T.KVA10000.get,
            lowVoltage = T.v3_15.get,
            lowVoltageCell = "224, 226",
            highVoltage = "Блок 6ГТ-10,5 кВ",
            highVoltageCell = "-",
            controlPanel = T.GSY.get,
            controlPanelCell = "23, 69",
            shutdownProtection = T.DFZ.get + "\n" + T.MTZ_10.get + "\n" + T.G_Z.get + "\n" + T.MTZ_3.get
                    + "\n" +  T.AVR.get + "\n" +T.ZMN.get + "\n" +T.BLOWING.get + "\n" + T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_D.get  + "\n" + T.TR_N.get
        )

        val t27 = Tsn(
            nameTsn = "27T",
            typeTsn = T.TM.get,
            powerTsn = T.KVA6300.get,
            lowVoltage = T.v6_3.get,
            lowVoltageCell = "15",
            highVoltage = "Блок 5ГТ-10,5 кВ",
            highVoltageCell = "-",
            controlPanel = T.GSY.get,
            controlPanelCell = "14а, 150",
            shutdownProtection = T.DFZ.get + "\n" + T.MTZ.get + "\n" + T.G_Z.get + "\n" + T.MTZ_6.get
                    + "\n" +T.AVR.get + "\n" +T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_M.get
        )

        val t28 = Tsn(
            nameTsn = "28T",
            typeTsn = T.TM.get,
            powerTsn = T.KVA6300.get,
            lowVoltage = T.v6_3.get,
            lowVoltageCell = "35",
            highVoltage = "Блок 6ГТ-10,5 кВ",
            highVoltageCell = "-",
            controlPanel = T.GSY.get,
            controlPanelCell = "14а, 151",
            shutdownProtection = T.DFZ.get + "\n" + T.MTZ.get + "\n" + T.G_Z.get + "\n" + T.MTZ_6.get
                    + "\n" +T.AVR.get + "\n" +T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_M.get
        )

        val t29 = Tsn(
            nameTsn = "29T",
            typeTsn = T.TM.get,
            powerTsn = T.KVA6300.get,
            lowVoltage = T.v6_3.get,
            lowVoltageCell = "45",
            highVoltage = "Блок 4ГАТ-10,5 кВ",
            highVoltageCell = "-",
            controlPanel = T.GSY.get,
            controlPanelCell = "13а, 156(л)",
            shutdownProtection = T.DFZ.get + "\n" + T.MTZ_10.get + "\n" + T.MTZ_6.get
                    + "\n" +T.AVR.get + "\n" +T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_M.get
        )

        val t10 = Tsn(
            nameTsn = "10T",
            typeTsn = T.TM.get,
            powerTsn = T.KVA10000.get,
            lowVoltage = T.v6_3.get,
            lowVoltageCell = "-",
            highVoltage = "Блок 3ГАТ-10,5 кВ",
            highVoltageCell = "-",
            controlPanel = T.GSY.get,
            controlPanelCell = "13а, 156(п)",
            shutdownProtection = T.DFZ.get + "\n" + T.MTZ_10.get + "\n" + T.MTZ_6.get
                     + "\n" + T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_M.get
        )

        val t20 = Tsn(
            nameTsn = "20T",
            typeTsn = T.TDNG.get,
            powerTsn = T.KVA10000.get,
            lowVoltage = T.v3_15.get,
            lowVoltageCell = "29",
            highVoltage = T.ORY_110.get,
            highVoltageCell = "9",
            controlPanel = T.GSY.get,
            controlPanelCell = "18, 75",
            shutdownProtection = T.DFZ.get + "\n" + T.MTZ.get + "\n" + T.MTZ_3.get + "\n" + T.G_Z.get
                    + "\n" +T.AVR.get + "\n" +T.ZMN.get + "\n" +T.BLOWING.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_D.get  + "\n" + T.TR_N.get + "\n" + T.TR_G.get
        )

        val t30 = Tsn(
            nameTsn = "30T",
            typeTsn = T.TDN.get,
            powerTsn = T.KVA15000.get,
            lowVoltage = T.v3_15.get,
            lowVoltageCell = "197",
            highVoltage = T.ORY_35.get,
            highVoltageCell = "-",
            controlPanel = T.GSY.get,
            controlPanelCell = "22, 79",
            shutdownProtection = T.DFZ.get + "\n" + T.MTZ.get + "\n" + T.MTZ_3.get+ "\n" +
                    T.G_Z.get + "\n" +T.AVR.get  + "\n" +T.BLOWING.get + "\n" +T.OVERLOAD.get,
            signalProtection = T.TR_T.get + "\n" + T.TR_D.get  + "\n" + T.TR_N.get
        )

        val list = listOf<Tsn>(t41,t42,t43,t44,t45,t46,t47,t40,t50,t63,t65,t66,t60,t68,t69,t70,t71,t72,t22,t23,t24,t25,t26,t27,t28,t29,t10,t20,t30)
        listTsn.addAll(list)
    }


    fun getList(): List<Tsn> = listTsn

}