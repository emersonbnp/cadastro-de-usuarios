package com.cadastrodeusuarios.entity;

import com.cadastrodeusuarios.dto.UserDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -4887746410344559969L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Integer id;
    @Column(name = "companyId")
    private Integer companyId;
    @Column(name = "email")
    private String email;
    @Column(name = "birthdate")
    private LocalDate birthdate;

    public User(UserDTO userDTO) {
        this.id = userDTO.getUserId();
        this.companyId = userDTO.getCompanyId();
        this.email = userDTO.getEmail();
        this.birthdate = userDTO.getBirthdate();
    }
}
