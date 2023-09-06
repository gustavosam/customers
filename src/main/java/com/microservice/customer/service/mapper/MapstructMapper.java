package com.microservice.customer.service.mapper;

import com.microservice.customer.documents.ClientDocument;
import com.microservice.customer.model.ClientCreate;
import com.microservice.customer.util.ClientDto;
import org.mapstruct.Mapper;

/**
 * Mapstruct para mapear clases a otras clases.
 * */
@Mapper(componentModel = "spring")
public interface MapstructMapper {

  ClientDocument clientCreateToClientDocument(ClientCreate clientCreate);

  ClientDto clientDocToClientDto(ClientDocument clientDoc);
}
