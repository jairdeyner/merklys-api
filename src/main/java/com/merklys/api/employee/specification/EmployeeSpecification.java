package com.merklys.api.employee.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.merklys.api.employee.dto.request.EmployeeFilterRequest;
import com.merklys.api.employee.entity.Employee;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

/**
 * Especificación JPA para filtrado dinámico de empleados.
 *
 * <p>
 * Construye un {@code WHERE} condicional según los filtros recibidos.
 * Los filtros no enviados (null o blank) son ignorados y no afectan la query.
 *
 * <p>
 * <b>Estructura de JOINs:</b>
 * <ul>
 * <li>{@code INNER JOIN users} — siempre presente, ya que todo empleado
 * tiene un usuario asociado ({@code user_id NOT NULL}).</li>
 * <li>{@code INNER JOIN roles} — solo se agrega cuando llega el filtro
 * {@code role}. Usar INNER es intencional: excluye empleados que no
 * tengan ese rol. Si el JOIN fuera siempre presente, empleados sin
 * roles asignados desaparecerían de la lista general.</li>
 * </ul>
 *
 * <p>
 * <b>Nota sobre paginación:</b> esta especificación no carga la colección
 * de roles del usuario. Esa responsabilidad recae en el repositorio mediante
 * {@code @EntityGraph}, lo que garantiza que la paginación se ejecute en base
 * de datos y no en memoria.
 */
public class EmployeeSpecification {

    private EmployeeSpecification() {
    }

    public static Specification<Employee> withFilters(EmployeeFilterRequest filters) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // JOIN con User siempre necesario para acceder a isActive y accountNonLocked
            Join<Object, Object> user = root.join("user", JoinType.INNER);

            // Búsqueda parcial por nombre, apellido o DNI (case-insensitive)
            if (filters.search() != null && !filters.search().isBlank()) {
                String pattern = "%" + filters.search().toLowerCase().trim() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("firstName")), pattern),
                        cb.like(cb.lower(root.get("lastName")), pattern),
                        cb.like(root.get("dni"), pattern)));
            }

            // Filtro exacto por nombre de rol — se normaliza a mayúsculas para
            // preservar el índice sobre roles.name (evita LOWER() en BD)
            if (filters.role() != null && !filters.role().isBlank()) {
                Join<Object, Object> roles = user.join("roles", JoinType.INNER);
                predicates.add(cb.equal(roles.get("name"),
                        filters.role().toUpperCase().trim()));
            }

            // Filtro por estado activo de la cuenta
            if (filters.isActive() != null) {
                predicates.add(cb.equal(user.get("isActive"), filters.isActive()));
            }

            // Filtro por bloqueo — accountNonLocked es la negación de isLocked
            if (filters.isLocked() != null) {
                predicates.add(cb.equal(user.get("accountNonLocked"), !filters.isLocked()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

}