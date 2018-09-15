package eu.m2rt.minesweeper.rest;

import eu.m2rt.minesweeper.logic.interfaces.Minesweeper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@SpringBootApplication
public class MinesweeperRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinesweeperRestApplication.class, args);
	}

	@Bean
	public ConcurrentMap<Long, Minesweeper> games() {
		return new ConcurrentHashMap<>();
	}

	@Bean
	public AtomicLong counter() {
		return new AtomicLong();
	}
}
