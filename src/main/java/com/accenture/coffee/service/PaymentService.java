package com.accenture.coffee.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.accenture.coffee.dto.AmountOwesByUser;
import com.accenture.coffee.dto.AmountPaidByUser;
import com.accenture.coffee.dto.Order;
import com.accenture.coffee.dto.Payment;
import com.accenture.coffee.util.DataFeeder;

/**
 * The type Payment service.
 */
@Service
public class PaymentService {

  /**
   * Retrieve amount paid list.
   *
   * @param user the user
   * @return the list
   */
  public List<AmountPaidByUser> retrieveAmountPaid(String user) {

    List<Payment> paymentList = DataFeeder.getPaymentDetailsData();

    return paymentList.stream()
        .filter(payment -> ObjectUtils.isEmpty(user) || user.equalsIgnoreCase(payment.getUser()))
        .collect(Collectors.groupingBy(Payment::getUser))
        .entrySet().stream()
        .map(stringListEntry -> AmountPaidByUser.builder()
            .userName(stringListEntry.getKey())
            .amountPaid(stringListEntry.getValue().stream()
                .mapToDouble(Payment::getAmount)
                .sum()).build())
        .collect(Collectors.toList());
  }

  /**
   * Gets amount owes by users.
   *
   * @param user the user
   * @return the amount owes by users
   */
  public List<AmountOwesByUser> getAmountOwesByUsers(String user) {

    List<Order> orderList = DataFeeder.getOrderDetailsData();
    Map<String, Map<String, Double>> productPricesMap = DataFeeder.getProductDetailsData();

    List<AmountOwesByUser> totalAmountOwesByUserList = orderList.parallelStream()
        //Filter by user | If no user then result of all user
        .filter(order -> ObjectUtils.isEmpty(user) || user.equalsIgnoreCase(order.getUser()))
        //Filter to verify if the ordered drink is present in product
        .filter(order -> productPricesMap.containsKey(order.getDrink()) && productPricesMap.get(order.getDrink())
            .containsKey(order.getSize()))
        //mapping order amount per user to AmountOwesByUser
        .map(order -> AmountOwesByUser.builder().userName(order.getUser())
            .amountOwes(productPricesMap.get(order.getDrink()).get(order.getSize())).build())
        //grouping by user
        .collect(Collectors.groupingBy(AmountOwesByUser::getUserName))
        .entrySet().stream()
        //mapping total amount per user
        .map(stringListEntry ->
            AmountOwesByUser.builder().userName(stringListEntry.getKey())
                .amountOwes(stringListEntry.getValue().stream()
                    .mapToDouble(AmountOwesByUser::getAmountOwes)
                    .sum()).build())
        .collect(Collectors.toList());

    Map<String, Double> stringDoubleMap = retrieveAmountPaid(user).stream()
        .collect(Collectors.toMap(AmountPaidByUser::getUserName, AmountPaidByUser::getAmountPaid, (v1, v2) -> v1));

    return totalAmountOwesByUserList.stream().map(amountOwesByUser ->
        AmountOwesByUser.builder()
            .userName(amountOwesByUser.getUserName())
            .amountOwes(stringDoubleMap.get(amountOwesByUser.getUserName()) - amountOwesByUser.getAmountOwes())
            .build()
    ).collect(Collectors.toList());

  }

}
