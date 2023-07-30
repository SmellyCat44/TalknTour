package com.tracysong.speechdemo.controller;

import com.google.maps.GeoApiContext;
import com.google.maps.model.PlacesSearchResponse;
import com.tracysong.speechdemo.entity.Location;
import com.tracysong.speechdemo.service.PlaceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "place")
@RequestMapping("/place")
@RestController
@CrossOrigin(origins = "http://localhost:8081")
public class PlaceController {

    @Autowired
    PlaceService placeService;

    @ApiOperation(value = "restaurant")
    @PostMapping("/restaurant_nearby")
    public PlacesSearchResponse getRestaurantNearBy(@RequestBody Location location) {
        return placeService.getRestaurantNearBy(location);
    }

    @ApiOperation(value = "lodging")
    @PostMapping("/lodging_nearby")
    public PlacesSearchResponse getLodgingNearBy(@RequestBody Location location) {
        return placeService.getLodgingNearBy(location);
    }

    @ApiOperation(value = "amusement_park")
    @PostMapping("/amusement_park_nearby")
    public PlacesSearchResponse getAmusementParkNearBy(@RequestBody Location location) {
        return placeService.getAmusementParkNearBy(location);
    }

    @ApiOperation(value = "park")
    @PostMapping("/park_nearby")
    public PlacesSearchResponse getParkNearBy(@RequestBody Location location) {
        return placeService.getParkNearBy(location);
    }

    @ApiOperation(value = "night_club")
    @PostMapping("/night_club_nearby")
    public PlacesSearchResponse getNightClubNearBy(@RequestBody Location location) {
        return placeService.getNightClubNearBy(location);
    }

    @ApiOperation(value = "public_station")
    @PostMapping("/public_station")
    public PlacesSearchResponse getPublicStationNearBy(@RequestBody Location location) {
        return placeService.getPublicStationNearBy(location);
    }


}
