package mx.edu.utez.H2R_Angelica_Davalos_CC7.controller;

import jakarta.validation.Valid;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.controller.dto.CrearPedidoDTO;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.controller.dto.PedidoRespuestaDTO;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.controller.dto.ProductoDTO;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.Pedido;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.repository.PedidoRepository;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.service.MyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/controlador")
@CrossOrigin("*"
)
public class MyController {
    private final MyService servicio;

    public MyController(MyService servicio){
        this.servicio=servicio;
    }

    @PostMapping("/pedidos")
    public ResponseEntity<PedidoRespuestaDTO> crearPedido(@RequestBody @Valid CrearPedidoDTO payload) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicio.crearPedido(payload));
    }

    @PutMapping("/pedidos/{id}/cancelar")
    public ResponseEntity<PedidoRespuestaDTO> cancelarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.cancelarPedido(id));
    }

    @PutMapping("/pedidos/{id}/procesar")
    public ResponseEntity<PedidoRespuestaDTO> procesarPedido(@PathVariable Long id) {
        return ResponseEntity.ok(servicio.procesarPedido(id));
    }

    @PutMapping("/productos/{id}/desactivar")
    public ResponseEntity<Void> desactivarProducto(@PathVariable Long id) {
        servicio.desactivarProducto(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/productos/{id}/cancelar-pendientes")
    public ResponseEntity<Void> cancelarPedidosPendientes(@PathVariable Long id) {
        servicio.cancelarPedidosPendientes(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/pedidos/estado/{estado}")
    public ResponseEntity<List<PedidoRespuestaDTO>> consultarPedidosPorEstado(@PathVariable String estado) {
        return ResponseEntity.ok(servicio.consultarPedidosPorEstado(estado));
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> listarTodosProductos() {
        return ResponseEntity.ok(servicio.listarTodosProductos());
    }

    @GetMapping("/productos/activos")
    public ResponseEntity<List<ProductoDTO>> listarProductosActivos() {
        return ResponseEntity.ok(servicio.listarProductosActivos());
    }
}
