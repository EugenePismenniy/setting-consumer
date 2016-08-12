package com.example.dao;

import com.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author it100985pev on 12.08.16 12:06.
 */

public interface UserDao extends JpaRepository<User, String> {
}
