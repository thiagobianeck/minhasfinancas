package com.bianeck.minhasfinancas.model.repository;

import com.bianeck.minhasfinancas.model.entity.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository repository;

    @Test
    @DisplayName("Deve verificar a existência de um e-mail")
    public void deveVerificarAExistenciaDeUmEmail() {
        // cenario
        Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
        repository.save(usuario);
        // ação / execução
        boolean result = repository.existsByEmail("usuario@email.com");
        // verificação
        Assertions.assertThat(result).isTrue();

    }

    @Test
    @DisplayName("Deve retornar falso quando não houver usuário cadastrado com o e-mail")
    public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
        // cenario
        repository.deleteAll();
        // acao - execucao
        boolean result = repository.existsByEmail("usuario@gmail.com");

        // verificacao
        Assertions.assertThat(result).isFalse();
    }
}
