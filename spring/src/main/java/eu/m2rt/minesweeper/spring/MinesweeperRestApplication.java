package eu.m2rt.minesweeper.spring;

import eu.m2rt.minesweeper.logic.interfaces.Minesweeper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SpringBootApplication
public class MinesweeperRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinesweeperRestApplication.class, args);
	}

	@Bean
	public ConcurrentMap<String, Minesweeper> games() {
		return new ConcurrentHashMap<>();
	}
}
