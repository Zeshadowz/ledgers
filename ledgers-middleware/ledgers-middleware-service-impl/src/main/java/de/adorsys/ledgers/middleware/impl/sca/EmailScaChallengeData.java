package de.adorsys.ledgers.middleware.impl.sca;

import de.adorsys.ledgers.middleware.api.domain.um.ScaMethodTypeTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailScaChallengeData extends AbstractScaChallengeData {

    @Override
    public ScaMethodTypeTO getScaMethodType() {
        return ScaMethodTypeTO.EMAIL;
    }
}
