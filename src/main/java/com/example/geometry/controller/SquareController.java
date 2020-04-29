package com.example.geometry.controller;

import com.example.geometry.data.Square;
import com.example.geometry.data.SquareRepository;
import com.example.geometry.data.SquareValidationError;
import com.example.geometry.data.SquareValidationErrorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SquareController {
    private SquareRepository squareRepository;

    @Autowired
    public SquareController(SquareRepository squareRepository) {
        this.squareRepository = squareRepository;
    }

    @GetMapping("/squares")
    public ResponseEntity<Iterable<Square>> getSquares(){

        List<Square> squares = (List<Square>) squareRepository.findAll();

        return ResponseEntity.ok(squareRepository.findAll());

    }

    @RequestMapping(value="/square", method = {RequestMethod.
            POST,RequestMethod.PUT})
    public ResponseEntity<?> createSquare(@Valid @RequestBody Square square,
                                          Errors errors){
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(SquareValidationErrorBuilder.fromBindingErrors(errors));
        }
        Square result = squareRepository.save(square);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}")

                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public SquareValidationError handleException(Exception exception) {
        return new SquareValidationError(exception.getMessage());
    }
}
