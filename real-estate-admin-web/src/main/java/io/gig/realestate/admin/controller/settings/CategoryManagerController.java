package io.gig.realestate.admin.controller.settings;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.category.CategoryService;
import io.gig.realestate.domain.category.dto.CategoryCreateForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author : JAKE
 * @date : 2023/03/29
 */
@Controller
@RequestMapping("settings/category-manager")
@RequiredArgsConstructor
public class CategoryManagerController {

    private final CategoryService categoryService;

    @GetMapping
    public String index() {
        return "settings/category/category-manager";
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody CategoryCreateForm createForm) {
        Long categoryId = categoryService.create(createForm);
        return new ResponseEntity<>(ApiResponse.OK(categoryId), HttpStatus.OK);
    }

}
