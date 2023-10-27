package io.gig.realestate.domain.realestate.land.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.land.LandInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * @author : JAKE
 * @date : 2023/09/25
 */
@SuperBuilder
@Getter
@NoArgsConstructor
public class LandDto {

    // 데이터 검증 필요
    private Long landId;

    private String address;

    /** 토지 필지 번호 **/
    // 고유번호
    // 각 필지를 서로 구별하기 위하여 필지마다 붙이는 고유한 번호
    private String pnu;

    private String pnuStr;

    /** 토지면적 **/
    // 각 필지의 지적공부에 등록한 필지의 수평면상 넓이의 합계(㎡)
    private Double lndpclAr;

    /** 토지면적 **/
    // 각 필지의 지적공부에 등록한 필지의 수평면상 넓이의 합계(㎡)
    private Double lndpclArByPyung;


    /** 지목 **/
    // imreal 에서는 용도지역을 지목으로 사용함
    // 토지의 주된 용도에 따라 토지의 종류를 구분한 지목코드의 코드정보
    private String lndcgrCodeNm;

    /** 용도지역 **/
    // imreal 에서는 지목을 용도지역으로 사용함
    // 도시계획구역 안에서 토지의 효율적인 이용과 공공복리 및 도시기능의 증진을 도모하기 위하여 용도가 지정된 지역을 표시하는 코드정보
    private String prposArea1Nm;

    /** 토지이용상황 **/
    // 토지의 실제이용상황 및 주위의 주된 토지이용상황을 표시하는 코드정보
    private String ladUseSittnNm;

    /** 지형높이 **/
    // 토지에 대한 높이(고저)를 표시하는 코드정보
    private String tpgrphHgCodeNm;

    /** 지형형상 **/
    // 토지형태(모양새)를 표시하는 코드정보
    private String tpgrphFrmCodeNm;

    /** 도로접면 **/
    // 법률에는 도로접면으로 표시되며 필지에 대하여 인접한 도로와의 관계를 구분하는 코드정보
    private String roadSideCodeNm;

    /** 토지면적당 공시지가 **/
    // 대한민국의 건설교통부가 토지의 가격을 조사, 감정을 해 공시함. 개별토지에한 공시 가격(원/㎡)
    private Double pblntfPclnd;

    /** 토지면적당 공시지가 합계 **/
    // 대한민국의 건설교통부가 토지의 가격을 조사, 감정을 해 공시함. 개별토지에한 공시 가격(원/㎡)
    private Double totalPblntfPclnd;

    /** 공시지가 년도 **/
    // 공시 기준년도
    private Integer stdrYear;

    private YnType commercialYn;

    public LandDto(LandInfo l) {
        this.landId = l.getId();
        this.pnu = l.getPnu();
        this.pnuStr = l.getPnu();
        this.address = l.getAddress();
        this.lndpclAr = l.getLndpclAr();
        this.lndpclArByPyung = l.getLndpclArByPyung();
        this.lndcgrCodeNm = l.getLndcgrCodeNm();
        this.prposArea1Nm = l.getPrposArea1Nm();
        this.tpgrphHgCodeNm = l.getTpgrphHgCodeNm();
        this.tpgrphFrmCodeNm = l.getTpgrphFrmCodeNm();
        this.roadSideCodeNm = l.getRoadSideCodeNm();
        this.pblntfPclnd = l.getPblntfPclnd();
        this.totalPblntfPclnd = l.getTotalPblntfPclnd();
        this.ladUseSittnNm = l.getLadUseSittnNm();
        this.commercialYn = l.getCommercialYn();
    }
}
