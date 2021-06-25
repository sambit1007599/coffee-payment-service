package com.accenture.coffee.dto;

import java.util.Map;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonAlias;

/**
 * The type Product.
 */
@Data
public class Product {

  @JsonAlias("drink_name")
  private String drinkName;
  private Map<String, Double> prices;

}
