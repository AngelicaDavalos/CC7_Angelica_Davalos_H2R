package mx.edu.utez.H2R_Angelica_Davalos_CC7.controller;

import jakarta.validation.Valid;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.controller.dto.CrearPedidoDTO;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.Pedido;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.repository.PedidoRepository;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.service.MyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controlador")
@CrossOrigin("*"
)
public class MyController {
    private final MyService servicio;

    public MyController(MyService servicio){
        this.servicio=servicio;
    }

    @PostMapping("/crearPedido")
    public ResponseEntity<Pedido>crearPedido(@RequestBody @Valid CrearPedidoDTO payload){
        return   ResponseEntity
                .status(HttpStatus.CREATED)
                .body(servicio.crearPedido(payload));
    }
    public ResponseEntity<Pedido>conultarPedidoEstado(){
        return ResponseEntity
                .status(200)
                .body(servicio.conultarPedidosEstado());
    }
}
