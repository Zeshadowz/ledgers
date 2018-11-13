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

package de.adorsys.ledgers;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import de.adorsys.ledgers.deposit.api.service.EnableDepositAccountService;
import de.adorsys.ledgers.middleware.EnableLedgersMiddleware;
import de.adorsys.ledgers.postings.impl.EnablePostingService;

@EntityScan
@EnableScheduling
@EnableJpaAuditing
@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = {"de.adorsys.ledgers.sca","de.adorsys.ledgers.um","de.adorsys.ledgers.app"})
@EnableLedgersMiddleware
@EnableDepositAccountService
@EnablePostingService
public class LedgersApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(LedgersApplication.class).run(args);
    }
}