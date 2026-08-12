package com.socialcontract.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.socialcontract.data.database.dao.ContractDao
import com.socialcontract.data.database.dao.EvidenceDao
import com.socialcontract.data.database.dao.ExpenseDao
import com.socialcontract.data.database.dao.HarvestDao
import com.socialcontract.data.database.dao.LandDao
import com.socialcontract.data.database.dao.PartyDao
import com.socialcontract.data.database.dao.SettlementDao
import com.socialcontract.data.database.entities.ContractEntity
import com.socialcontract.data.database.entities.CultivationEntity
import com.socialcontract.data.database.entities.EvidenceEntity
import com.socialcontract.data.database.entities.ExpenseEntity
import com.socialcontract.data.database.entities.HarvestEntity
import com.socialcontract.data.database.entities.LandEntity
import com.socialcontract.data.database.entities.PartyEntity
import com.socialcontract.data.database.entities.SettlementEntity

@Database(
    entities = [
        ContractEntity::class,
        PartyEntity::class,
        LandEntity::class,
        CultivationEntity::class,
        ExpenseEntity::class,
        HarvestEntity::class,
        EvidenceEntity::class,
        SettlementEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun contractDao(): ContractDao

    abstract fun partyDao(): PartyDao

    abstract fun landDao(): LandDao

    abstract fun expenseDao(): ExpenseDao

    abstract fun harvestDao(): HarvestDao

    abstract fun evidenceDao(): EvidenceDao

    abstract fun settlementDao(): SettlementDao
}
