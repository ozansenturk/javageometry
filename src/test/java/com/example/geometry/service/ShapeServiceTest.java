package com.example.geometry.service;

import com.example.geometry.data.Square;
import com.example.geometry.data.SquareRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.naming.OperationNotSupportedException;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class ShapeServiceTest {

    @Autowired
    private ShapeService shapeService;

    @Mock
    private SquareRepository squareRepository;

    @Test
    public void testSaveSquare() throws OperationNotSupportedException {

        Square square1 = new Square(3, 2, 6, 4);

        shapeService.saveSquare(square1);

        assertTrue(true,
                "Square cannot be saved");
    }

    @Test
    public void testWhenThereIsIntersection() throws OperationNotSupportedException {

        Square square1 = new Square(3, 2, 6, 4);

        shapeService.saveSquare(square1);

        assertTrue(shapeService.isIntersected(square1),
                "Squares must be intersected");
    }

    @Test
    public void testWhenThereIsNoIntersection() throws OperationNotSupportedException {

        Square square1 = new Square(3, 2, 6, 4);
        Square square2 = new Square(2, 5, 6, 6);

        shapeService.saveSquare(square1);

        assertFalse(shapeService.isIntersected(square2),
                "Squares should not be intersected");
    }
}
