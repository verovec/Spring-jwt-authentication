package com.quest.etna.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;

@RestController
public class DefaultController {

	@RequestMapping(value = "/testSuccess")
	public ResponseEntity<String> testSuccess() {
		return new ResponseEntity<>("success", HttpStatus.OK);
	}

	@RequestMapping(value = "/testNotFound")
	public ResponseEntity<String> testNotFound() {
		return new ResponseEntity<>("not found", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/testError")
	public ResponseEntity<String> testError() {
		return new ResponseEntity<>("error", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}