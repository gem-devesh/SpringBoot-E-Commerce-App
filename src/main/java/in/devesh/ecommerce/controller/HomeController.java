package in.devesh.ecommerce.controller;

import in.devesh.ecommerce.global.Constants;
import in.devesh.ecommerce.service.CategoryService;
import in.devesh.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping({Constants.Redirections.root, Constants.Redirections.home})
    public String homePage(Model model) {
        return Constants.Functionalities.index;
    }

    @GetMapping(Constants.Redirections.shop)
    public String shopPage(Model model) {
        model.addAttribute(Constants.Functionalities.categories, categoryService.getAllCategories());
        model.addAttribute(Constants.Functionalities.products, productService.getAllProducts());

        return Constants.Redirections.shop;
    }

    @GetMapping(Constants.ApiEndpointsMapping.shopByCategory)
    public String shopByCategoryPage(@PathVariable int id, Model model) {
        model.addAttribute(Constants.Functionalities.categories, categoryService.getAllCategories());
        model.addAttribute(Constants.Functionalities.products, productService.getAllProductsByCategoryId(id));

        return Constants.Redirections.shop;
    }

    @GetMapping(Constants.ApiEndpointsMapping.viewProduct)
    public String viewProduct(@PathVariable int id, Model model) {
        if (productService.getProductById(id).isPresent()) {
            model.addAttribute(Constants.Functionalities.product, productService.getProductById(id).get());
        }
        return Constants.Redirections.viewProduct;
    }
}
