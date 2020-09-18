package com.trinity.ms.invoicing.sale_session.repository;

import com.trinity.commons.model.SaleSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleSessionRepository extends JpaRepository<SaleSession, Integer> {

    List<SaleSession> findAllByUserIdAndAndSaleSessionStatusId(Integer userId, Short saleSessionStatusId);

//    SaleSession findByUuid(String token, Short saleSessionStatusId);

}
