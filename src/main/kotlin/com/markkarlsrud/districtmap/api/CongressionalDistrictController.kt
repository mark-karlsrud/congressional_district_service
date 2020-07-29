package com.markkarlsrud.districtmap.api

import com.markkarlsrud.districtmap.model.CongressionalDistrict
import com.markkarlsrud.districtmap.CongressionalDistrictService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/district")
internal class CongressionalDistrictController(
    private val service: CongressionalDistrictService
) {

    @RequestMapping("/map")
    @ResponseBody
    fun getDistrict(
        @RequestParam("latitude", required = true) latitude: String,
        @RequestParam("longitude", required = true) longitude: String
    ): CongressionalDistrict? {
        return service.getDistrict(latitude = latitude, longitude = longitude)
    }
}