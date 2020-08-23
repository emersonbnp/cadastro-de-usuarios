package com.cadastrodeusuarios.service;


import com.cadastrodeusuarios.entity.User;
import com.cadastrodeusuarios.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    User save(User user);

    User findById(Integer id) throws EntityNotFoundException;

    void deleteById(Integer id);

    Page<User> findByCompanyId(Integer companyId, Pageable pageable);

    Page<User> findByEmail(String email, Pageable pageable);
}