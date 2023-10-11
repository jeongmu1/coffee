package com.dnlab.coffee.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.filter.HiddenHttpMethodFilter

@Configuration
class MvcConfig {
    @Bean
    fun hiddenHttpMethodFilter(): HiddenHttpMethodFilter {
        return HiddenHttpMethodFilter()
    }
}