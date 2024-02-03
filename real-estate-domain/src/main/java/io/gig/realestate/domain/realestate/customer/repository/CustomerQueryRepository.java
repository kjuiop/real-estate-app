package io.gig.realestate.domain.realestate.customer.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.customer.CustomerInfo;
import io.gig.realestate.domain.realestate.customer.dto.CustomerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static io.gig.realestate.domain.realestate.customer.QCustomerInfo.customerInfo;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerQueryRepository {

    private final JPAQueryFactory queryFactory;

    public List<CustomerDto> getCustomerListInfoByRealEstateId(Long realEstateId) {

        JPAQuery<CustomerDto> contentQuery = this.queryFactory
                .select(Projections.constructor(CustomerDto.class,
                        customerInfo
                ))
                .from(customerInfo)
                .where(defaultCondition())
                .where(eqRealEstateId(realEstateId))
                .orderBy(customerInfo.id.asc());

        return contentQuery.fetch();
    }

    public CustomerInfo getCustomerById(Long customerId) {

        JPAQuery<CustomerInfo> contentQuery = this.queryFactory
                .selectFrom(customerInfo)
                .where(defaultCondition())
                .where(eqCustomerId(customerId));

        return contentQuery.fetchOne();
    }

    private BooleanExpression defaultCondition() {
        return customerInfo.deleteYn.eq(YnType.N);
    }

    private BooleanExpression eqRealEstateId(Long realEstateId) {
        return realEstateId != null ? customerInfo.realEstate.id.eq(realEstateId) : null;
    }

    private BooleanExpression eqCustomerId(Long customerId) {
        return customerId != null ? customerInfo.id.eq(customerId) : null;
    }
}
