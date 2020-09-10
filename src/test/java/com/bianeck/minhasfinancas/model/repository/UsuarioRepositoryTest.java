package com.bianeck.minhasfinancas.model.repository;

import com.bianeck.minhasfinancas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    @DisplayName("Deve verificar a existência de um e-mail")
    public void deveVerificarAExistenciaDeUmEmail() {
        // cenario
        Usuario usuario = criarUsuario();
        entityManager.persist(usuario);
        // ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");
        // verificação
        Assertions.assertThat(result).isTrue();

    }

    @Test
    @DisplayName("Deve retornar falso quando não houver usuário cadastrado com o e-mail")
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        // cenario

        // acao - execucao
        boolean result = repository.existsByEmail("usuario@gmail.com");

        // verificacao
        Assertions.assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Deve persistir um usuário na base de dados")
    public void devePersistirUmUsuarioNaBaseDeDados() {
        // cenario
        Usuario usuario = criarUsuario();
        // acao - execucao
        Usuario usuarioSalvo = repository.save(usuario);

        // verificacao
        Assertions.assertThat(usuarioSalvo.getId()).isNotNull();
    }

    @Test
    @DisplayName("Deve buscar um usuário por e-mail")
    public void deveBuscarUmUsuarioPorEmail() {
        // cenario
        Usuario usuario = criarUsuario();

        // acao - execucao
        entityManager.persist(usuario);

        // verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isTrue();

    }

    @Test
    @DisplayName("Deve retornar vazio ao buscar um usuário por e-mail quando não existe na base")
    public void deveRetornarVazioAoBuscarUsuarioPorEmailQuandoNaoExisteNaBase() {

        // verificacao
        Optional<Usuario> result = repository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isFalse();

    }

    public static Usuario criarUsuario() {
        return Usuario.builder()
                        .nome("usuario")
                        .email("usuario@email.com")
                        .senha("senha")
                        .build();
    }
}
