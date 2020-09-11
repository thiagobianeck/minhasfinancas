package com.bianeck.minhasfinancas.service;

import com.bianeck.minhasfinancas.exception.RegraNegocioException;
import com.bianeck.minhasfinancas.model.entity.Usuario;
import com.bianeck.minhasfinancas.model.repository.UsuarioRepository;
import com.bianeck.minhasfinancas.service.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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

    private static UsuarioRepository repository;
    private static UsuarioService service;

    @BeforeAll
    public static void setUp(){
        repository = Mockito.mock(UsuarioRepository.class);
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
