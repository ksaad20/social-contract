package com.socialcontract.ui.parties

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.socialcontract.data.database.entities.PartyEntity
import com.socialcontract.data.database.dao.PartyDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class PartyViewModel(
    private val partyDao: PartyDao,
    private val contractId: String
) : ViewModel() {

    val parties: StateFlow<List<PartyEntity>> =
        partyDao
            .observeByContractId(contractId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun addParty(
        name: String,
        phoneNumber: String?,
        address: String?,
        role: String,
        nationalIdReference: String?
    ) {
        if (name.isBlank() || role.isBlank()) {
            return
        }

        viewModelScope.launch {
            val now = System.currentTimeMillis()

            partyDao.insert(
                PartyEntity(
                    id = UUID.randomUUID().toString(),
                    contractId = contractId,
                    name = name.trim(),
                    phoneNumber = phoneNumber?.trim(),
                    address = address?.trim(),
                    role = role.trim(),
                    nationalIdReference =
                        nationalIdReference?.trim(),
                    createdAt = now,
                    updatedAt = now
                )
            )
        }
    }

    fun deleteParty(
        party: PartyEntity
    ) {
        viewModelScope.launch {
            partyDao.delete(party)
        }
    }
}
