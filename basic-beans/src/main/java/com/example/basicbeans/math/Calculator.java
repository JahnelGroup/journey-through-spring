package com.example.basicbeans.math;

import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class Calculator implements MathService {

    @Override
    public int add(Integer... numbers) {
        return Arrays.stream(numbers).mapToInt(Integer::intValue).sum();
    }

    @Override
    public int sub(Integer... numbers) {
        return Arrays.stream(numbers).reduce(0, (i, i1) -> i - i1);
    }
}
