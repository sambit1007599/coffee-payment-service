package com.accenture.coffee.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import com.accenture.coffee.dto.AmountOwesByUser;
import com.accenture.coffee.dto.AmountPaidByUser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@UtilityClass
@Slf4j
public class DataFeederTest {

  private final ObjectMapper objectMapper = new ObjectMapper();

  public List<AmountPaidByUser> getAmountPaidByUserData() {
    try {
      return objectMapper
          .readValue(new File(DataFeederTest.class.getClassLoader().getResource("amountPaidByUser.json").getFile())
              , new TypeReference<>() {

              });

    } catch (IOException e) {
      log.error("IO exception occured while retrieving amountPaidByUser details: {} ", e);
      throw new RuntimeException(e);
    }
  }

  public List<AmountOwesByUser> getAmountOwesByUserData() {
    try {
      return objectMapper
          .readValue(new File(DataFeederTest.class.getClassLoader().getResource("amountOwesByUser.json").getFile())
              , new TypeReference<>() {

              });

    } catch (IOException e) {
      log.error("IO exception occured while retrieving amountOwesByUser details: {} ", e);
      throw new RuntimeException(e);
    }
  }
}
