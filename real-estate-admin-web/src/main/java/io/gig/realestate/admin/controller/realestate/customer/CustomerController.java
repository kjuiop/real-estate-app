package io.gig.realestate.admin.controller.realestate.customer;

import io.gig.realestate.admin.util.ApiResponse;
import io.gig.realestate.domain.admin.LoginUser;
import io.gig.realestate.domain.realestate.customer.CustomerService;
import io.gig.realestate.domain.realestate.customer.dto.CustomerCreateForm;
import io.gig.realestate.domain.realestate.customer.dto.CustomerDto;
import io.gig.realestate.domain.utils.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : JAKE
 * @date : 2023/10/02
 */
@Controller
@RequestMapping("real-estate/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("{realEstateId}")
    public ResponseEntity<ApiResponse> getPriceData(
            @PathVariable(name = "realEstateId") Long realEstateId) {
        List<CustomerDto> customerList = customerService.getCustomerListInfoByRealEstateId(realEstateId);
        return new ResponseEntity<>(ApiResponse.OK(customerList), HttpStatus.OK);
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody CustomerCreateForm createForm,
                                            @CurrentUser LoginUser loginUser) {
        Long realEstateId = customerService.create(createForm, loginUser);
        return new ResponseEntity<>(ApiResponse.OK(realEstateId), HttpStatus.OK);
    }

}
