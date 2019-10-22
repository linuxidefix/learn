package com.quyc.learn.javabasic.data_structures_and_algorithm_anlalysis;

public interface HashFamily<AnyType> {
    int hash(AnyType x, int which);

    int getNumberOfFunctions();

    void generateNewFunctions();
}
