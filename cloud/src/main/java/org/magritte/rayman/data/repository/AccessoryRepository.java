package org.magritte.rayman.data.repository;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Integer> {

    @NotNull
    Optional<Accessory> findById(@NotNull Integer id);

    Optional<Accessory> findByName(@NotNull String name);
}
