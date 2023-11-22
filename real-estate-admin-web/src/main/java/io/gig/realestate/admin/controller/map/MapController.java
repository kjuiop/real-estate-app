package io.gig.realestate.admin.controller.map;

import io.gig.realestate.domain.area.AreaService;
import io.gig.realestate.domain.area.dto.AreaListDto;
import io.gig.realestate.domain.realestate.basic.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@Controller
@RequestMapping("map")
@RequiredArgsConstructor
public class MapController {

    private final AreaService areaService;

    @GetMapping
    public String map(RealEstateSearchDto searchDto, Model model) {

        List<AreaListDto> sidoList = areaService.getParentAreaList();
        model.addAttribute("sidoList", sidoList);

        if (StringUtils.hasText(searchDto.getSido())) {
            List<AreaListDto> gunguList = areaService.getAreaListBySido(searchDto.getSido());
            model.addAttribute("gunguList", gunguList);
        }

        if (StringUtils.hasText(searchDto.getGungu())) {
            List<AreaListDto> dongList = areaService.getAreaListByGungu(searchDto.getGungu());
            model.addAttribute("dongList", dongList);
        }

        model.addAttribute("condition", searchDto);

        return "map/map";
    }
}
