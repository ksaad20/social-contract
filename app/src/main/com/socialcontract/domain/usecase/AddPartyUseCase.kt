```kotlin
package com.socialcontract.domain.usecase

import com.socialcontract.data.database.entities.PartyEntity
import com.socialcontract.data.repository.PartyRepository
import java.util.UUID

class AddPartyUseCase(
    private val partyRepository: PartyRepository
) {

    suspend operator fun invoke(
        contractId: String,
        name: String,
        role: String,
        phone: String?,
        address: String?,
        nationalIdReference: String?
    ): String {
        require(contractId.isNotBlank()) {
            "Contract ID must not be blank."
        }

        require(name.isNotBlank()) {
            "Party name must not be blank."
        }

        require(role.isNotBlank()) {
            "Party role must not be blank."
        }

        val partyId = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()

        val party = PartyEntity(
            id = partyId,
            contractId = contractId,
            name = name.trim(),
            role = role.trim(),
            phone = phone?.trim()?.ifBlank { null },
            address = address?.trim()?.ifBlank { null },
            nationalIdReference =
                nationalIdReference
                    ?.trim()
                    ?.ifBlank { null },
            createdAt = now,
            updatedAt = now
        )

        partyRepository.insertParty(party)

        return partyId
    }
}
```
