package com.socialcontract.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Database migrations for the Social Contract application.
 *
 * Version 1 is the initial schema, so no migration is required yet.
 * Future schema changes should be added here rather than modifying
 * existing migration definitions.
 */
object DatabaseMigrations {

    /**
     * Migration from database version 1 to version 2.
     *
     * This placeholder should only be enabled once the schema actually
     * changes and the corresponding SQL statements are defined.
     */
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {

        override fun migrate(database: SupportSQLiteDatabase) {
            // Reserved for the first schema migration.
        }
    }
}
