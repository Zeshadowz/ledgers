package de.adorsys.ledgers.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyCode {
    private String userId;
    private boolean verified;

    public VerifyCode(String userId) {
        this(userId, true);
    }
}
