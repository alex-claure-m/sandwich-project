package com.example.sandwich.domain.compra;

import com.example.sandwich.domain.PersistenceId;
import com.example.sandwich.domain.productos.Prodcuto;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Setter
@Getter
public class Item extends PersistenceId {

  //cada carrito de compra contendra un producto y a su vez estara asociado con un comprador
  // un comprador contendra un carrito de compra y este carrito de compra tendra cada item de cada producto
  // que desee comprar
  /*
  * cuando un comprador decide agregar producto a su carrito
  * primero lo que sucede es que el el comprador agregra productos a su carrito pero no un producto perse
  * por que primero lo que agrega son los items de los productos (digamos que los que estan a la venta)
  * entonces la clase Item se relaciona a un producto en si
  * y la clase Comprador se relaciona con carrito de compras que tiene una lista de items
  * */
/*
  Clase Comprador: Representa al cliente que realiza las compras.
  Clase CarritoDeCompras: Representa el carrito de un comprador. Cada comprador tiene un único carrito activo, que contiene una colección de Item.
  Clase Item: Representa un producto específico en el carrito de compras. Cada Item está asociado con un Producto específico y tiene una cantidad y precio, calculado según el tipo de producto (por ejemplo, cantidad, peso, etc.).
  Clase abstracta Producto: Clase base para los distintos tipos de productos, como ProductoPorCantidad, ProductoPorKg, Torta, etc.
  Estructura de clases y relaciones
  Comprador tiene una relación uno a uno con CarritoDeCompras.
  CarritoDeCompras tiene una relación uno a muchos con Item.
  Item tiene una relación muchos a uno con Producto.
 */
  // recorda que el name  de @JoinColumn es como vas a llamar la columna -> si tenes dudas no isntancies
  // carrito necesita conocer al Item? ->

  @ManyToOne
  @JoinColumn(name="carrito_id")
	private Carrito unCarritoPorComprador;

  //un item es la referencia a un producto en particular, si no estuviese ITEM la relacion seria entree
  // producto - carrito -> y un producto puede estar en muchos carritos de compras y un carrito de compras tien emuchos productos
  // entonces item rompe esa relacion y aparece en el medio en la que : un producto puede estar ne muchos items
  @OneToOne
  // un producto necesita conocer a su item? no! , por que querria yo que un producto conozca ? por eso UNIDIRECCIONAL
  // pero un ITEM en realidad responde a un producto en si, entonces no es MANY-To-One , sino ONEtoONE
  private Prodcuto producto;
}

