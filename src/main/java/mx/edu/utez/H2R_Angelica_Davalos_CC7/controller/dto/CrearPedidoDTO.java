package mx.edu.utez.H2R_Angelica_Davalos_CC7.controller.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearPedidoDTO {

    @NotNull(message = "el id no puede ir nulo ")
    private Long idProducto;
    @NotBlank(message = "el nombre no puede ir vacio ")
    private String nombreClientee;
    @NotNull(message = "la cantidad solicitada no puede ir vacia")
    @Min(value = 1, message = "la cantidad deve ser mayor a cero")
    private Integer cantidadSolicitada;

}
