package com.markkarlsrud.districtmap

import com.markkarlsrud.districtmap.app.CACHE_KEY
import com.markkarlsrud.districtmap.database.CongressionalDistrictDao
import com.markkarlsrud.districtmap.model.CongressionalDistrict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

interface CongressionalDistrictService {
    fun getDistrict(
        latitude: String,
        longitude: String
    ): CongressionalDistrict?
}

@Service
internal open class CongressionalDistrictServiceImpl(
    private val congressionalDistrictDao: CongressionalDistrictDao
): CongressionalDistrictService {
    @Cacheable(value = [CACHE_KEY])
    override fun getDistrict(
        latitude: String,
        longitude: String
    ): CongressionalDistrict? {
        return congressionalDistrictDao.getDistrict(latitude = latitude, longitude = longitude)
    }
}