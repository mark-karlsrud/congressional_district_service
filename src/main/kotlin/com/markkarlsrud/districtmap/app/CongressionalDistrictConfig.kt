package com.markkarlsrud.districtmap.app

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ResourceLoader
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.util.*
import javax.sql.DataSource

private const val PROPERTIES_FILE = "district_map.properties"
internal const val DISTRICT_PROPERTIES = "district"

internal const val CACHE_KEY = "district"

@Configuration
@EnableCaching // TODO customize caching
internal open class CongressionalDistrictConfig {
    @Bean(DISTRICT_PROPERTIES)
    internal open fun properties(
        resourceLoader: ResourceLoader
    ): Properties = Properties().apply {
        resourceLoader.classLoader?.getResource(PROPERTIES_FILE)?.openStream()?.run {
            load(this)
        }
    }

    @Bean
    open fun databaseClient(
        @Qualifier(DISTRICT_PROPERTIES) properties: Properties
    ): DataSource? = DriverManagerDataSource().apply {
        /*
        * Might need to add the geometry types to the connection
        * (conn as org.postgresql.PGConnection).addDataType("geometry", PGgeometry::class.java)
        * (conn as org.postgresql.PGConnection).addDataType("box3d", PGbox3d::class.java)
        */
        setDriverClassName("org.postgresql.Driver")
        url = "jdbc:postgresql://${properties.getProperty("host")}/${properties.getProperty("database")}"
        username = properties.getProperty("user")
        password = properties.getProperty("password")
    }

    @Bean
    open fun jdbcTemplate(
        dataSource: DataSource
    ): JdbcTemplate = JdbcTemplate(dataSource)

    @Bean("table")
    open fun databaseTable(
        @Qualifier(DISTRICT_PROPERTIES) properties: Properties
    ): String = properties.getProperty("table")
}