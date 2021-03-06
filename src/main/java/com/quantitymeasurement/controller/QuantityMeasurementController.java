package com.quantitymeasurement.controller;

import com.quantitymeasurement.enumeration.Measurement;
import com.quantitymeasurement.enumeration.Unit;
import com.quantitymeasurement.model.QuantityMeasurement;
import com.quantitymeasurement.model.Response;
import com.quantitymeasurement.service.QuantityMeasurementService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;

import static com.quantitymeasurement.enumeration.Message.SUCCESSFUL;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(produces = {APPLICATION_JSON_VALUE,APPLICATION_XML_VALUE})
public class QuantityMeasurementController {

    @Autowired
    QuantityMeasurementService quantityMeasurementService;

    /**
     *
     * @param unit  is given measurement unit
     * @param value is given quantity
     * @param requiredUnit is the unit to convert
     * @return Response Entity with result and status
     */
    @ApiOperation(value = "Get converted value of given quantity measure")
    @GetMapping("/quantity-measurement/{unit}/{value}/{requiredUnit}")
    public ResponseEntity<Response> getConvertedValue(@PathVariable("unit") Unit unit, @PathVariable("value") Double value,
                                                      @PathVariable("requiredUnit") Unit requiredUnit) {
        double result = quantityMeasurementService.convertTo(new QuantityMeasurement(value, unit), requiredUnit);
        return new ResponseEntity<>(new Response(result, SUCCESSFUL, 200),HttpStatus.OK);
    }

    /**
     *
     * @param measurementType is given Measurement
     * @return Response Entity with list of available units of given Measurement type ans status
     */
    @GetMapping("/quantity-measurement/{measurementType}")
    @ApiOperation("View a list of available units of given measurement type")
    public ResponseEntity<Response> getUnits(@PathVariable("measurementType") Measurement measurementType) {
        List<Unit> units = quantityMeasurementService.getUnits(measurementType);
        return new ResponseEntity<>(new Response(units,SUCCESSFUL,200),HttpStatus.OK);
    }

    /**
     *
     * @return Response Entity with list of available measurement types and status
     */
    @GetMapping("/quantity-measurement")
    @ApiOperation("View a list of available measurement types")
    public ResponseEntity<Response> getMeasurements() {
        List<Measurement> measurementTypes = quantityMeasurementService.getMeasurementTypes();
        return new ResponseEntity<>(new Response(measurementTypes,SUCCESSFUL,200),HttpStatus.OK);
    }

}
