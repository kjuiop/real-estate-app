package io.gig.realestate.domain.realestate.construct.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/09/28
 */
@Getter
public class ConstructDataApiDto {

    @Getter
    @Builder
    public static class Request {
        private final String sigunguCd;
        private final String bjdongCd;
        private final String platGbCd;
        private final String bun;
        private final String ji;

        public static Request assembleParam(String pnu) {
            int[] lengths = {5, 5, 1, 4, 4};

            List<String> result = new ArrayList<>();
            int currentIndex = 0;

            for (int i = 0; i < lengths.length; i++) {
                if (currentIndex + lengths[i] > pnu.length()) {
                    lengths[i] = pnu.length() - currentIndex;
                }

                result.add(pnu.substring(currentIndex, currentIndex + lengths[i]));
                currentIndex += lengths[i];

                if (currentIndex >= pnu.length()) {
                    break;
                }
            }

            return Request.builder()
                    .sigunguCd(result.get(0))
                    .bjdongCd(result.get(1))
                    .platGbCd("0")
                    .bun(result.get(3))
                    .ji(result.get(4))
                    .build();
        }
    }
}
