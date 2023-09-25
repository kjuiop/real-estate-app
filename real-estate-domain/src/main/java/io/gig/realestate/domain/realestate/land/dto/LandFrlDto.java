package io.gig.realestate.domain.realestate.land.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author : JAKE
 * @date : 2023/09/25
 */
public class LandFrlDto {

    @JsonProperty("lndpclAr")
    private double lndpclAr;

    @JsonProperty("lndcgrCodeNm")
    private String lndcgrCodeNm;

    @JsonProperty("ldCode")
    private int ldCode;

    @JsonProperty("ldCodeNm")
    private String ldCodeNm;

    @JsonProperty("ladFrtlScNm")
    private String ladFrtlScNm;

    @JsonProperty("posesnSeCode")
    private String posesnSeCode;

    @JsonProperty("mnnmSlno")
    private String mnnmSlno;

    @JsonProperty("lndcgrCode")
    private String lndcgrCode;

    @JsonProperty("cnrsPsnCo")
    private int cnrsPsnCo;

    @JsonProperty("ladFrtlSc")
    private String ladFrtlSc;

    @JsonProperty("pnu")
    private long pnu;

    @JsonProperty("posesnSeCodeNm")
    private String posesnSeCodeNm;

    @JsonProperty("lastUpdtDt")
    private String lastUpdtDt;

    @JsonProperty("regstrSeCodeNm")
    private String regstrSeCodeNm;

    @JsonProperty("regstrSeCode")
    private int regstrSeCode;

}
