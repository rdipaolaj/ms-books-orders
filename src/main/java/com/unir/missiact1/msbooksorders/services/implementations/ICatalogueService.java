package com.unir.missiact1.msbooksorders.services.implementations;

import java.util.UUID;

import com.unir.missiact1.msbooksorders.application.dtos.catalogue.BookInfoDto;

public interface ICatalogueService {
    /**
     * Recupera detalles de un libro desde el microservicio de catálogo.
     */
    BookInfoDto getBookById(UUID bookId);
}