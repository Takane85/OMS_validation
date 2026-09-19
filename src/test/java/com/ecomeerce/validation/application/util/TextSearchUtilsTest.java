package com.ecomeerce.validation.application.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class TextSearchUtilsTest {

    @Test
    void shouldIgnoreUpperAndLowerCase() {
        assertTrue(
                TextSearchUtils.flexibleMatch(
                        "L SANTA FE",
                        "santa fe"
                )
        );
    }

    @Test
    void shouldIgnoreAccents() {
        assertTrue(
                TextSearchUtils.flexibleMatch(
                        "Liverpool Galerías Serdán",
                        "galerias serdan"
                )
        );
    }

    @Test
    void shouldSupportPartialSearch() {
        assertTrue(
                TextSearchUtils.flexibleMatch(
                        "Monterrey Centro",
                        "monterr"
                )
        );
    }

    @Test
    void shouldAllowMinorSpellingErrors() {
        assertTrue(
                TextSearchUtils.flexibleMatch(
                        "Monterrey Centro",
                        "monterey"
                )
        );
    }

    @Test
    void shouldNotMatchUnrelatedText() {
        assertFalse(
                TextSearchUtils.flexibleMatch(
                        "Monterrey Centro",
                        "santa fe"
                )
        );
    }
}