package com.accenture.coffee.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The type Amount paid by user.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountPaidByUser {

  private String userName;
  private double amountPaid;
}
