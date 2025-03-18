package com.example.sandwich.domain.compra;

import com.example.sandwich.domain.PersistenceId;
import com.example.sandwich.domain.productos.Prodcuto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Carrito extends PersistenceId {
  	@OneToMany //quiero que ver bidireccional o udireccional? -> necesito que producto conozca a CARRITO? NO! -> UNIDIRECCIONAL
    //entonces una anotacion MANYtoOne en ITEM
		private List<Item> itemsProductos;

    private float precioTotalAbonar;

    @OneToOne(mappedBy = "carritoCompras",cascade = CascadeType.ALL)
    private Comprador comprador;


}
