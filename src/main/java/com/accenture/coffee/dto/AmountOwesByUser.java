package com.accenture.coffee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Amount owes by user.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountOwesByUser {

  private String userName;
  private double amountOwes;
}
