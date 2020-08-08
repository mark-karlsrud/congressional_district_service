package com.markkarlsrud.districtmap.model

import com.fasterxml.jackson.annotation.JsonFormat
import org.postgis.PGgeometry
import java.sql.ResultSet

data class CongressionalDistrict(
    val gid: Int,
    val state: StateFederalInformationProcessingSeries,
    val districtProcessingSeriesCode: String,
    val geoid: String,
    val description: String,
    val lsad: LegalStatisticalAreaDescription,
    private val cdsessn: CongressionalDistrictSessionCode,
    private val mtfcc: MafTigerFeatureClassCode,
    private val funcstat: FunctionalStatus,
    val landAreaSquareMeters: String,
    val waterAreaSquareMeters: String,
    val internalPoint: GeoLocation,
    private val geom: PGgeometry
) {
    companion object {
        operator fun invoke(result: ResultSet) =
            CongressionalDistrict(
                result.getInt("gid"),
                result.getString("statefp").let {
                    StateFederalInformationProcessingSeries.get(
                        it
                    )
                },
                result.getString("cd116fp"),
                result.getString("geoid"),
                result.getString("namelsad"),
                result.getString("lsad").let {
                    LegalStatisticalAreaDescription.get(
                        it
                    )
                },
                result.getInt("cdsessn").let {
                    CongressionalDistrictSessionCode.get(
                        it
                    )
                },
                result.getString("mtfcc").let {
                    MafTigerFeatureClassCode.get(it)
                },
                result.getString("funcstat")
                    .let { FunctionalStatus.get(it) },
                result.getString("aland"),
                result.getString("awater"),
                GeoLocation(
                    latitude = result.getString("intptlat"),
                    longitude = result.getString("intptlon")
                ),
                result.getObject("geom") as PGgeometry
            )
    }

    val districtProcessingSeriesDescription = when(districtProcessingSeriesCode) {
        "00" -> "At Large Representation"
        "98" -> "Non-Voting Delegate"
        "ZZ" -> "Congressional District Not Defined"
        else -> "Congressional District (in states with more than one district)"
    }
}

enum class LegalStatisticalAreaDescription(val value: String, val description: String) {
    Blank("00", "Blank"),
    CongressionalDistrictAtLarge("C1", "Congressional District (at Large)"),
    CongressionalDistrictPrefix("C2", "Congressional District (prefix)"),
    ResidentCommissionerDistrict("C3", "Resident Commissioner District (at Large)"),
    DelegateDistrict("C4", "Delegate District (at Large)");

    companion object {
        fun get(value: String) = values().first { it.value == value }
    }
}

enum class CongressionalDistrictSessionCode(val value: Int) {
    Congress116(116);

    companion object {
        fun get(value: Int) = values().first { it.value == value }
    }
}

enum class MafTigerFeatureClassCode(val value: String, val description: String) {
    CongressionalDistrict("G5200", "Congressional District");

    companion object {
        fun get(value: String) = values().first { it.value == value }
    }
}

enum class FunctionalStatus(val value: String, val description: String) {
    Nonfunctional("N", "Nonfunctioning legal entity"),
    Functional("F", "Functioning legal entity");

    companion object {
        fun get(value: String) = values().first { it.value == value }
    }
}

data class GeoLocation(
    val latitude: String,
    val longitude: String
)

/**
 * https://www.nrcs.usda.gov/wps/portal/nrcs/detail/?cid=nrcs143_013696
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class StateFederalInformationProcessingSeries(val value: String, val description: String) {
    // TODO add state codes like AZ
    ALABAMA("01", "Alabama"),
    ALASKA("02", "Alaska"),
    ARIZONA("04", "Arizona"),
    ARKANSAS("05", "Arkansas"),
    CALIFORNIA("06", "California"),
    COLORADO("08", "Colorado"),
    CONNECTICUT("09", "Connecticut"),
    DELAWARE("10", "Delaware"),
    DC("11", "District of Columbia"),
    FLORIDA("12", "Florida"),
    GEORGIA("13", "Georgia"),
    HAWAII("15", "Hawaii"),
    IDAHO("16", "Idaho"),
    ILLINOIS("17", "Illinois"),
    INDIANA("18", "Indiana"),
    IOWA("19", "Iowa"),
    KANSAS("20", "Kansas"),
    KENTUCKY("21", "Kentucky"),
    LOUISIANA("22", "Louisiana"),
    MAINE("23", "Maine"),
    MARYLAND("24", "Maryland"),
    MASSACHUSETTS("25", "Massachusetts"),
    MICHIGAN("26", "Michigan"),
    MINNESOTA("27", "Minnesota"),
    MISSISSIPPI("28", "Mississippi"),
    MISSOURI("29", "Missouri"),
    MONTANA("30", "Montana"),
    NEBRASKA("31", "Nebraska"),
    NEVADA("32", "Nevada"),
    NEWHAMPSHIRE("33", "New Hampshire"),
    NEWJERSEY("34", "New Jersey"),
    NEWMEXICO("35", "New Mexico"),
    NEWYORK("36", "New York"),
    NORTHCAROLINA("37", "North Carolina"),
    NORTHDAKOTA("38", "North Dakota"),
    OHIO("39", "Ohio"),
    OKLAHOMA("40", "Oklahoma"),
    OREGON("41", "Oregon"),
    PENNSYLVANIA("42", "Pennsylvania"),
    RHODEISLAND("44", "Rhode Island"),
    SOUTHCAROLINA("45", "South Carolina"),
    SOUTHDAKOTA("46", "South Dakota"),
    TENNESSEE("47", "Tennessee"),
    TEXAS("48", "Texas"),
    UTAH("49", "Utah"),
    VERMONT("50", "Vermont"),
    VIRGINIA("51", "Virginia"),
    WASHINGTON("53", "Washington"),
    WESTVIRGINIA("54", "West Virginia"),
    WISCONSIN("55", "Wisconsin"),
    WYOMING("56", "Wyoming"),
    AS("60", "American Samoa"),
    GU("66", "Guam"),
    MP("69", "Northern Mariana Islands"),
    PR("72", "Puerto Rico"),
    VI("78", "Virgin Islands");

    companion object {
        fun get(value: String) = values().first { it.value == value }
    }
}