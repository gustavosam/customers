package com.microservice.customer.service.mapper;

import com.microservice.customer.documents.ClientDocument;
import com.microservice.customer.model.Accounts;
import com.microservice.customer.model.ClientCreate;
import com.microservice.customer.model.CreditCards;
import com.microservice.customer.model.Credits;
import com.microservice.customer.util.AccountDto;
import com.microservice.customer.util.CardDto;
import com.microservice.customer.util.ClientDto;
import com.microservice.customer.util.CreditDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapstruct para mapear clases a otras clases.
 * */
@Mapper(componentModel = "spring")
public interface MapstructMapper {

  ClientDocument clientCreateToClientDocument(ClientCreate clientCreate);

  ClientDto clientDocToClientDto(ClientDocument clientDoc);

  List<Accounts> mapLisAccountDtoToListAccounts(List<AccountDto> accountDtoList);

  List<Credits> mapLisCreditsDtoToListCredits(List<CreditDto> creditDtoList);

  List<CreditCards> mapLisCardsDtoToListCards(List<CardDto> cardDtoList);
}
