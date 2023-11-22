package io.gig.realestate.admin.controller.map;

import io.gig.realestate.domain.area.AreaService;
import io.gig.realestate.domain.area.dto.AreaListDto;
import io.gig.realestate.domain.realestate.basic.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.basic.RealEstateService;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateDetailDto;
import io.gig.realestate.domain.realestate.basic.dto.RealEstateListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@Controller
@RequestMapping("map")
@RequiredArgsConstructor
public class MapController {

    private final RealEstateService realEstateService;
    private final AreaService areaService;

    @GetMapping
    public String map(HttpServletRequest request, RealEstateSearchDto searchDto, Model model) {

        HttpSession session = request.getSession();

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

        Page<RealEstateListDto> pages = realEstateService.getRealEstatePageListBySearch(session.getId(), searchDto);

        model.addAttribute("condition", searchDto);
        model.addAttribute("pages", pages);

        return "map/map";
    }
}
