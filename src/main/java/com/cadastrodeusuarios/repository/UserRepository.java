package com.cadastrodeusuarios.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cadastrodeusuarios.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findByCompanyId(Integer companyId, Pageable pageable);

    Page<User> findByEmail(String email, Pageable pageable);
}
