```kotlin
package com.socialcontract.domain.usecase

import com.socialcontract.data.database.entities.LandEntity
import com.socialcontract.data.repository.LandRepository
import java.util.UUID

class AddLandUseCase(
    private val landRepository: LandRepository
) {

    suspend operator fun invoke(
        contractId: String,
        description: String,
        area: Double,
        areaUnit: String,
        locationDescription: String?,
        latitude: Double?,
        longitude: Double?
    ): String {
        require(contractId.isNotBlank()) {
            "Contract ID must not be blank."
        }

        require(description.isNotBlank()) {
            "Land description must not be blank."
        }

        require(area > 0.0) {
            "Land area must be greater than zero."
        }

        require(areaUnit.isNotBlank()) {
            "Land area unit must not be blank."
        }

        if (latitude != null) {
            require(latitude in -90.0..90.0) {
                "Latitude must be between -90 and 90."
            }
        }

        if (longitude != null) {
            require(longitude in -180.0..180.0) {
                "Longitude must be between -180 and 180."
            }
        }

        val landId = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()

        val land = LandEntity(
            id = landId,
            contractId = contractId,
            description = description.trim(),
            area = area,
            areaUnit = areaUnit.trim(),
            locationDescription =
                locationDescription
                    ?.trim()
                    ?.ifBlank { null },
            latitude = latitude,
            longitude = longitude,
            createdAt = now,
            updatedAt = now
        )

        landRepository.insertLand(land)

        return landId
    }
}
```
