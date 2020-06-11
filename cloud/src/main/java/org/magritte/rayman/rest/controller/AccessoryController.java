package org.magritte.rayman.rest.controller;

import org.magritte.rayman.data.entity.Accessory;
import org.magritte.rayman.data.entity.Data;
import org.magritte.rayman.exceptions.AccessoryNotFoundException;
import org.magritte.rayman.rest.request.AccessoryRequest;
import org.magritte.rayman.rest.response.AccessoryResponse;
import org.magritte.rayman.service.AccessoryService;
import org.magritte.rayman.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(name = "/accessory")
public class AccessoryController {

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private DataService dataService;

    @GetMapping("/accessory/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    // https://stackoverflow.com/questions/15359306/how-to-fetch-fetchtype-lazy-associations-with-jpa-and-hibernate-in-a-spring-cont
    public AccessoryResponse getRoutine(@PathVariable Integer id) {
        Accessory accessory = accessoryService.getAccessoryById(id);
        return new AccessoryResponse(accessory);
    }

    @GetMapping("/accessories")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public List<AccessoryResponse> getAccessories() {
        return accessoryService.getAccessories();
    }

    @PostMapping("/accessory")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public void addAccessory(@RequestBody @Valid AccessoryRequest request) {
        /*
            En estos casos no lanzo error porque en caso de que no exista, lo inserto sino lo actualizo.
         */
        Optional<Accessory> optionalAccessory = Optional.of(request.getName())
                .flatMap(accessoryService::getAccessoryByName);
        Accessory accessory;
        if (optionalAccessory.isPresent()) {
            accessory = optionalAccessory.get();
            accessory.addAll(request.getCollect());
        } else {
            accessory = request.toNewEntity();
        }
        accessoryService.save(accessory);
    }

    @DeleteMapping("/accessory/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeAccessory(@PathVariable Integer id) {
        Accessory accessory = Optional.of(id)
                .map(accessoryService::getAccessoryById)
                .orElseThrow(AccessoryNotFoundException::new);
        accessoryService.delete(accessory);
    }

    @DeleteMapping("/accessory/{idAccessory}/data/{idData}")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public void removeAccessoryData(@PathVariable Integer idAccessory, @PathVariable Integer idData) {
        Accessory accessory = Optional.of(idAccessory)
                .map(accessoryService::getAccessoryById)
                .orElseThrow(AccessoryNotFoundException::new);
        Set<Data> newData = accessory.getData().stream()
                .filter(data -> !Objects.equals(data.getIdData(), idData))
                .collect(Collectors.toSet());
        accessory.setData(newData);
        Data data = dataService.getDataById(idData);
        dataService.delete(data);
        accessoryService.save(accessory);
    }

}
