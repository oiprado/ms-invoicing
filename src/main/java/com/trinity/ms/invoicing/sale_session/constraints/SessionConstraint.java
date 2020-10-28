package com.trinity.ms.invoicing.sale_session.constraints;

import com.trinity.commons.Validator;
import com.trinity.commons.model.SaleSession;
import com.trinity.ms.invoicing.sale_session.repository.SaleSessionRepository;
import com.trinity.ms.invoicing.sale_session.share.SalesSessionStatus;

import java.util.Arrays;
import java.util.List;

public class SessionConstraint implements Validator<Integer> {

    private SaleSessionRepository saleSessionRepository;

    public SessionConstraint(SaleSessionRepository saleSessionRepository) {
        this.saleSessionRepository = saleSessionRepository;
    }

    @Override
    public boolean validate(Integer userId) {
        Short saleSessionStatusId = 1;
        List<Short> statuses = Arrays.asList(
            SalesSessionStatus.OPENED.getValue(),
            SalesSessionStatus.STAND_BY.getValue()
        );
        List<SaleSession> sessions = saleSessionRepository.getCurrentSessions(userId, statuses);
        return sessions.isEmpty();
    }

    @Override
    public String getMessage() {
        return "Session already exists";
    }
}
