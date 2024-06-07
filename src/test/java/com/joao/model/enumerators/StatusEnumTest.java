package com.joao.model.enumerators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusEnumTest {

    @Test
    public void testGetValue() {
        assertEquals("EM_ANDAMENTO", StatusEnum.EM_ANDAMENTO.getValue());
        assertEquals("CONCLUIDO", StatusEnum.CONCLUIDO.getValue());
        assertEquals("PENDENTE", StatusEnum.PENDENTE.getValue());
        assertEquals("ATRASADO", StatusEnum.ATRASADO.getValue());
    }

    @Test
    public void testFromValue() {
        assertEquals(StatusEnum.EM_ANDAMENTO, StatusEnum.fromValue("EM_ANDAMENTO"));
        assertEquals(StatusEnum.CONCLUIDO, StatusEnum.fromValue("CONCLUIDO"));
        assertEquals(StatusEnum.PENDENTE, StatusEnum.fromValue("PENDENTE"));
        assertEquals(StatusEnum.ATRASADO, StatusEnum.fromValue("ATRASADO"));
    }

    @Test
    public void testFromValueInvalid() {
        assertThrows(IllegalArgumentException.class, () -> StatusEnum.fromValue("INVALIDO"));
    }

}