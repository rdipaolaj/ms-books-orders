package com.unir.missiact1.msbooksorders.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

import com.unir.missiact1.msbooksorders.application.dtos.catalogue.BookInfoDto;
import com.unir.missiact1.msbooksorders.commons.enums.ApiErrorCode;
import com.unir.missiact1.msbooksorders.commons.exceptions.CustomException;
import com.unir.missiact1.msbooksorders.commons.responses.ApiResponse;
import com.unir.missiact1.msbooksorders.services.implementations.ICatalogueService;

@Service
public class CatalogueService implements ICatalogueService {
    private final WebClient webClient;
    private final String apiVersion;

    public CatalogueService(WebClient.Builder builder,
                                @Value("${catalogue.service.url}") String baseUrl,
                                @Value("${catalogue.service.api-version:1}") String apiVersion) {
        this.webClient = builder.baseUrl(baseUrl).build();
        this.apiVersion = apiVersion;
    }

    @Override
    public BookInfoDto getBookById(UUID bookId) {
        try {
            ApiResponse<BookInfoDto> resp = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/v1/api/books/find-by-id/{id}")
                    .build(bookId))
                .header("X-Api-Version", apiVersion)
                .retrieve()
                .onStatus(status -> status.isError(), clientResponse ->
                    clientResponse.bodyToMono(new ParameterizedTypeReference<ApiResponse<BookInfoDto>>() {})
                        .flatMap(errorResp -> Mono.error(
                            new CustomException(
                                errorResp.getMessage(),
                                ApiErrorCode.valueOf(errorResp.getErrors().get(0).getCode())
                            )
                        ))
                )
                .bodyToMono(new ParameterizedTypeReference<ApiResponse<BookInfoDto>>() {})
                .block();

            if (resp == null || resp.getData() == null) {
                throw new CustomException("Respuesta inválida del servicio de catálogo", ApiErrorCode.InternalServerError);
            }
            return resp.getData();
        } catch (WebClientResponseException ex) {
            // Manejo de errores HTTP
            if (ex.getStatusCode().value() == 404) {
                throw new CustomException(ex.getResponseBodyAsString(), ApiErrorCode.NotFound);
            }
            throw new CustomException(
                "Error en llamada al catálogo: " + ex.getStatusCode().value(),
                ApiErrorCode.InternalServerError
            );
        }
    }
}