package com.example.svetlogorskchpp.__data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarMyNoteTagEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsEntity
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorEntity
import com.example.svetlogorskchpp.__data.database.note.NoteDao
import com.example.svetlogorskchpp.__data.database.note.NoteEntity
import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkDao
import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkEntity
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagDao
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagEntity

@Database(
    entities = [
        CalendarMyNoteTagEntity::class,
        NoteEntity::class,
        RequestWorkTagEntity::class,
        NoteRequestWorkEntity::class,
        OpenSwitchgearVlEntity::class,
        OpenSwitchgearTrEntity::class,
        TransformerOwnNeedsEntity::class,
        TurboGeneratorEntity::class,
        ElMotorEntity::class,
        SwitchgearEntity::class,
        LightingAndOtherEntity::class],
    version = 11
)
@TypeConverters(CalendarTypeConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun calendarNoteTagDao(): CalendarNoteTagDao
    abstract fun noteDao(): NoteDao
    abstract fun requestWorkTagDao(): RequestWorkTagDao
    abstract fun requestWorkDao(): NoteRequestWorkDao
    abstract fun openSwitchgearVlDao(): OpenSwitchgearVlDao
    abstract fun openSwitchgearTrDao(): OpenSwitchgearTrDao
    abstract fun transformerOwnNeedsDao(): TransformerOwnNeedsDao
    abstract fun turboGeneratorDao(): TurboGeneratorDao
    abstract fun elMotorDao(): ElMotorDao
    abstract fun switchgearDao(): SwitchgearDao
    abstract fun lightingAndOtherDao(): LightingAndOtherDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE notes ADD COLUMN is_request_work INTEGER NOT NULL DEFAULT 0")
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(dataBase: SupportSQLiteDatabase) {
        dataBase.execSQL("CREATE TABLE IF NOT EXISTS request_work_tag (date INTEGER PRIMARY KEY NOT NULL, month INTEGER NOT NULL)")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS request_work (
                id TEXT PRIMARY KEY NOT NULL,
                tagDateOpen INTEGER NOT NULL,
                tagDateClose INTEGER NOT NULL,
                tagMonthOpen INTEGER NOT NULL,
                tagMonthClose INTEGER NOT NULL,
                numberRequestWork TEXT NOT NULL,
                dateOpen INTEGER NOT NULL,
                dateClose INTEGER NOT NULL,
                accession TEXT NOT NULL,
                reason TEXT NOT NULL,
                additionally TEXT NOT NULL,
                isExtend INTEGER NOT NULL,
                contentExtend TEXT NOT NULL
            )
            """.trimIndent()
        )
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE request_work ADD COLUMN permission TEXT NOT NULL DEFAULT 'default_value'")
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE IF NOT EXISTS open_switchgear_vl (
                id TEXT NOT NULL PRIMARY KEY,
                name TEXT NOT NULL,
                panelMcp TEXT NOT NULL,
                bysSystem TEXT NOT NULL,
                cell TEXT NOT NULL,
                voltage TEXT NOT NULL,
                isTransit INTEGER NOT NULL,
                isVl INTEGER NOT NULL,
                typeSwitch TEXT NOT NULL,
                typeInsTr TEXT NOT NULL,
                automation TEXT NOT NULL,
                apv TEXT NOT NULL,
                keyShr1 TEXT NOT NULL,
                keyShr2 TEXT NOT NULL,
                keyLr TEXT NOT NULL,
                keyOr TEXT NOT NULL,
                phaseProtection TEXT NOT NULL,
                earthProtection TEXT NOT NULL
            )
        """.trimIndent()
        )
    }
}

val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
                    CREATE TABLE IF NOT EXISTS open_switchgear_tr (
                        id TEXT PRIMARY KEY NOT NULL,
                        name TEXT NOT NULL,
                        panelMcp TEXT NOT NULL,
                        type TEXT NOT NULL,
                        parameterType TEXT NOT NULL,
                        transcriptType TEXT NOT NULL,
                        additionally TEXT NOT NULL,
                        isSpare INTEGER NOT NULL DEFAULT 0,
                        isThreeWinding INTEGER NOT NULL DEFAULT 0,

                        bysSystemVn TEXT NOT NULL,
                        cellVn TEXT NOT NULL,
                        voltageVn TEXT NOT NULL,
                        typeSwitchVn TEXT NOT NULL,
                        typeInsTrVn TEXT NOT NULL,
                        keyShr1Vn TEXT NOT NULL,
                        keyShr2Vn TEXT NOT NULL,
                        keyLrVn TEXT NOT NULL,
                        keyOrVn TEXT NOT NULL,

                        bysSystemSn TEXT NOT NULL,
                        cellSn TEXT NOT NULL,
                        voltageSn TEXT NOT NULL,
                        typeSwitchSn TEXT NOT NULL,
                        typeInsTrSn TEXT NOT NULL,
                        keyShr1Sn TEXT NOT NULL,
                        keyShr2Sn TEXT NOT NULL,
                        keyLrSn TEXT NOT NULL,
                        keyOrSn TEXT NOT NULL,

                        automation TEXT NOT NULL,
                        apv TEXT NOT NULL,
                        phaseProtection TEXT NOT NULL,
                        earthProtection TEXT NOT NULL
                    )
                """.trimIndent()
        )
    }
}

val MIGRATION_7_8 = object : Migration(7, 8) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE transformer_own_needs (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                panelMcp TEXT NOT NULL,
                type TEXT NOT NULL,
                parameterType TEXT NOT NULL,
                transcriptType TEXT NOT NULL,
                additionally TEXT NOT NULL,
                isSpare INTEGER NOT NULL,
                powerSupplyId TEXT NOT NULL,
                powerSupplyCell TEXT NOT NULL,
                powerSupplyName TEXT NOT NULL,
                voltage TEXT NOT NULL,
                typeSwitch TEXT NOT NULL,
                typeInsTr TEXT NOT NULL,
                automation TEXT NOT NULL,
                apv TEXT NOT NULL,
                phaseProtection TEXT NOT NULL,
                earthProtection TEXT NOT NULL
            )
        """.trimIndent()
        )
    }
}

val MIGRATION_8_9 = object : Migration(8, 9) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
            CREATE TABLE turbogenerator (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                panelMcp TEXT NOT NULL,
                typeSwitch TEXT NOT NULL,
                typeInsTr TEXT NOT NULL,
                typeGenerator TEXT NOT NULL,
                transcriptTypeGenerator TEXT NOT NULL,
                volumeTg TEXT NOT NULL,
                volumeReceiver TEXT NOT NULL,
                additionallyGenerator TEXT NOT NULL,
                sourceExcitation TEXT NOT NULL,
                generatorStarted TEXT NOT NULL,
                translationIntoRv TEXT NOT NULL,
                translationFromRv TEXT NOT NULL,
                typeTurbin TEXT NOT NULL,
                transcriptTypeTurbin TEXT NOT NULL,
                powerEl TEXT NOT NULL,
                powerThermal TEXT NOT NULL,
                steamConsumption TEXT NOT NULL,
                additionallyTurbin TEXT NOT NULL,
                powerSupplyId TEXT NOT NULL,
                powerSupplyCell TEXT NOT NULL,
                powerSupplyName TEXT NOT NULL,
                automation TEXT NOT NULL,
                phaseProtection TEXT NOT NULL,
                earthProtection TEXT NOT NULL,
                additionallyRza1 TEXT NOT NULL,
                additionallyRza2 TEXT NOT NULL
            )
        """.trimIndent()
        )
    }
}

val MIGRATION_9_10 = object : Migration(9, 10) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
                CREATE TABLE el_motor (
                 id TEXT PRIMARY KEY NOT NULL,
                 nam TEXT NOT NULL,
                 powSuId TEXT NOT NULL,
                 powSuC TEXT NOT NULL,
                 powSuNam TEXT NOT NULL,
                 automation TEXT NOT NULL,
                 phPr TEXT NOT NULL,
                 eaPr TEXT NOT NULL,
                 addRz TEXT NOT NULL,
                 cat TEXT NOT NULL,
                 gCat TEXT NOT NULL,
                 tSw TEXT NOT NULL,
                 tIT TEXT NOT NULL,
                 addit TEXT NOT NULL,
                 isRep INTEGER NOT NULL,
                 tRep TEXT NOT NULL,
                 conCir TEXT NOT NULL,
                 powEl TEXT NOT NULL,
                 vol TEXT NOT NULL,
                 i TEXT NOT NULL,
                 n TEXT NOT NULL,
                 tEl TEXT NOT NULL,
                 mecTyp TEXT NOT NULL,
                 mecPer TEXT NOT NULL,
                 mecPr TEXT NOT NULL,
                 mecN TEXT NOT NULL
                )
            """.trimIndent()
        )
        db.execSQL(
            """
                CREATE TABLE switchgear (
                 id TEXT PRIMARY KEY NOT NULL,
                 nam TEXT NOT NULL,
                 tSw TEXT NOT NULL,
                 tIT TEXT NOT NULL,
                 addit TEXT NOT NULL,
                 cat TEXT NOT NULL,
                 namDep TEXT NOT NULL,
                 vol TEXT NOT NULL,
                 aut TEXT NOT NULL,
                 phPr TEXT NOT NULL,
                 eaPr TEXT NOT NULL,
                 addRz TEXT NOT NULL,
                 inf TEXT NOT NULL,
                 powSuId1 TEXT NOT NULL,
                 powSuC1 TEXT NOT NULL,
                 powSuNam1 TEXT NOT NULL,
                 powSuId2 TEXT NOT NULL,
                 powSuC2 TEXT NOT NULL,
                 powSuNam2 TEXT NOT NULL,
                 powSuRId1 TEXT NOT NULL,
                 powSuRC1 TEXT NOT NULL,
                 powSuRNam1 TEXT NOT NULL,
                 powSuRId2 TEXT NOT NULL,
                 powSuRC2 TEXT NOT NULL,
                 powSuRNam2 TEXT NOT NULL,
                 powSuRId3 TEXT NOT NULL,
                 powSuRC3 TEXT NOT NULL,
                 powSuRNam3 TEXT NOT NULL
                )
            """.trimIndent()
        )
        db.execSQL(
            """
                CREATE TABLE lighting_and_other (
                 id TEXT PRIMARY KEY NOT NULL,
                 nam TEXT NOT NULL,
                 powSuId TEXT NOT NULL,
                 powSuC TEXT NOT NULL,
                 powSuNam TEXT NOT NULL,
                 tSw TEXT NOT NULL,
                 addit TEXT NOT NULL,
                 isLi INTEGER NOT NULL,
                 loc TEXT NOT NULL
                )
            """.trimIndent()
        )
    }
}

val MIGRATION_10_11 = object : Migration(10, 11) {
    override fun migrate(db: SupportSQLiteDatabase) {

        db.execSQL("ALTER TABLE el_motor ADD COLUMN mecH TEXT ")
        db.execSQL("ALTER TABLE el_motor ADD COLUMN mecPowN TEXT ")
        db.execSQL("ALTER TABLE el_motor ADD COLUMN mecAddit TEXT ")

    }
}