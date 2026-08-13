package com.socialcontract.android.domain

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Date

class ContractTest {

    @Test
    fun `creates contract with required fields`() {
        val contract = Contract(
            id = "contract-001",
            contractNumber = "SC-2026-001",
            landId = "land-001",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Rice",
            landArea = 2.5,
            landAreaUnit = "acre"
        )

        assertEquals("contract-001", contract.id)
        assertEquals("SC-2026-001", contract.contractNumber)
        assertEquals("land-001", contract.landId)
        assertEquals("party-001", contract.cultivatorPartyId)
        assertEquals("party-002", contract.landownerPartyId)
        assertEquals("Rice", contract.cropName)
        assertEquals(2.5, contract.landArea, 0.001)
        assertEquals("acre", contract.landAreaUnit)
    }

    @Test
    fun `contract stores cultivation duration`() {
        val contract = Contract(
            id = "contract-002",
            contractNumber = "SC-2026-002",
            landId = "land-002",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Wheat",
            landArea = 5.0,
            landAreaUnit = "acre",
            cultivationDurationDays = 120
        )

        assertEquals(
            120,
            contract.cultivationDurationDays
        )
    }

    @Test
    fun `contract stores settlement shares`() {
        val contract = Contract(
            id = "contract-003",
            contractNumber = "SC-2026-003",
            landId = "land-003",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Rice",
            landArea = 1.0,
            landAreaUnit = "acre",
            cultivatorSharePercent = 60.0,
            landownerSharePercent = 40.0
        )

        assertEquals(
            60.0,
            contract.cultivatorSharePercent,
            0.001
        )

        assertEquals(
            40.0,
            contract.landownerSharePercent,
            0.001
        )
    }

    @Test
    fun `contract stores creation and cultivation dates`() {
        val createdAt = Date(1_700_000_000_000L)
        val startDate = Date(1_700_100_000_000L)
        val endDate = Date(1_710_100_000_000L)

        val contract = Contract(
            id = "contract-004",
            contractNumber = "SC-2026-004",
            landId = "land-004",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Maize",
            landArea = 3.0,
            landAreaUnit = "acre",
            createdAt = createdAt,
            startDate = startDate,
            endDate = endDate
        )

        assertEquals(createdAt, contract.createdAt)
        assertEquals(startDate, contract.startDate)
        assertEquals(endDate, contract.endDate)
    }

    @Test
    fun `contract stores expected harvest information`() {
        val contract = Contract(
            id = "contract-005",
            contractNumber = "SC-2026-005",
            landId = "land-005",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Potato",
            landArea = 4.0,
            landAreaUnit = "acre",
            expectedYield = 10_000.0,
            yieldUnit = "kg",
            expectedRevenue = 350_000.0
        )

        assertEquals(
            10_000.0,
            contract.expectedYield,
            0.001
        )

        assertEquals("kg", contract.yieldUnit)

        assertEquals(
            350_000.0,
            contract.expectedRevenue,
            0.001
        )
    }

    @Test
    fun `contract stores notes`() {
        val contract = Contract(
            id = "contract-006",
            contractNumber = "SC-2026-006",
            landId = "land-006",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Rice",
            landArea = 2.0,
            landAreaUnit = "acre",
            notes = "Irrigation costs shared equally."
        )

        assertEquals(
            "Irrigation costs shared equally.",
            contract.notes
        )
    }

    @Test
    fun `contract status defaults to active`() {
        val contract = Contract(
            id = "contract-007",
            contractNumber = "SC-2026-007",
            landId = "land-007",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Rice",
            landArea = 1.5,
            landAreaUnit = "acre"
        )

        assertEquals(
            Contract.Status.ACTIVE,
            contract.status
        )
    }

    @Test
    fun `contract status can be completed`() {
        val contract = Contract(
            id = "contract-008",
            contractNumber = "SC-2026-008",
            landId = "land-008",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Rice",
            landArea = 1.5,
            landAreaUnit = "acre",
            status = Contract.Status.COMPLETED
        )

        assertEquals(
            Contract.Status.COMPLETED,
            contract.status
        )
    }

    @Test
    fun `contract status can be cancelled`() {
        val contract = Contract(
            id = "contract-009",
            contractNumber = "SC-2026-009",
            landId = "land-009",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Rice",
            landArea = 1.5,
            landAreaUnit = "acre",
            status = Contract.Status.CANCELLED
        )

        assertEquals(
            Contract.Status.CANCELLED,
            contract.status
        )
    }

    @Test
    fun `contract data class equality works`() {
        val first = Contract(
            id = "contract-010",
            contractNumber = "SC-2026-010",
            landId = "land-010",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Rice",
            landArea = 2.0,
            landAreaUnit = "acre"
        )

        val second = first.copy()

        assertEquals(first, second)
    }

    @Test
    fun `contract copy preserves unchanged fields`() {
        val original = Contract(
            id = "contract-011",
            contractNumber = "SC-2026-011",
            landId = "land-011",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Rice",
            landArea = 2.0,
            landAreaUnit = "acre",
            cultivatorSharePercent = 60.0,
            landownerSharePercent = 40.0
        )

        val updated = original.copy(
            cropName = "Wheat"
        )

        assertEquals("Wheat", updated.cropName)
        assertEquals(original.id, updated.id)
        assertEquals(
            original.landArea,
            updated.landArea,
            0.001
        )
        assertEquals(
            original.cultivatorSharePercent,
            updated.cultivatorSharePercent,
            0.001
        )
        assertEquals(
            original.landownerSharePercent,
            updated.landownerSharePercent,
            0.001
        )
    }

    @Test
    fun `contract can represent zero optional financial values`() {
        val contract = Contract(
            id = "contract-012",
            contractNumber = "SC-2026-012",
            landId = "land-012",
            cultivatorPartyId = "party-001",
            landownerPartyId = "party-002",
            cropName = "Rice",
            landArea = 1.0,
            landAreaUnit = "acre",
            expectedYield = 0.0,
            expectedRevenue = 0.0
        )

        assertEquals(
            0.0,
            contract.expectedYield,
            0.001
        )

        assertEquals(
            0.0,
            contract.expectedRevenue,
            0.001
        )
    }

    @Test
    fun `contract status enum contains expected lifecycle states`() {
        val statuses = Contract.Status.entries.toSet()

        assertTrue(
            statuses.contains(Contract.Status.ACTIVE)
        )

        assertTrue(
            statuses.contains(Contract.Status.COMPLETED)
        )

        assertTrue(
            statuses.contains(Contract.Status.CANCELLED)
        )

        assertFalse(statuses.isEmpty())
    }
}
