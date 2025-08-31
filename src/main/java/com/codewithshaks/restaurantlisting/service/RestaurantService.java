package com.codewithshaks.restaurantlisting.service;

import com.codewithshaks.restaurantlisting.dto.RestaurantDTO;
import com.codewithshaks.restaurantlisting.entity.Restaurant;
import com.codewithshaks.restaurantlisting.mapper.RestaurantMapper;
import com.codewithshaks.restaurantlisting.repo.RestaurantRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    RestaurantRepo restaurantRepo;

    public List<RestaurantDTO> findAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepo.findAll();
        //map it RestaurantDTO
        List<RestaurantDTO> restaurantDTOList = restaurants.stream()
                .map(restaurant -> RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(restaurant))
                .toList();
        return restaurantDTOList;
    }


    public RestaurantDTO saveRestaurant(RestaurantDTO restaurantDTO) {
        Restaurant restaurantToBeAdded = RestaurantMapper.INSTANCE.mapRestaurantDTOToRestaurant(restaurantDTO);
        Restaurant addedRestaurant = restaurantRepo.save(restaurantToBeAdded);
        return RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(addedRestaurant);
    }


    public ResponseEntity<RestaurantDTO> findRestaurantById(Integer id) {
        Optional<Restaurant> restaurant = restaurantRepo.findById(id);
        if (restaurant.isPresent()) {
            Restaurant rest=  restaurant.get();
            RestaurantDTO foundRestaurant = RestaurantMapper.INSTANCE.mapRestaurantToRestaurantDTO(rest);
            return new ResponseEntity<>(foundRestaurant, HttpStatus.OK);
        }
        else  {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }
}
