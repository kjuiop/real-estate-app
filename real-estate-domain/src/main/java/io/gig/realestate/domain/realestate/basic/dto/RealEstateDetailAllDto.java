package io.gig.realestate.domain.realestate.basic.dto;

import io.gig.realestate.domain.common.YnType;
import io.gig.realestate.domain.realestate.basic.RealEstate;
import io.gig.realestate.domain.realestate.construct.dto.ConstructDto;
import io.gig.realestate.domain.realestate.construct.dto.FloorListDto;
import io.gig.realestate.domain.realestate.image.dto.ImageDto;
import io.gig.realestate.domain.realestate.land.LandInfo;
import io.gig.realestate.domain.realestate.land.dto.LandDto;
import io.gig.realestate.domain.realestate.land.dto.LandListDto;
import io.gig.realestate.domain.realestate.landprice.dto.LandPriceListDto;
import io.gig.realestate.domain.realestate.price.FloorPriceInfo;
import io.gig.realestate.domain.realestate.price.dto.PriceDto;
import io.gig.realestate.domain.realestate.print.dto.PrintDto;
import io.gig.realestate.domain.realestate.vertex.dto.VertexDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@SuperBuilder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RealEstateDetailAllDto extends RealEstateDto {

    private static final RealEstateDetailAllDto EMPTY;

    private String addressStr;

    private Long managerId;

    private Long managerTeamId;

    private Long createdById;

    private Long usageCdId;

    private String pdfTitle;

    private double sumUnitPblntfPclnd;

    private double sumUnitPblndfPclndByPyung;

    private double sumLndpclAr;

    private double sumLndpclArByPyung;

    private String useAprYear;

    private String useAprMonth;

    private LandDto landInfo;

    private PriceDto priceInfo;

    private ConstructDto constructInfo;

    private PrintDto printInfo;

    private List<LandListDto> landInfoList;

    private List<FloorListDto> constructFloorList;

    private List<ImageDto> imgList;

    private List<LandPriceListDto> landPriceList;

    static {
        EMPTY = RealEstateDetailAllDto.builder()
                .build();
    }

    public static RealEstateDetailAllDto emptyDto() {
        return EMPTY;
    }

    public RealEstateDetailAllDto(RealEstate r) {
        super(r);

        StringBuilder pdfTitle = new StringBuilder();
        StringBuilder addressStrBuilder = new StringBuilder();
        if (r.getLandInfoList().size() > 0) {
            this.landInfo = new LandDto(r.getLandInfoList().get(0));
            List<LandListDto> landList = new ArrayList<>();
            double sumUnitPblntfPclnd = 0;
            double sumUnitPblndfPclndByPyung = 0;
            double sumLndpclAr = 0;
            double sumLndpclArByPyung = 0;

            for (int i=0; i<r.getLandInfoList().size(); i++) {
                sumUnitPblntfPclnd += r.getLandInfoList().get(i).getPblntfPclnd();
                sumUnitPblndfPclndByPyung += r.getLandInfoList().get(i).getPblndfPclndByPyung();
                sumLndpclAr += r.getLandInfoList().get(i).getLndpclAr();
                sumLndpclArByPyung += r.getLandInfoList().get(i).getLndpclArByPyung();
                landList.add(new LandListDto(r.getLandInfoList().get(i)));

                String input = r.getLandInfoList().get(i).getAddress();
                int lastIndex = input.lastIndexOf(" ");
                if (lastIndex == -1) {
                    continue;
                }
                if (i == 0) {
                    addressStrBuilder.append(input);
                    continue;
                }
                addressStrBuilder.append(", ");
                String lastWord = input.substring(lastIndex + 1);
                addressStrBuilder.append(lastWord);
            }

            BigDecimal pblntfPclnd = BigDecimal.valueOf(sumUnitPblntfPclnd);
            sumUnitPblntfPclnd = pblntfPclnd.setScale(2, RoundingMode.HALF_UP).doubleValue();

            BigDecimal pblndfPclndByPyung = BigDecimal.valueOf(sumUnitPblndfPclndByPyung);
            sumUnitPblndfPclndByPyung = pblndfPclndByPyung.setScale(2, RoundingMode.HALF_UP).doubleValue();

            this.sumUnitPblntfPclnd = sumUnitPblntfPclnd;
            this.sumUnitPblndfPclndByPyung = sumUnitPblndfPclndByPyung;
            this.sumLndpclAr = sumLndpclAr;
            this.sumLndpclArByPyung = sumLndpclArByPyung;
            this.landInfoList = landList;
            this.addressStr = addressStrBuilder.toString();
        }

        if (r.getPriceInfoList().size() > 0) {
            PriceDto priceDto = new PriceDto(r.getPriceInfoList().get(0));
            this.priceInfo = priceDto;
            if (priceDto.getSalePrice() > 0) {
                int salePrice = (int) priceDto.getSalePrice();
                pdfTitle.append(salePrice);
                pdfTitle.append("억");
                pdfTitle.append("_");
            }
        }

        if (r.getConstructInfoList().size() > 0) {
            ConstructDto constructDto = new ConstructDto(r.getConstructInfoList().get(0));
            if (StringUtils.hasText(constructDto.getUseAprDate())) {
                String[] strArray = constructDto.getUseAprDate().split("-");
                this.useAprYear = strArray[0];
                this.useAprMonth = strArray[1];
            }

            this.constructInfo = constructDto;
        }

        if (r.getPrintInfoList().size() > 0) {
            this.printInfo = new PrintDto(r.getPrintInfoList().get(0));
        }

        if (r.getFloorPriceInfo().size() > 0) {
            List<FloorListDto> constructFloorList = new ArrayList<>();
            for (FloorPriceInfo floor : r.getFloorPriceInfo()) {
                constructFloorList.add(new FloorListDto(floor));
            }
            this.constructFloorList = constructFloorList;
        }

        if (r.getLandPriceInfoList().size() > 0) {
            this.landPriceList = r.getLandPriceInfoList().stream().map(LandPriceListDto::new).collect(Collectors.toList());
        }

        if (r.getSubImgInfoList() != null && r.getSubImgInfoList().size() > 0)  {
            List<ImageDto> imgs = new ArrayList<>();
            for (int i=0; i<r.getSubImgInfoList().size(); i++) {
                if (i == 0) {
                    continue;
                }
                imgs.add(new ImageDto(r.getSubImgInfoList().get(i)));
            }
            this.imgList = imgs;
        }

        pdfTitle.append(r.getAddress());
        pdfTitle.append("_");

        if (r.getUsageType() != null) {
            pdfTitle.append(r.getUsageType().getName());
            pdfTitle.append("_");
        }

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = today.format(formatter);

        pdfTitle.append(formattedDate);
        this.pdfTitle = pdfTitle.toString();
    }
}
