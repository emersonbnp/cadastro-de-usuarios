package com.cadastrodeusuarios.dto;

import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.cadastrodeusuarios.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "Usuário a ser salvo", value = "user")
@NoArgsConstructor
public class UserDTO implements Serializable {
    private static final long serialVersionUID = 3063009554101058648L;
    @ApiModelProperty(hidden = true)
    private Integer userId;
    @NotNull(message = "field \"companyId\" can't be empty")
    @ApiModelProperty(value = "Identificador da companhia", example = "1", required = true)
    private Integer companyId;
    @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    @ApiModelProperty(value = "E-mail do usuário", example = "joao@test.com", required = true)
    private String email;
    @Size(max = 255, message = "Exceeded email size (255 characters)")
    @NotNull(message = "Birthdate can't be empty")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @ApiModelProperty(value = "Data de nascimento do usuário", example = "20/02/1980", required = true)
    private LocalDate birthdate;

    public UserDTO(User user) {
        this.userId = user.getId();
        this.companyId = user.getCompanyId();
        this.email = user.getEmail();
        this.birthdate = user.getBirthdate();
    }
}
