package io.gig.realestate.domain.realestate.land.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

/**
 * @author : JAKE
 * @date : 2023/09/25
 */
@Getter
public class LandFrlDto {

    // 고유번호
    // 각 필지를 서로 구별하기 위하여 필지마다 붙이는 고유한 번호
    @JsonProperty("pnu")
    private long pnu;

    // 법정동코드
    // 토지가 소재한 소재지의 행정구역 명칭(법정동명)
    @JsonProperty("ldCode")
    private int ldCode;

    // ldCodeNm
    // 토지가 소재한 소재지의 행정구역코드(법정동코드) 10자리
    @JsonProperty("ldCodeNm")
    private String ldCodeNm;

    // 지번
    // 필지에 부여하여 지적공부에 등록한 번호
    @JsonProperty("mnnmSlno")
    private String mnnmSlno;

    // 대장구분코드
    // 토지가 위치한 토지의 대장 구분 (토지(임야)대장구분)코드
    @JsonProperty("regstrSeCode")
    private int regstrSeCode;

    // 대장구분명
    // 코드 정보
    @JsonProperty("regstrSeCodeNm")
    private String regstrSeCodeNm;

    // 지목코드
    // 토지의 주된 용도에 따라 토지의 종류를 구분한 지목코드
    @JsonProperty("lndcgrCode")
    private String lndcgrCode;

    // 지목명
    // 코드 정보
    @JsonProperty("lndcgrCodeNm")
    private String lndcgrCodeNm;

    // 면적(㎡)
    // 지적공부에 등록한 필지의 수평면상 넓이(㎡)
    @JsonProperty("lndpclAr")
    private double lndpclAr;

    // 소유구분코드
    // 국토를 토지 소유권 취득 주체에 따라 구분한 코드
    @JsonProperty("posesnSeCode")
    private String posesnSeCode;

    // 소유구분명
    // 코드 정보
    @JsonProperty("posesnSeCodeNm")
    private String posesnSeCodeNm;

    // 소유(공유)인수(명)
    // 토지를 공동 소유하고있는 사람수(명)
    @JsonProperty("cnrsPsnCo")
    private int cnrsPsnCo;

    // 축척구분코드
    // 토지(임야)대장에 등록된 지적도(임야도)의 축척구분 코드
    @JsonProperty("ladFrtlSc")
    private String ladFrtlSc;

    // 축척구분명
    // 코드 정보
    @JsonProperty("ladFrtlScNm")
    private String ladFrtlScNm;

    // 데이터기준일자
    // 데이터 작성 기준일자
    @JsonProperty("lastUpdtDt")
    private String lastUpdtDt;
}
