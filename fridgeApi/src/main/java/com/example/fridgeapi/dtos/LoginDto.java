package com.example.fridgeapi.dtos;

import java.util.Optional;

public record LoginDto(String email, String password, Optional<String> teste) {

}
