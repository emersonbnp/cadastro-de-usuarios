package com.cadastrodeusuarios.controller;

import javax.validation.Valid;

import com.cadastrodeusuarios.exception.EntityNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cadastrodeusuarios.dto.UserDTO;
import com.cadastrodeusuarios.entity.User;
import com.cadastrodeusuarios.service.UserService;

@RestController
@RequestMapping("/users")
@Validated
@Api(value = "Endpoints de manipulação de usuários")
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${pagination.default_limit}")
    private Integer defaultPageSize;

    @PostMapping
    @ApiOperation(value = "Cadastrar um novo usuário")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<UserDTO> save(
            @ApiParam("Corpo da requisição")
            @RequestBody @Valid UserDTO userDTO,
            BindingResult result) {
        User user = new User(userDTO);
        UserDTO savedUserDTO = new UserDTO(userService.save(user));
        return new ResponseEntity<>(savedUserDTO, HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Encontrar usuário por ID")
    @ApiResponse(code = 200, message = "Sucesso")
    public ResponseEntity<UserDTO> findById(
            @ApiParam("Identificador do usuário")
            @PathVariable Integer id)
            throws EntityNotFoundException {
        UserDTO userDTO = new UserDTO(userService.findById(id));
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @GetMapping(path = "find-by-companyid")
    @ApiOperation(value = "Encontrar usuário por ID da companhia")
    @ApiResponse(code = 200, message = "Sucesso")
    public Page<User> findByCompanyId(
            @ApiParam("Identificador da companhia") @RequestParam Integer companyId,
            @ApiParam(value = "Página atual", example = "1", defaultValue = "0")
            @RequestParam(required = false) Integer currentPage,
            @ApiParam(value = "Tamanho da página", example = "5", defaultValue = "5")
            @RequestParam(required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage != null ? currentPage : 0,
                pageSize != null ? pageSize : defaultPageSize, Sort.by("id"));
        return userService.findByCompanyId(companyId, pageable);
    }

    @GetMapping(path = "/find-by-email")
    @ApiOperation(value = "Encontrar usuário por e-mail")
    @ApiResponse(code = 200, message = "Sucesso")
    public Page<User> findByEmail(
            @ApiParam("E-mail do usuário") @RequestParam String email,
            @ApiParam(value = "Página atual", example = "1", defaultValue = "0")
            @RequestParam(required = false) Integer currentPage,
            @ApiParam(value = "Tamanho da página", example = "5", defaultValue = "5")
            @RequestParam(required = false) Integer pageSize) {
        Pageable pageable = PageRequest.of(currentPage != null ? currentPage : 0,
                pageSize != null ? pageSize : defaultPageSize, Sort.by("id"));
        return userService.findByEmail(email, pageable);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Excluir usuário por ID")
    @ApiResponse(code = 200, message = "Sucesso")
    public ResponseEntity<UserDTO> deleteById(
            @ApiParam("Identificador do usuário")
            @PathVariable Integer id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
