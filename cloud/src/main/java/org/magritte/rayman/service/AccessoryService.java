package org.magritte.rayman.service;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Accessory;
import org.magritte.rayman.data.entity.Data;
import org.magritte.rayman.data.repository.AccessoryRepository;
import org.magritte.rayman.data.repository.DataRepository;
import org.magritte.rayman.exceptions.AccessoryNotFoundException;
import org.magritte.rayman.rest.request.DataRequest;
import org.magritte.rayman.rest.response.AccessoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AccessoryService {

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Autowired
    private DataRepository dataRepository;

    public Accessory getAccessoryById(@NotNull Integer id) {
        return accessoryRepository
                .findById(id)
                .orElseThrow(AccessoryNotFoundException::new);
    }

    public List<AccessoryResponse> getAccessories() {
        return accessoryRepository.findAll().stream()
                .map(AccessoryResponse::new)
                .collect(Collectors.toList());
    }

    public void save(@NotNull Accessory accessory,@NotNull Set<DataRequest> requests) {
        requests.forEach(dataRequest -> {
            Optional<Data> optionalData = dataRepository.findByDataType(dataRequest.getDataType());
            Data data = optionalData.orElseGet(dataRequest::toNewEntity);
            data.add(accessory);
            accessory.add(data);
            if (optionalData.isEmpty()) dataRepository.save(data);
        });
        accessoryRepository.save(accessory);
    }

    public void save(@NotNull Accessory accessory) {
        accessoryRepository.save(accessory);
    }

    public void delete(@NotNull Accessory accessory) {
        accessoryRepository.delete(accessory);
    }

    public Optional<Accessory> getAccessoryByName(@NotNull String name) {
        return accessoryRepository.findByName(name);
    }
}
