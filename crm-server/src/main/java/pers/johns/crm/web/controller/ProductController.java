package pers.johns.crm.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.johns.crm.model.vo.HttpResult;
import pers.johns.crm.model.vo.ViewProduct;
import pers.johns.crm.service.ProductService;

import java.util.List;

/**
 * ClassName    : ProductController
 * <br/>
 * Description  : 产品相关控制器
 * <br/>
 * CreateTime   : 2024/7/6 23:49
 * <br/>
 *
 * @author : JohnS
 * @version : 1.0
 */

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/names")
    public HttpResult getProductNames() {
        List<ViewProduct> productNames = productService.getProductNames();
        return HttpResult.OK("获取产品名称成功", productNames);
    }
}
