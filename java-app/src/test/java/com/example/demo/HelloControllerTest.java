package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloControllerTest {

    @Test
    void hello_should_return_hello_world() {
        var result = new HelloController().hello();

        assertThat(result).isEqualTo("Hello World!");
    }

}
