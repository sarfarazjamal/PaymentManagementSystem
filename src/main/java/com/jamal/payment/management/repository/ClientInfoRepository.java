package com.jamal.payment.management.repository;

import com.jamal.payment.management.domain.ClientInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ClientInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long>, JpaSpecificationExecutor<ClientInfo> {
}
