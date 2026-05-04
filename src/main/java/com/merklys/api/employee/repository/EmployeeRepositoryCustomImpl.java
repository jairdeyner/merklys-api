package com.merklys.api.employee.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import com.merklys.api.employee.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    // EntityManager es la puerta de entrada a JPA — permite crear queries,
    // buscar entidades y gestionar el ciclo de vida de los objetos persistidos
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Long> findAllIds(Specification<Employee> spec, Pageable pageable) {

        // CriteriaBuilder es la fábrica de condiciones — con él construyes
        // predicados (WHERE), ordenamientos (ORDER BY), funciones, etc.
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();

        // ── Query principal: trae solo los IDs ───────────────────────────────

        // CriteriaQuery<Long> define el tipo del resultado — en este caso Long (el ID)
        CriteriaQuery<Long> query = cb.createQuery(Long.class);

        // Root<Employee> es el FROM de la query — representa la tabla employees
        // desde aquí puedes hacer JOINs y acceder a sus campos
        Root<Employee> root = query.from(Employee.class);

        // Aplica los filtros de la Specification sobre este root y query
        // toPredicate construye internamente los JOINs y WHEREs dinámicos
        Predicate predicate = spec.toPredicate(root, query, cb);

        // SELECT DISTINCT e.id FROM employees e ... WHERE ...
        query.select(root.get("id")) // proyecta solo el ID
                // evita duplicados por JOINs de roles
                .where(predicate); // aplica los filtros

        // Aplica el ORDER BY desde el Pageable (ej: sort=lastName,asc)
        // Convierte cada Sort.Order de Spring a un Order de Criteria API
        if (pageable.getSort().isSorted()) {
            List<Order> orders = pageable.getSort()
                    .stream()
                    .map(sortOrder -> sortOrder.getDirection() == Sort.Direction.ASC
                            ? cb.asc(root.get(sortOrder.getProperty()))
                            : cb.desc(root.get(sortOrder.getProperty())))
                    .toList();
            query.orderBy(orders);
        }

        // Ejecuta la query aplicando LIMIT y OFFSET en BD
        List<Long> ids = this.entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset()) // OFFSET — desde qué registro
                .setMaxResults(pageable.getPageSize()) // LIMIT — cuántos registros
                .getResultList();

        // ── Query de conteo: para calcular totalElements y totalPages ─────────

        // CriteriaQuery<Long> separada solo para contar
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);

        // Root independiente del root anterior — cada query necesita su propio root
        Root<Employee> countRoot = countQuery.from(Employee.class);

        // Aplica los mismos filtros pero sobre el countRoot
        Predicate countPredicate = spec.toPredicate(countRoot, countQuery, cb);

        // SELECT COUNT(DISTINCT e.id) FROM employees e ... WHERE ...
        countQuery.select(cb.countDistinct(countRoot))
                .where(countPredicate);

        // Ejecuta el conteo y obtiene el resultado
        Long total = this.entityManager.createQuery(countQuery).getSingleResult();

        // PageImpl combina los IDs paginados + el pageable + el total
        // Spring usa esto para calcular totalPages y exponer la metadata de paginación
        return new PageImpl<>(ids, pageable, total);
    }

}