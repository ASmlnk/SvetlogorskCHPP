package com.example.svetlogorskchpp.__di

import android.content.Context
import androidx.room.Room
import com.example.svetlogorskchpp.__data.database.AppDataBase
import com.example.svetlogorskchpp.__data.database.MIGRATION_10_11
import com.example.svetlogorskchpp.__data.database.MIGRATION_1_2
import com.example.svetlogorskchpp.__data.database.MIGRATION_2_3
import com.example.svetlogorskchpp.__data.database.MIGRATION_3_4
import com.example.svetlogorskchpp.__data.database.MIGRATION_4_5
import com.example.svetlogorskchpp.__data.database.MIGRATION_5_6
import com.example.svetlogorskchpp.__data.database.MIGRATION_6_7
import com.example.svetlogorskchpp.__data.database.MIGRATION_7_8
import com.example.svetlogorskchpp.__data.database.MIGRATION_8_9
import com.example.svetlogorskchpp.__data.database.MIGRATION_9_10
import com.example.svetlogorskchpp.__data.database.calendarNoteTag.CalendarNoteTagDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.ElMotor.ElMotorDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.LightingAndOther.LightingAndOtherDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearTr.OpenSwitchgearTrDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.OpenSwitchgearVl.OpenSwitchgearVlDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.Switchgear.SwitchgearDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.transformerOwnNeeds.TransformerOwnNeedsDao
import com.example.svetlogorskchpp.__data.database.electrical_equipment.turbogenerator.TurboGeneratorDao
import com.example.svetlogorskchpp.__data.database.note.NoteDao
import com.example.svetlogorskchpp.__data.database.requestWork.NoteRequestWorkDao
import com.example.svetlogorskchpp.__data.database.requestWorkTag.RequestWorkTagDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocaleModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "app_database"
        )
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_3_4)
            .addMigrations(MIGRATION_4_5)
            .addMigrations(MIGRATION_5_6)
            .addMigrations(MIGRATION_6_7)
            .addMigrations(MIGRATION_7_8)
            .addMigrations(MIGRATION_8_9)
            .addMigrations(MIGRATION_9_10)
            .addMigrations(MIGRATION_10_11)
            .build()
    }

    @Provides
    fun provideCalendarNoteTagDao (dataBase: AppDataBase): CalendarNoteTagDao {
        return dataBase.calendarNoteTagDao()
    }

    @Provides
    fun provideNoteDao (dataBase: AppDataBase): NoteDao {
        return dataBase.noteDao()
    }

    @Provides
    fun provideRequestWorkTagDao (dataBase: AppDataBase): RequestWorkTagDao {
        return dataBase.requestWorkTagDao()
    }

    @Provides
    fun provideNoteRequestWorkDao (dataBase: AppDataBase): NoteRequestWorkDao {
        return dataBase.requestWorkDao()
    }

    @Provides
    fun provideOpenSwitchgearVlDao(dataBase: AppDataBase) : OpenSwitchgearVlDao {
        return dataBase.openSwitchgearVlDao()
    }

    @Provides
    fun provideOpenSwitchgearTrDao(dataBase: AppDataBase) : OpenSwitchgearTrDao {
        return dataBase.openSwitchgearTrDao()
    }

    @Provides
    fun provideTransformerOwnNeedsDao(dataBase: AppDataBase): TransformerOwnNeedsDao {
        return dataBase.transformerOwnNeedsDao()
    }

    @Provides
    fun provideTurboGeneratorDao(dataBase: AppDataBase): TurboGeneratorDao {
        return dataBase.turboGeneratorDao()
    }

    @Provides
    fun provideElMotorDaoDao(dataBase: AppDataBase): ElMotorDao {
        return dataBase.elMotorDao()
    }

    @Provides
    fun provideSwitchgearDao(dataBase: AppDataBase): SwitchgearDao {
        return dataBase.switchgearDao()
    }

    @Provides
    fun provideLightingAndOtherDao(dataBase: AppDataBase): LightingAndOtherDao {
        return dataBase.lightingAndOtherDao()
    }
}

