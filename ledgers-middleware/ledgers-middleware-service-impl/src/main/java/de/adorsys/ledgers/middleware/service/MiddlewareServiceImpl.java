/*
 * Copyright 2018-2018 adorsys GmbH & Co KG
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.adorsys.ledgers.middleware.service;


import de.adorsys.ledgers.deposit.api.domain.PaymentResultBO;
import de.adorsys.ledgers.deposit.api.domain.TransactionStatusBO;
import de.adorsys.ledgers.deposit.api.exception.PaymentNotFoundException;
import de.adorsys.ledgers.deposit.api.service.DepositAccountPaymentService;
import de.adorsys.ledgers.middleware.converter.PaymentConverter;
import de.adorsys.ledgers.middleware.service.domain.payment.PaymentResultTO;
import de.adorsys.ledgers.middleware.service.domain.payment.TransactionStatusTO;
import de.adorsys.ledgers.middleware.service.exception.AuthCodeGenerationMiddlewareException;
import de.adorsys.ledgers.middleware.service.exception.PaymentNotFoundMiddlewareException;
import de.adorsys.ledgers.middleware.service.exception.SCAOperationNotFoundMiddlewareException;
import de.adorsys.ledgers.middleware.service.exception.SCAOperationValidationMiddlewareException;
import de.adorsys.ledgers.sca.exception.AuthCodeGenerationException;
import de.adorsys.ledgers.sca.exception.SCAOperationNotFoundException;
import de.adorsys.ledgers.sca.exception.SCAOperationValidationException;
import de.adorsys.ledgers.sca.service.SCAOperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MiddlewareServiceImpl implements MiddlewareService {
    private static final Logger logger = LoggerFactory.getLogger(MiddlewareServiceImpl.class);

    private final DepositAccountPaymentService paymentService;
    private final SCAOperationService scaOperationService;

    private final PaymentConverter paymentConverter;

    public MiddlewareServiceImpl(DepositAccountPaymentService paymentService, SCAOperationService scaOperationService, PaymentConverter paymentConverter) {
        this.paymentService = paymentService;
        this.scaOperationService = scaOperationService;
        this.paymentConverter = paymentConverter;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaymentResultTO<TransactionStatusTO> getPaymentStatusById(String paymentId) throws PaymentNotFoundMiddlewareException {
        try {
            PaymentResultBO<TransactionStatusBO> paymentStatus = paymentService.getPaymentStatusById(paymentId);
            return paymentConverter.toPaymentResultTO(paymentStatus);
        } catch (PaymentNotFoundException e) {
            logger.error("Payment with id=" + paymentId + " not found", e);
            throw new PaymentNotFoundMiddlewareException(e.getMessage(), e);
        }
    }

    @Override
    public String generateAuthCode(String opId, String opData, int validitySeconds) throws AuthCodeGenerationMiddlewareException {
        try {
            return scaOperationService.generateAuthCode(opId, opData, validitySeconds);
        } catch (AuthCodeGenerationException e) {
            logger.error(e.getMessage(), e);
            throw new AuthCodeGenerationMiddlewareException(e);
        }
    }

    @Override
    @SuppressWarnings("PMD.IdenticalCatchBranches")
    public boolean validateAuthCode(String opId, String opData, String authCode) throws SCAOperationNotFoundMiddlewareException, SCAOperationValidationMiddlewareException {
        try {
            return scaOperationService.validateAuthCode(opId, opData, authCode);
        } catch (SCAOperationNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new SCAOperationNotFoundMiddlewareException(e);
        } catch (SCAOperationValidationException e) {
           logger.error(e.getMessage(),e);
           throw new SCAOperationValidationMiddlewareException(e);
        }
    }
}
