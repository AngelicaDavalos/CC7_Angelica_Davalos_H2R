package mx.edu.utez.H2R_Angelica_Davalos_CC7.repository;

import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.EstadoPedido;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findAllByEstadoPedido();
}
