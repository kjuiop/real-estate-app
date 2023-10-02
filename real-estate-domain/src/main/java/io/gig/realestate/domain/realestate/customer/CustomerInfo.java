package io.gig.realestate.domain.realestate.customer;

import io.gig.realestate.domain.common.BaseTimeEntity;
import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(length = 2, columnDefinition = "char(1) default 'N'")
    private YnType deleteYn = YnType.N;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "real_estate_id")
    private RealEstate realEstate;

    public static CustomerInfo create(CustomerDto dto, RealEstate realEstate) {
        return CustomerInfo.builder()
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
                .realEstate(realEstate)
                .build();
    }
}
