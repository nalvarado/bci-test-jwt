package com.bci.test;

import org.dozer.DozerBeanMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuracion Conversor Dozer para Model y DTO.
 *
 * @Author Nelson Alvarado
 */
@Configuration
public class DozerConfig {

    @Bean
    public DozerBeanMapper dozerBeanMapper() {
        return new DozerBeanMapper();
    }
}

