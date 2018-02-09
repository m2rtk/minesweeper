package com.example.minesweeper_rest.logic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid x or y value.")
public class IndexOutOfBoundsException extends java.lang.IndexOutOfBoundsException {

}
