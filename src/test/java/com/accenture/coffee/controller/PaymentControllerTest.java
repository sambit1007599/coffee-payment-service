package com.accenture.coffee.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.accenture.coffee.dto.AmountOwesByUser;
import com.accenture.coffee.dto.AmountPaidByUser;
import com.accenture.coffee.service.PaymentService;
import com.accenture.coffee.util.DataFeederTest;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

  private static final String NULL = "NULL";

  @Mock
  private PaymentService paymentService;

  private PaymentController paymentController;

  @BeforeEach
  public void setUp() {
    paymentController = new PaymentController(paymentService);
  }

  @ParameterizedTest(name = "#{index} - Test getAmountPaidPerUser of PaymentController for user: {0}")
  @MethodSource("userDataProvider")
  public void testGetAmountPaidPerUser(String user) {

    List<AmountPaidByUser> expectedAmountPaidByUsers;

    if (NULL.equalsIgnoreCase(user)) {
      user = null;
      expectedAmountPaidByUsers = DataFeederTest.getAmountPaidByUserData();
    } else {
      expectedAmountPaidByUsers = List.of(AmountPaidByUser.builder().userName(user).amountPaid(77).build());
    }

    when(paymentService.retrieveAmountPaid(user)).thenReturn(expectedAmountPaidByUsers);

    ResponseEntity<List<AmountPaidByUser>> amountPaidByUserResponseEntity =
        paymentController.getAmountPaidPerUser(user);

    assertThat(amountPaidByUserResponseEntity.getBody()).isEqualTo(expectedAmountPaidByUsers);
    assertThat(amountPaidByUserResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    verify(paymentService, times(1)).retrieveAmountPaid(user);
  }

  @ParameterizedTest(name = "#{index} - Test getAmountOwesByUsers of PaymentController for user: {0}")
  @MethodSource("userDataProvider")
  public void testGetAmountOwesByUsers(String user) {

    List<AmountOwesByUser> expectedAmountOwesByUsers;

    if (NULL.equalsIgnoreCase(user)) {
      user = null;
      expectedAmountOwesByUsers = DataFeederTest.getAmountOwesByUserData();
    } else {
      expectedAmountOwesByUsers = List.of(AmountOwesByUser.builder().userName(user).amountOwes(77).build());
    }

    when(paymentService.getAmountOwesByUsers(user)).thenReturn(expectedAmountOwesByUsers);

    ResponseEntity<List<AmountOwesByUser>> amountPaidByUserResponseEntity =
        paymentController.getAmountOwesByUsers(user);

    assertThat(amountPaidByUserResponseEntity.getBody()).isEqualTo(expectedAmountOwesByUsers);
    assertThat(amountPaidByUserResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

    verify(paymentService, times(1)).getAmountOwesByUsers(user);
  }

  private static Stream<Arguments> userDataProvider() {
    return Stream.of(
        Arguments.of("bill"),
        Arguments.of(NULL)
    );
  }

}
