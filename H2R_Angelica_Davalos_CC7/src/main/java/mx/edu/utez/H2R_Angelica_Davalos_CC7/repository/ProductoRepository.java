package mx.edu.utez.H2R_Angelica_Davalos_CC7.repository;

import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository  extends JpaRepository<Producto, Long> {
}
