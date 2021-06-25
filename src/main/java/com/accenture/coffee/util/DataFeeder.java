package com.accenture.coffee.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import com.accenture.coffee.dto.Order;
import com.accenture.coffee.dto.Payment;
import com.accenture.coffee.dto.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The type Data feeder.
 */
@UtilityClass
@Slf4j
public class DataFeeder {

  private final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Gets order details data.
   *
   * @return the order details data
   */
  public List<Order> getOrderDetailsData() {
    try {
      return objectMapper
          .readValue(new File(DataFeeder.class.getClassLoader().getResource("data/orders.json").getFile())
              , new TypeReference<>() {

              });

    } catch (IOException e) {
      log.error("IO exception occured while retrieving order details: {} ", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets payment details data.
   *
   * @return the payment details data
   */
  public List<Payment> getPaymentDetailsData() {
    try {
      return objectMapper
          .readValue(new File(DataFeeder.class.getClassLoader().getResource("data/payments.json").getFile())
              , new TypeReference<>() {

              });

    } catch (IOException e) {
      log.error("IO exception occured while retrieving payment details: {} ", e);
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets product details data.
   *
   * @return the product details data
   */
  public Map<String, Map<String, Double>> getProductDetailsData() {
    try {
      return objectMapper
          .readValue(new File(DataFeeder.class.getClassLoader().getResource("data/products.json").getFile())
              , new TypeReference<List<Product>>() {

              }).stream()
          .collect(Collectors.toMap(Product::getDrinkName, Product::getPrices, (drinkName, prices) -> drinkName));

    } catch (IOException e) {
      log.error("IO exception occured while retrieving product details: {} ", e);
      throw new RuntimeException(e);
    }
  }
}
