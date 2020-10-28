package com.trinity.ms.invoicing.sale_session.repository;

import com.trinity.commons.model.SaleSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleSessionRepository extends JpaRepository<SaleSession, Integer> {

    List<SaleSession> findAllByUserIdAndAndSaleSessionStatusId(Integer userId, Short saleSessionStatusId);

    SaleSession findByUserIdAndSaleSessionStatusId(Integer userId, Short SaleSessionStatusId);

    @Query("select s from SaleSession s where s.userId = :userId and s.saleSessionStatusId in( :statuses)")
    SaleSession getCurrentSession( @Param("userId") Integer userId, @Param("statuses") List<Short> statuses );

    @Query("select s from SaleSession s where s.userId = :userId and s.saleSessionStatusId in( :statuses )")
    List<SaleSession> getCurrentSessions( @Param("userId") Integer userId, @Param("statuses") List<Short> statuses );

}
