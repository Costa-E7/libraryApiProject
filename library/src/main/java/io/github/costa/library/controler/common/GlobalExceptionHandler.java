package io.github.costa.library.controler.common;

import io.github.costa.library.dto.ErroCampo;
import io.github.costa.library.dto.ErroResposta;
import io.github.costa.library.exceptions.CampoInvalidoException;
import io.github.costa.library.exceptions.OperacaoNaoPermitidaException;
import io.github.costa.library.exceptions.RegistroDuplicadoException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice // capturar exception(@valid_ e retornar a resposta
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY) // para ele retornar no metodo q ele caputrar o erro o status selecionado
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroResposta handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<ErroCampo> listaErros = fieldErrors.
                stream().
                map(
                        fieldError -> new ErroCampo(fieldError.getField(), fieldError.getDefaultMessage())
                ).toList();
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação.",
                listaErros
                );
    }
    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta handleRegistroDuplicadoExcepiton(RegistroDuplicadoException e){
        return ErroResposta.conflito(e.getMessage());

    }
    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta operacaoNaoPermitidaException(OperacaoNaoPermitidaException e){
        return ErroResposta.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta handleCampoInvalidoException(CampoInvalidoException e){
        return new ErroResposta(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação.",
                List.of(new ErroCampo(e.getCampo(), e.getMessage()))
        );
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroResposta handleErrosNaoTradatos(RuntimeException e){
        return new ErroResposta(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro inesperado. Entre em contato com a administração",
                new ArrayList<>());
    }

}
