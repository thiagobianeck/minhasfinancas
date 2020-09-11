package com.bianeck.minhasfinancas.service;

import com.bianeck.minhasfinancas.exception.RegraNegocioException;
import com.bianeck.minhasfinancas.model.repository.UsuarioRepository;
import com.bianeck.minhasfinancas.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @MockBean
    UsuarioRepository repository;
    UsuarioService service;

    @BeforeEach
    public void setUp(){
        service = new UsuarioServiceImpl(repository);
    }

    @Test
    @DisplayName("Deve validar e-mail")
    public void deveValidarEmail() {
        // cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
        // acao - execucao
        assertDoesNotThrow(() -> {
            service.validarEmail("email@email.com");
        });
    }

    @Test
    @DisplayName("Deve lançar erro ao validar e-mail quando já exisitir e-mail cadastrado")
    public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrados() {
        // cenario
        Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
        // acao - execucao
        assertThrows(RegraNegocioException.class,() -> {
            service.validarEmail("email@email.com");
        });

        // validacao
    }
}
