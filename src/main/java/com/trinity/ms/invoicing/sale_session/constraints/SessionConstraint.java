package com.trinity.ms.invoicing.sale_session.constraints;

import com.trinity.commons.Validator;
import com.trinity.commons.model.SaleSession;
import com.trinity.ms.invoicing.sale_session.repository.SaleSessionRepository;

import java.util.List;

public class SessionConstraint implements Validator<Integer> {

    private SaleSessionRepository saleSessionRepository;

    public SessionConstraint(SaleSessionRepository saleSessionRepository) {
        this.saleSessionRepository = saleSessionRepository;
    }

    @Override
    public boolean validate(Integer userId) {
        Short saleSessionStatusId = 1;
        List<SaleSession> sessions = saleSessionRepository.findAllByUserIdAndAndSaleSessionStatusId(userId, saleSessionStatusId);
        return sessions.isEmpty();
    }

    @Override
    public String getMessage() {
        return "Session already exists";
    }
}
