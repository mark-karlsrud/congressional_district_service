package com.markkarlsrud.districtmap.app

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.markkarlsrud.districtmap"])
internal open class Application {
    @Bean
    open fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner {
        return CommandLineRunner {}
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Application::class.java, *args)
        }
    }
}