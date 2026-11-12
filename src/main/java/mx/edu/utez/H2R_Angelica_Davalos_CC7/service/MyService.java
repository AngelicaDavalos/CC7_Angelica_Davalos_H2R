package mx.edu.utez.H2R_Angelica_Davalos_CC7.service;

import mx.edu.utez.H2R_Angelica_Davalos_CC7.controller.dto.CrearPedidoDTO;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.controller.dto.PedidoRespuestaDTO;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.controller.dto.ProductoDTO;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.exeption.CustomException;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.EstadoPedido;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.Pedido;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.Producto;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.repository.PedidoRepository;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyService {
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public MyService(ProductoRepository productoRepository, PedidoRepository pedidoRepository){
        this.pedidoRepository=pedidoRepository;
        this.productoRepository=productoRepository;
    }

    @Transactional
    public PedidoRespuestaDTO crearPedido(CrearPedidoDTO payload){
        Producto producto = productoRepository.findById(payload.getIdProducto())
                .orElseThrow(
                        ()-> new CustomException("error")

                );
        if (!productoRepository.existsById(payload.getIdProducto())){
            throw new CustomException("el producto no existe ");
        }

        if (!producto.isActivo()){
            throw  new CustomException("El producto no esta activo ");
        }

        if(producto.getStock()>10){
            throw  new CustomException("No hay suficiente producto en stock");
        }

       Double precioUnitario= producto.getPrecio();
        Double total = precioUnitario*payload.getCantidadSolicitada();

        producto.setStock(producto.getStock()-payload.getCantidadSolicitada());
        productoRepository.save(producto);

        Pedido pedido= new Pedido();
        pedido.setNombreCliente(payload.getNombreClientee());
        pedido.setCantidad(payload.getCantidadSolicitada());
        pedido.setPrescioUnitario(precioUnitario);
        pedido.setTotal(total);
        pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
        pedido.setProducto(producto);

        Pedido guardado = pedidoRepository.save(pedido);

        return convertirPedidoADTO(guardado);
    }

    @Transactional
    public PedidoRespuestaDTO cancelarPedido(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(
                        ()-> new CustomException("el pedido no exste")
                );

        if (pedido.getEstadoPedido() != EstadoPedido.PENDIENTE){
            throw  new CustomException("solo se pueden cancelar pedidos en estaso pendiente");
        }
        pedido.setEstadoPedido(EstadoPedido.CANCELADO);

        Producto producto = pedido.getProducto();
        producto.setStock(producto.getStock()+pedido.getCantidad());
        productoRepository.save(producto);

        Pedido guardado = pedidoRepository.save(pedido);
        return convertirPedidoADTO(guardado);
    }

    public  PedidoRespuestaDTO procesarPedido(Long id ){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(
                        ()->new CustomException("El pedido no exixte ")
                );

        if (pedido.getEstadoPedido() != EstadoPedido.PENDIENTE){
            throw new CustomException("solo se pueden procesar pedidos Pendientes");
        }
        pedido.setEstadoPedido(EstadoPedido.PROCESADO);
        Pedido guardado = pedidoRepository.save(pedido);
        return convertirPedidoADTO(guardado);
    }

    public  void desactivarProducto(Long id){
        Producto producto = productoRepository.findById(id)
                .orElseThrow(
                        ()->new CustomException("el producto no existe")
                );

        if (pedidoRepository.existsByProductoIdAndEstado(id,EstadoPedido.PENDIENTE)){
            throw  new CustomException("no se puede desactivar un producto con pedidos pendientes");
        }
        producto.setActivo(false);
        productoRepository.save(producto);
    }

    @Transactional
    public void cancelarPedidosPendientes(Long productoId) {
        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new CustomException("El producto no existe"));

        List<Pedido> pedidosPendientes = pedidoRepository.findByProductoIdAndEstado(productoId, EstadoPedido.PENDIENTE);

        for (Pedido pedido : pedidosPendientes) {
            pedido.setEstadoPedido(EstadoPedido.CANCELADO);
            producto.setStock(producto.getStock() + pedido.getCantidad());
            pedidoRepository.save(pedido);
        }

        productoRepository.save(producto);
    }


    public List<PedidoRespuestaDTO> consultarPedidosPorEstado(String estado) {
        String valor = estado.toUpperCase();

        if (!valor.equals("PENDIENTE") && !valor.equals("PROCESADO") && !valor.equals("CANCELADO")) {
            throw new CustomException("Estado inválido");
        }

        return pedidoRepository.findByEstadoPedido(EstadoPedido.valueOf(valor))
                .stream()
                .map(this::convertirPedidoADTO)
                .toList();
    }

    public List<ProductoDTO> listarTodosProductos() {
        return productoRepository.findAll()
                .stream()
                .map(this::convertirProductoADTO)
                .collect(Collectors.toList());
    }

    public List<ProductoDTO> listarProductosActivos() {
        return productoRepository.findByActivoTrue()
                .stream()
                .map(this::convertirProductoADTO)
                .collect(Collectors.toList());
    }




    private ProductoDTO convertirProductoADTO(Producto producto) {
        return new ProductoDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getCategoria(),
                producto.getPrecio(),
                producto.getStock(),
                producto.isActivo()
        );
    }

    private PedidoRespuestaDTO convertirPedidoADTO(Pedido pedido) {
        return new PedidoRespuestaDTO(
                pedido.getId(),
                pedido.getNombreCliente(),
                pedido.getCantidad(),
                pedido.getPrescioUnitario(),
                pedido.getTotal(),
                pedido.getEstadoPedido(),
                convertirProductoADTO(pedido.getProducto())
        );
    }

}
