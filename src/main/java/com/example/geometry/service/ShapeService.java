package com.example.geometry.service;

import com.example.geometry.data.Square;
import com.example.geometry.data.SquareRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
public class ShapeService {

    private SquareRepository squareRepository;

    public void saveSquare(Square square) {


        if (isIntersected(square)==false) {
            Square result = squareRepository.save(square);
        }

    }

    public boolean isIntersected(Square square) {

        AtomicBoolean isIntersected = new AtomicBoolean(false);

        squareRepository.findAll().forEach((oneSquare)->{

                    try {
                        if (oneSquare.isIntersected(square)){
                            isIntersected.set(true);
                        }
                    } catch (OperationNotSupportedException e) {
                        e.printStackTrace();
                    }
                }

        );
        return isIntersected.get();
    }
}
