package com.markkarlsrud.districtmap.database

import com.markkarlsrud.districtmap.model.CongressionalDistrict
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
internal open class CongressionalDistrictDao(
    private val jdbcTemplate: JdbcTemplate,
    @Qualifier("table") private val table: String
) {
    open fun getDistrict(latitude: String, longitude: String): CongressionalDistrict? {
        val collect: (ResultSet) -> CongressionalDistrict = {
            it.next()
            CongressionalDistrict(it)
        }
        return jdbcTemplate.
            query(
                "SELECT * FROM $table as a WHERE ST_CONTAINS(a.geom, ST_GeomFromText('Point($longitude $latitude)'))",
                collect
            )
    }
}
