package com.profumi.profumi.repositories;

import com.profumi.profumi.models.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface PedidoRepository extends MongoRepository<Pedido, String> {
    List<Pedido> findByEstado(String estado);
}
