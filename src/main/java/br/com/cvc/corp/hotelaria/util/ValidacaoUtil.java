package br.com.cvc.corp.hotelaria.util;

import static java.util.Objects.isNull;

import java.util.function.Predicate;

import br.com.cvc.corp.hotelaria.exceptions.ValidationException;

public class ValidacaoUtil {

    private ValidacaoUtil() {
        throw new IllegalStateException("Classe Utilitária");
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void validar(final Predicate validacao, final Object valor, final String mensagemException){

        if (isNull(validacao)) {
            throw new IllegalArgumentException("O argumento validacao não pode ser nulo");
        }

        if (validacao.test(valor)) {
            throw new ValidationException(mensagemException);
        }
    }
}
