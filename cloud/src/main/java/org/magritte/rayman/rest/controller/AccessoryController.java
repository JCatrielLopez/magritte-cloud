package org.magritte.rayman.rest.controller;

import org.magritte.rayman.data.entity.Accessory;
import org.magritte.rayman.data.entity.Data;
import org.magritte.rayman.rest.request.AccessoryRequest;
import org.magritte.rayman.rest.request.DatasRequest;
import org.magritte.rayman.rest.response.AccessoryResponse;
import org.magritte.rayman.rest.response.DataResponse;
import org.magritte.rayman.rest.response.DatasResponse;
import org.magritte.rayman.service.AccessoryService;
import org.magritte.rayman.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(name = "/accessory")
public class AccessoryController {

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private DataService dataService;

    @GetMapping("/accessories")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional(rollbackOn = Exception.class)
    public List<AccessoryResponse> getAccessories() {
        return accessoryService.getAccessories()
                .stream()
                .map(AccessoryResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/accessory/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional(rollbackOn = Exception.class)
    // https://stackoverflow.com/questions/15359306/how-to-fetch-fetchtype-lazy-associations-with-jpa-and-hibernate-in-a-spring-cont
    public AccessoryResponse getRoutine(@PathVariable Integer id) {
        Accessory accessory = accessoryService.getAccessoryById(id);
        return new AccessoryResponse(accessory);
    }

    @PostMapping("/accessory")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional(rollbackOn = Exception.class)
    public AccessoryResponse addAccessory(@RequestBody @Valid AccessoryRequest request) {
        // En estos casos no lanzo error porque en caso de que no exista, lo inserto sino lo actualizo.
        Optional<Accessory> optionalAccessory = accessoryService.getAccessoryByName(request.getName());
        Accessory accessory = optionalAccessory.orElseGet(request::toNewEntity);
        accessoryService.save(accessory, request.getData());
        return new AccessoryResponse(accessory);
    }

    @PostMapping("/accessory/{id}/data")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional(rollbackOn = Exception.class)
    public DatasResponse addData(@PathVariable Integer id, @RequestBody @Valid DatasRequest request) {
        Accessory accessory = accessoryService.getAccessoryById(id);
        Set<DataResponse> dataResponses = accessoryService.save(accessory, request.getData());
        return new DatasResponse(dataResponses);
    }

    @DeleteMapping("/accessory/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeAccessory(@PathVariable Integer id) {
        Accessory accessory = Optional.of(id)
                .map(accessoryService::getAccessoryById)
                .orElseThrow(NoSuchElementException::new);
        accessoryService.delete(accessory);
    }

    @DeleteMapping("/accessory/{idAccessory}/data/{idData}")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional(rollbackOn = Exception.class)
    public void removeAccessoryData(@PathVariable Integer idAccessory, @PathVariable Integer idData) {
        Accessory accessory = accessoryService.getAccessoryById(idAccessory);

        Optional<Data> dataOpt = accessory.getData().stream()
                .filter(data -> data.getIdData().equals(idData))
                .findFirst();

        Data dataToRemove = dataOpt.orElseThrow(NoSuchElementException::new);
        accessory.getData().remove(dataToRemove);
        accessoryService.save(accessory);
    }

}
