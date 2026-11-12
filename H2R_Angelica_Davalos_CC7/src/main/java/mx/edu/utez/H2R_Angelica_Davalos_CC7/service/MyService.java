package mx.edu.utez.H2R_Angelica_Davalos_CC7.service;

import mx.edu.utez.H2R_Angelica_Davalos_CC7.controller.dto.CrearPedidoDTO;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.exeption.CustomException;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.EstadoPedido;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.Pedido;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.model.Producto;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.repository.PedidoRepository;
import mx.edu.utez.H2R_Angelica_Davalos_CC7.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MyService {
    private final PedidoRepository pedidoRepository;
    private final ProductoRepository productoRepository;

    public MyService(ProductoRepository productoRepository, PedidoRepository pedidoRepository){
        this.pedidoRepository=pedidoRepository;
        this.productoRepository=productoRepository;
    }

    @Transactional
    public Pedido crearPedido(CrearPedidoDTO payload){
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

        Producto product = new  Producto();
        product.setStock(payload.getCantidadSolicitada());
        product.setActivo(true);
        product.setPrecio(payload.getCantidadSolicitada()* product.getPrecio());
        product.setNombre(payload.getNombreClientee());
        product.setCategoria(product.getCategoria());




        return productoRepository.save(product);
    }

    public List conultarPedidosEstado(){
        return pedidoRepository.findAllByEstadoPedido();
    }




}
