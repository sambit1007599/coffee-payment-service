package com.accenture.coffee.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.accenture.coffee.dto.AmountOwesByUser;
import com.accenture.coffee.dto.AmountPaidByUser;
import com.accenture.coffee.util.DataFeederTest;

public class PaymentServiceTest {

  private static final String NULL = "NULL";

  private PaymentService paymentService;

  @BeforeEach
  public void setUp() {
    paymentService = new PaymentService();
  }

  @ParameterizedTest(name = "#{index} - Test retrieveAmountPaid of PaymentService for user: {0}")
  @MethodSource("userDataProvider")
  public void testRetrieveAmountPaid(String user) {

    List<AmountPaidByUser> amountPaidByUsers;

    if (NULL.equalsIgnoreCase(user)) {
      List<AmountPaidByUser> expectedAmountPaidByUsers = DataFeederTest.getAmountPaidByUserData();
      amountPaidByUsers = paymentService.retrieveAmountPaid(null);
      assertThat(amountPaidByUsers).isEqualTo(expectedAmountPaidByUsers);
    } else {
      double amount = 77;
      amountPaidByUsers = paymentService.retrieveAmountPaid(user);
      assertThat(amountPaidByUsers.size()).isEqualTo(1);
      assertThat(amountPaidByUsers.get(0).getUserName()).isEqualTo(user);
      assertThat(amountPaidByUsers.get(0).getAmountPaid()).isEqualTo(amount);
    }

    assertThat(amountPaidByUsers).isNotNull();

  }

  @ParameterizedTest(name = "#{index} - Test getAmountOwesByUsers of PaymentService for user: {0}")
  @MethodSource("userDataProvider")
  public void testGetAmountOwesByUsers(String user) {
    List<AmountOwesByUser> amountOwesByUsers;

    if (NULL.equalsIgnoreCase(user)) {
      List<AmountOwesByUser> expectedAmountOwesByUsers = DataFeederTest.getAmountOwesByUserData();
      amountOwesByUsers = paymentService.getAmountOwesByUsers(null);
      assertThat(amountOwesByUsers).isEqualTo(expectedAmountOwesByUsers);
    } else {
      double amount = 29.25;
      amountOwesByUsers = paymentService.getAmountOwesByUsers(user);
      assertThat(amountOwesByUsers.size()).isEqualTo(1);
      assertThat(amountOwesByUsers.get(0).getUserName()).isEqualTo(user);
      assertThat(amountOwesByUsers.get(0).getAmountOwes()).isEqualTo(amount);
    }

    assertThat(amountOwesByUsers).isNotNull();
  }

  private static Stream<Arguments> userDataProvider() {
    return Stream.of(
        Arguments.of("bill"),
        Arguments.of(NULL)
    );
  }
}
