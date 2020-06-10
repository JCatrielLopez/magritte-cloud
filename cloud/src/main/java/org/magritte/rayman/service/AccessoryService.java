package org.magritte.rayman.service;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Accessory;
import org.magritte.rayman.data.repository.AccessoryRepository;
import org.magritte.rayman.exceptions.AccessoryNotFoundException;
import org.magritte.rayman.rest.response.AccessoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccessoryService {

    @Autowired
    private AccessoryRepository accessoryRepository;

    public Accessory getAccessoryById(@NotNull Integer id) {
        return accessoryRepository
                .findById(id)
                .orElseThrow(AccessoryNotFoundException::new);
    }

    public List<AccessoryResponse> getAccessories() {
        return accessoryRepository
                .findAll()
                .stream()
                .map(AccessoryResponse::new)
                .collect(Collectors.toList());
    }

    public void save(Accessory accessory) {
        accessoryRepository.save(accessory);
    }

    public void delete(Accessory accessory) {
        accessoryRepository.delete(accessory);
    }

    public Optional<Accessory> getAccessoryByName(@NotNull String name) {
        return accessoryRepository
                .findByName(name);
    }
}
