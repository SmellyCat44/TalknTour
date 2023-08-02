package com.tracysong.speechdemo.service;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.RankBy;
import com.tracysong.speechdemo.entity.Location;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {

    private static final int DEFAULT_RADIUS = 2000;
    private static final int LARGE_RADIUS = 10000;
    private static final String API_KEY = "AIzaSyCy6gHvkhbUHkNzBQdH9sd7WpOOOR-UAoU";

    private GeoApiContext getGooglePlaceApi() {
        return new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
    }

    public PlacesSearchResponse getPlacesNearBy(Location location, PlaceType type, int radius) {
        try {
            GeoApiContext context = this.getGooglePlaceApi();
            LatLng cur_location = new LatLng(location.getLatitude(), location.getLongitude()); // Replace with user's location
            PlacesSearchResponse response = PlacesApi.nearbySearchQuery(context, cur_location)
                    .radius(radius) // in meters
                    .type(type)
                    .rankby(RankBy.PROMINENCE)
                    .await();
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch nearby places", e);
        }
    }

    public PlacesSearchResponse getPlacesNearBy1(Location location, String keyword) {
        try {
            GeoApiContext context = this.getGooglePlaceApi();
            LatLng cur_location = new LatLng(location.getLatitude(), location.getLongitude()); // Replace with user's location
            PlacesSearchResponse response = PlacesApi.nearbySearchQuery(context, cur_location)
                    .keyword(keyword) // Replace with your keyword
                    .rankby(RankBy.DISTANCE)
                    .await();
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch nearby places", e);
        }
    }


//    public PlacesSearchResponse getRestaurantNearBy(Location location) {
//        return getPlacesNearBy(location, PlaceType.RESTAURANT, DEFAULT_RADIUS);
//    }
    public PlacesSearchResponse getRestaurantNearBy(Location location) {
        return getPlacesNearBy1(location, "restaurant"); // Replace "restaurant" with your keyword
    }

    public PlacesSearchResponse getLodgingNearBy(Location location) {
        return getPlacesNearBy(location, PlaceType.LODGING, DEFAULT_RADIUS);
    }

    public PlacesSearchResponse getAmusementParkNearBy(Location location) {
        return getPlacesNearBy(location, PlaceType.AMUSEMENT_PARK, LARGE_RADIUS);
    }

    public PlacesSearchResponse getParkNearBy(Location location) {
        return getPlacesNearBy(location, PlaceType.PARK, 5000);
    }

    public PlacesSearchResponse getNightClubNearBy(Location location) {
        return getPlacesNearBy(location, PlaceType.NIGHT_CLUB, 5000);
    }


    public PlacesSearchResponse getPublicStationNearBy(Location location) {
        PlaceType[] types = {PlaceType.BUS_STATION, PlaceType.SUBWAY_STATION, PlaceType.TRAIN_STATION};
        try {
            GeoApiContext context = this.getGooglePlaceApi();
            LatLng cur_location = new LatLng(location.getLatitude(), location.getLongitude()); // Replace with user's location
            PlacesSearchResponse response = PlacesApi.nearbySearchQuery(context, cur_location)
                    .radius(LARGE_RADIUS) // in meters
                    .type(types)
                    .await();
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch nearby places", e);
        }
    }
}
