package io.gig.realestate.admin.controller.realestate;

import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.category.dto.CategoryDto;
import io.gig.realestate.domain.realestate.RealEstateSearchDto;
import io.gig.realestate.domain.realestate.dto.RealEstateDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/04/14
 */
@Controller
@RequestMapping("real-estate")
@RequiredArgsConstructor
public class RealEstateController {

    private final CategoryService categoryService;

    @GetMapping
    public String index(RealEstateSearchDto searchDto, Model model) {
        model.addAttribute("pages", null);
        model.addAttribute("condition", searchDto);
        return "realestate/list";
    }

    @GetMapping("new")
    public String register(Model model) {

        RealEstateDetailDto dto = RealEstateDetailDto.emptyDto();
        List<CategoryDto> processCds = categoryService.getChildrenCategoryDtosByName("진행구분");
        CategoryDto usageCds = categoryService.getCategoryDtoWithChildrenByName("매물용도");

        model.addAttribute("dto", dto);
        model.addAttribute("processCds", processCds);
        model.addAttribute("usageCds", usageCds);

        return "realestate/editor";
    }
}
