package com.fatec.scelrest;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;

import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolation;
import com.fatec.scelrest.model.Livro;
import com.fatec.scelrest.model.LivroRepository;

@SpringBootTest
class REQ01CadastrarLivroTests {
	@Autowired
	private LivroRepository repository;
	private Livro livro;
	private Validator validator;
	private ValidatorFactory validatorFactory;

	@BeforeEach
	public void setup() {
		repository.deleteAll();
	}

	@Test
	void ct01_quando_dados_validos_isbn_nao_cadastrado_retorna1() {
		// Dado – que o atendente tem um livro não cadastrado
		
		livro = new Livro("3333", "Teste de Software", "Delamaro");
		// Quando – o atendente cadastra o livro
		repository.save(livro);
		// Então – o sistema verifica os dados E confirma a operação
		assertEquals(1, repository.count());

	}

	@Test
	void ct02_quando_dados_validos_violacoes_retorna_vazio() {
		// Dado – que o atendente tem um livro não cadastrado
		livro = new Livro("3333", "Teste de Software", "Delamaro");
		// Quando – o atendente cadastra um livro com informações válidas
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		// Então – o sistema verifica os dados e retorna que nao existem violacoes.
		assertTrue(violations.isEmpty());
	}

	@Test
	void ct03_quando_titulo_branco_msg_titulo_invalido() {
		// Dado – que o atendente tem um livro não cadastrado
		livro = new Livro("3333", "", "Delamaro");
		// Quando – o atendente cadastra um livro com informações válidas
		validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
		Set<ConstraintViolation<Livro>> violations = validator.validate(livro);
		// Então – o sistema verifica os dados e retorna que nao existem violacoes.
		// assertFalse(violations.isEmpty());
		// assertEquals(violations.size(), 1);
		assertEquals("Titulo deve ter entre 1 e 50 caracteres", violations.iterator().next().getMessage());

	}

	// unique=true em @Column 
	// as verificações de chave primaria acontecem na camada de persistência.
	@Test
	void ct04_quando_isbn_ja_cadastrado_retornar_violacao_de_integridade() {
		// Dado – que o ISBN do livro ja está cadastrado
		
		livro = new Livro("4444", "Teste de Software", "Delamaro");
		repository.save(livro);
		// Quando – o usuário registra as informações do livro e confirma a operação
		livro = new Livro("4444", "Teste de Software", "Delamaro");
		// Então – o sistema valida as informações E retorna a quantidade de registros
		// inseridos igual a 1.
		try {
			repository.save(livro);
		} catch (DataIntegrityViolationException e) {
			assertEquals("could not execute statement", e.getMessage().substring(0, 27));
			assertEquals(1,repository.count());
		}
	}

}
