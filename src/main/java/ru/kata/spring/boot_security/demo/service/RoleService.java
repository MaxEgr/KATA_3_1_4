package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;
@Repository
public interface RoleService {
    public List<Role> getAllRoles();
    public Role getRoleByRoleName(String role);
}
