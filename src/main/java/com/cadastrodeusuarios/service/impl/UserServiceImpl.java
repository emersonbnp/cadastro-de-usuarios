package com.cadastrodeusuarios.service.impl;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

import com.cadastrodeusuarios.constants.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cadastrodeusuarios.entity.User;
import com.cadastrodeusuarios.exception.EntityNotFoundException;
import com.cadastrodeusuarios.repository.UserRepository;
import com.cadastrodeusuarios.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) {
        List<Integer> validCompanies = Arrays.asList(Company.ALLOWED_COMPANIES);
        if (!validCompanies.contains(user.getCompanyId())) {
            throw new InvalidParameterException("Invalid company ID");
        }
        return userRepository.save(user);
    }

    @Override
    public User findById(Integer id) throws EntityNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public Page<User> findByCompanyId(Integer companyId, Pageable pageable) {
        return userRepository.findByCompanyId(companyId, pageable);
    }

    @Override
    public Page<User> findByEmail(String email, Pageable pageable) {
        return userRepository.findByEmail(email, pageable);
    }

}
