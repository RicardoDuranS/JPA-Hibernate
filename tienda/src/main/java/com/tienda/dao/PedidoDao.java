package com.tienda.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import com.tienda.modelo.Pedido;
import com.tienda.vo.RelatorioDeVenta;

public class PedidoDao {

    private EntityManager em;

    public PedidoDao(EntityManager em) {
        this.em = em;
    }

    public void guardar(Pedido pedido) {
        this.em.persist(pedido);
    }

    public Pedido consultaPorId(Long id) {
        return em.find(Pedido.class, id);
    }

    public List<Pedido> consultarTodos() {
        String jqpl = "SELECT P FROM Pedido AS P";
        return em.createQuery(jqpl, Pedido.class).getResultList();
    }

    public List<Pedido> consultaPorNombre(String nombre) {
        String jpql = "SELECT P  FROM Pedido AS P WHERE P.nombre=:nombre";
        return em.createQuery(jpql, Pedido.class).setParameter("nombre", nombre).getResultList();
    }

    public List<Pedido> consultaPorCategoria(String categoria) {
        String jpql = "SELECT P  FROM Pedido AS P WHERE P.categoria=:categoria";
        return em.createQuery(jpql, Pedido.class).setParameter("categoria", categoria).getResultList();
    }

    public BigDecimal consultarPrecioPorNombreDePedido(String nombre) {
        String jpql = "SELECT P.precio FROM Pedido AS P WHERE P.nombre=:nombre";
        return em.createQuery(jpql, BigDecimal.class).setParameter("nombre", nombre).getSingleResult();
    }

    public BigDecimal valorTotalVendido() {
        String jpql = "SELECT SUM(p.valorTotal) FROM Pedido p";
        return em.createQuery(jpql, BigDecimal.class).getSingleResult();
    }

    public List<RelatorioDeVenta> relatorioVentas() {
        String jpql = "SELECT new package com.tienda.vo.RelatorioDeVenta(producto.nombre, "
                + "SUM(items.cantidad), "
                + "MAX(pedido.fecha)) "
                + "FROM Pedido pedido "
                + "JOIN pedido.items"
                + "JOIN item.producto producto"
                + "ORDER BY item.cantidad DESC";
        return em.createQuery(jpql, RelatorioDeVenta.class).getResultList();
    }
}
