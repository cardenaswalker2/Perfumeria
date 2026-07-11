package com.profumi.profumi.repositories;

import com.profumi.profumi.models.Perfume;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfumeRepository extends MongoRepository<Perfume, String> {
    List<Perfume> findByTipoPielIdeal(String tipoPielIdeal);
    List<Perfume> findByOcasion(String ocasion);
    List<Perfume> findByGenero(String genero);
    List<Perfume> findByNotasOlfativasContaining(String nota);
    List<Perfume> findByNombreContainingIgnoreCaseOrMarcaContainingIgnoreCase(String nombre, String marca);
}
