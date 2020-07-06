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

/**
 * EndPoints para atender peticiones asociadas a accesorios
 */
@RestController
@RequestMapping(name = "/accessory")
@Transactional(rollbackOn = Exception.class)
public class AccessoryController {

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private DataService dataService;

    /**
     * Obtiene la informacion de todos los accesorios.
     *
     * @return Una lista de accesorios, o una lista vacia en caso de que no existe ninguno.
     */
    @GetMapping("/accessories")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<AccessoryResponse> getAccessories() {
        return accessoryService.getAccessories()
                .stream()
                .map(AccessoryResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene la informacion de un accesorio con el ID dado.
     *
     * @param id Id del accesorio
     * @return El accesorio solicitado, o si no existe NOT FOUND.
     */
    @GetMapping("/accessory/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public AccessoryResponse getAccesory(@PathVariable Integer id) {
        Accessory accessory = accessoryService.getAccessoryById(id);
        return new AccessoryResponse(accessory);
    }

    /**
     * Agrega un accesorio.
     *
     * @param request Informacion necesaria para agregar un nuevo accesorio.
     * @return El accesorio agregado.
     */
    @PostMapping("/accessory")
    @ResponseStatus(code = HttpStatus.OK)
    public AccessoryResponse addAccessory(@RequestBody @Valid AccessoryRequest request) {
        // En estos casos no lanzo error porque en caso de que no exista, lo inserto sino lo actualizo.
        Optional<Accessory> optionalAccessory = accessoryService.getAccessoryByName(request.getName());
        Accessory accessory = optionalAccessory.orElseGet(request::toNewEntity);
        accessoryService.save(accessory, request.getData());
        return new AccessoryResponse(accessory);
    }

    /**
     * Agrega datos para un accesorio para el ID dado.
     *
     * @param id      Id del accesorio.
     * @param request Informacion necesaria de los datos a ser agregados.
     * @return Los datos agregados, o si no existe el accesorio NOT FOUND.
     */
    @PostMapping("/accessory/{id}/data")
    @ResponseStatus(code = HttpStatus.OK)
    public DatasResponse addData(@PathVariable Integer id, @RequestBody @Valid DatasRequest request) {
        Accessory accessory = accessoryService.getAccessoryById(id);
        Set<DataResponse> dataResponses = accessoryService.save(accessory, request.getData());
        return new DatasResponse(dataResponses);
    }

    /**
     * Elimina un accesorio con el ID dado.
     *
     * @param id Id del accesorio.
     */
    @DeleteMapping("/accessory/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeAccessory(@PathVariable Integer id) {
        Accessory accessory = Optional.of(id)
                .map(accessoryService::getAccessoryById)
                .orElseThrow(NoSuchElementException::new);
        accessoryService.delete(accessory);
    }

    /**
     * Elimina los datos con el ID dado para un accesorio.
     *
     * @param idAccessory Id del accesorio.
     * @param idData      ID de los datos.
     */
    @DeleteMapping("/accessory/{idAccessory}/data/{idData}")
    @ResponseStatus(code = HttpStatus.OK)
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
