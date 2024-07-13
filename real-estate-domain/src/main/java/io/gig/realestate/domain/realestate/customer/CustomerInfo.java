package io.gig.realestate.domain.realestate.customer;

import io.gig.realestate.domain.admin.Administrator;
import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.customer.dto.CustomerCreateForm;
import io.gig.realestate.domain.realestate.customer.dto.CustomerDto;
import io.gig.realestate.domain.realestate.customer.types.CustomerType;
import io.gig.realestate.domain.realestate.customer.types.GenderType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private CustomerType type;

    private String customerName;

    private String birth;

    private String phone;

    private String etcPhone;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    private String etcInfo;

    private String companyName;

    private String companyPhone;

    private String representName;

    private String representPhone;

    private String saleReason;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private Administrator createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_id")
    private Administrator updatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static CustomerInfo create(CustomerCreateForm dto, RealEstate realEstate, Administrator loginUser) {
        return CustomerInfo.builder()
                .id(dto.getCustomerId())
                .type(dto.getType())
                .birth(dto.getBirth())
                .customerName(dto.getCustomerName())
                .phone(dto.getPhone())
                .etcPhone(dto.getPhone())
                .gender(dto.getGender())
                .etcInfo(dto.getEtcInfo())
                .companyName(dto.getCompanyName())
                .companyPhone(dto.getCompanyPhone())
                .representName(dto.getRepresentName())
                .representPhone(dto.getRepresentPhone())
                .saleReason(dto.getSaleReason())
                .createdBy(loginUser)
                .updatedBy(loginUser)
                .realEstate(realEstate)
                .build();
    }

    public void update(CustomerCreateForm dto, Administrator loginUser) {

        this.type = dto.getType();
        this.birth = dto.getBirth();
        this.customerName = dto.getCustomerName();
        this.phone = dto.getPhone();
        this.etcPhone = dto.getEtcPhone();
        this.gender = dto.getGender();
        this.etcInfo = dto.getEtcInfo();
        this.companyName = dto.getCompanyName();
        this.companyPhone = dto.getCompanyPhone();
        this.representName =dto.getRepresentName();
        this.representPhone = dto.getRepresentPhone();
        this.saleReason = dto.getSaleReason();
        this.updatedBy = loginUser;
    }

    public void delete() {
        this.deleteYn = YnType.Y;
    }
}
