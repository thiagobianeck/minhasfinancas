package com.bianeck.minhasfinancas.service;

import com.bianeck.minhasfinancas.exception.ErroAutenticacao;
import com.bianeck.minhasfinancas.exception.RegraNegocioException;
import com.bianeck.minhasfinancas.model.entity.Usuario;
import com.bianeck.minhasfinancas.model.repository.UsuarioRepository;
import com.bianeck.minhasfinancas.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
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

import java.util.Optional;

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
    @DisplayName("Deve autenticar um usuário com sucesso")
    public void deveAUtenticarUmUsuarioComSucesso() {
        // cenário
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1L).build();
        Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));

        // ação

        Usuario result = assertDoesNotThrow(() -> service.autenticar(email, senha));

        Assertions.assertThat(result).isNotNull();
    }


    @Test
    @DisplayName("Deve lançar erro quando não encontrar usuário cadastrado com o e-mail informado")
    public void deveLancarErroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
        // cenário
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        // ação
        assertThrows(ErroAutenticacao.class, () ->
                service.autenticar("email@email.com", "senha"));

    }

    @Test
    @DisplayName("Deve lançar erro quando a senha não bater")
    public void deveLancarErroQuandoSenhaNaoBater() {
        // cenário
        String senha = "senha";
        Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
        Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        // ação
        assertThrows(ErroAutenticacao.class, () ->
                service.autenticar("email@email.com", "123"));

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
