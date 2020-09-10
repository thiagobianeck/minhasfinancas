package com.bianeck.minhasfinancas.service;

import com.bianeck.minhasfinancas.exception.RegraNegocioException;
import com.bianeck.minhasfinancas.model.entity.Usuario;
import com.bianeck.minhasfinancas.model.repository.UsuarioRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @Autowired
    UsuarioService service;

    @Autowired
    UsuarioRepository repository;

    @Test
    @DisplayName("Deve validar e-mail")
    public void deveValidarEmail() {
        // cenario
        repository.deleteAll();
        // acao - execucao
        assertDoesNotThrow(() -> {
            service.validarEmail("email@email.com");
        });
    }

    @Test
    @DisplayName("Deve lançar erro ao validar e-mail quando já exisitir e-mail cadastrado")
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrados() {
        // cenario
        Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build();
        repository.save(usuario);
        // acao - execucao
        assertThrows(RegraNegocioException.class,() -> {
            service.validarEmail("email@email.com");
        });

        // validacao
    }
}
