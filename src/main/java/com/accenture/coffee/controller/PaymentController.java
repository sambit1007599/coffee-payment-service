package com.accenture.coffee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.accenture.coffee.dto.AmountOwesByUser;
import com.accenture.coffee.dto.AmountPaidByUser;
import com.accenture.coffee.service.PaymentService;

/**
 * The type Payment controller.
 */
@RequestMapping("/v1")
@RequiredArgsConstructor
@RestController
@Slf4j
public class PaymentController {

  private final PaymentService paymentService;

  /**
   * Gets amount paid per user.
   *
   * @param user the user
   * @return the amount paid per user
   */
  @GetMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AmountPaidByUser>> getAmountPaidPerUser(
      @RequestParam(name = "user", required = false) String user) {
    log.info("getAmountPaidPerUser of PaymentController input request is : {}", user);
    return new ResponseEntity<>(paymentService.retrieveAmountPaid(user), HttpStatus.OK);
  }

  /**
   * Gets amount owes by users.
   *
   * @param user the user
   * @return the amount owes by users
   */
  @GetMapping(value = "/owes", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<AmountOwesByUser>> getAmountOwesByUsers(
      @RequestParam(name = "user", required = false) String user) {
    log.info("getAmountOwesByUsers of PaymentController input request is : {}", user);
    return new ResponseEntity<>(paymentService.getAmountOwesByUsers(user), HttpStatus.OK);
  }

}
