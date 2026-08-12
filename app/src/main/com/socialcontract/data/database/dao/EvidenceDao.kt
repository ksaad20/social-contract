package com.socialcontract.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.socialcontract.data.database.entities.EvidenceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EvidenceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(evidence: EvidenceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(evidence: List<EvidenceEntity>)

    @Update
    suspend fun update(evidence: EvidenceEntity)

    @Delete
    suspend fun delete(evidence: EvidenceEntity)

    @Query(
        """
        SELECT *
        FROM evidence
        WHERE contractId = :contractId
        ORDER BY capturedAt ASC, createdAt ASC
        """
    )
    fun observeByContractId(
        contractId: String
    ): Flow<List<EvidenceEntity>>

    @Query(
        """
        SELECT *
        FROM evidence
        WHERE id = :evidenceId
        LIMIT 1
        """
    )
    suspend fun getById(
        evidenceId: String
    ): EvidenceEntity?

    @Query(
        """
        DELETE FROM evidence
        WHERE contractId = :contractId
        """
    )
    suspend fun deleteByContractId(
        contractId: String
    )
}
