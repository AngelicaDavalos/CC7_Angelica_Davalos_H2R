package mx.edu.utez.H2R_Angelica_Davalos_CC7.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.EstadoPedido;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRespuestaDTO {
    private Long id;
    private String nombreCliente;
    private Integer cantidad;
    private Double precioUnitario;
    private Double total;
    private EstadoPedido estado;
    private ProductoDTO productoDTO;
}
