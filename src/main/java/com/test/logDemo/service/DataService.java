package com.test.logDemo.service;

import org.springframework.data.domain.Pageable;

public interface DataService {
    Pageable getSliceSortedById(int page, int size);
    void loadFromFile(String filePath);
}
