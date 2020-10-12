package dev.sukanya.gamecourtbooking.controller;

import dev.sukanya.gamecourtbooking.dto.court.CourtDTO;
import dev.sukanya.gamecourtbooking.dto.ResponseDTO;
import dev.sukanya.gamecourtbooking.dto.timeslot.TimeSlotResponseDTO;
import dev.sukanya.gamecourtbooking.model.courts.Court;
import dev.sukanya.gamecourtbooking.service.interfaces.CourtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@Slf4j //--> for logging
@RestController //--> we can pass JSON objects too
//if @Controller --> expects we will give name of html page we want to view
public class CourtController {

    @Autowired
    private CourtService courtService;

    @PostMapping("/court/addCourt")
    public ResponseDTO<?> addCourt(@RequestBody @Valid CourtDTO courtDTO, BindingResult result){
        log.info("Received Request for adding new court" );
        List<String> errorMessages = new ArrayList<String>();
        if(checkForErrors(result, errorMessages)){
            return new ResponseDTO<List<String>>(HttpStatus.BAD_REQUEST, errorMessages);
        }
        try{
            Court court= courtService.addCourt(courtDTO);
            return new ResponseDTO<String>(HttpStatus.OK, "Court added successfully!");
        }catch( Exception e){
            return new ResponseDTO<String>(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @GetMapping("/court/allCourts")
    public ResponseDTO<?> getAllCourts(){
        log.info("Received Request for getting all courts" );

        try{
            List<Court> courts= courtService.getAllCourts();
            return new ResponseDTO<List<Court>>(HttpStatus.OK, courts);
        }catch( Exception e){
            return new ResponseDTO<String>(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @GetMapping("/court/timeSlots/{id}")
    public ResponseDTO<?> getTimeSlotsOfCourt(@PathVariable("id") int courtId){
        log.info("Received Request for getting all time slots of a court" );

        try{
            List<TimeSlotResponseDTO> timeSlots= courtService.getTimeSlotsOfCourt(courtId);
            return new ResponseDTO<List<TimeSlotResponseDTO>>(HttpStatus.OK, timeSlots);
        }catch( Exception e){
            return new ResponseDTO<String>(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @GetMapping("/court/cityCourts/{city}")
    public ResponseDTO<?> getCourtsByCity(@PathParam("city") String city){
        log.info("Received Request for getting all courts of a city" );

        try{
            List<Court> courts= courtService.getAllCourtsByCity(city);
            return new ResponseDTO<List<Court>>(HttpStatus.OK, courts);
        }catch( Exception e){
            return new ResponseDTO<String>(HttpStatus.BAD_REQUEST, e.getMessage());
        }

    }

    @GetMapping("/court/stateCourts/{state}")
    public ResponseDTO<?> getCourtsByState(@PathParam("state") String state){
        log.info("Received Request for getting all courts of a state" );

        try{
            List<Court> courts= courtService.getAllCourtsByState(state);
            return new ResponseDTO<List<Court>>(HttpStatus.OK, courts);
        }catch( Exception e){
            return new ResponseDTO<String>(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/court/countryCourts/{country}")
    public ResponseDTO<?> getAllCourtsByCountry(@PathParam("country") String country){
        log.info("Received Request for getting all courts of a country" );

        try{
            List<Court> courts= courtService.getAllCourtsByCountry(country);
            return new ResponseDTO<List<Court>>(HttpStatus.OK, courts);
        }catch( Exception e){
            return new ResponseDTO<String>(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    public boolean checkForErrors(BindingResult result, List<String> errorMessages){
        if (result.hasErrors()) {
            List<ObjectError> errors = result.getAllErrors();

            for (ObjectError error:
                    errors) {
                errorMessages.add(error.getDefaultMessage());
            }
            return true;
        }
        return false;
    }
}