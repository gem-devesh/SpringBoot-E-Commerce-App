package in.devesh.ecommerce.controller;

import in.devesh.ecommerce.dto.ProductDTO;
import in.devesh.ecommerce.global.Constants;
import in.devesh.ecommerce.model.Category;
import in.devesh.ecommerce.model.Product;
import in.devesh.ecommerce.service.CategoryService;
import in.devesh.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    Product product;

    @GetMapping("/admin")
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String redirectToCategories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String addCategories(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postCategoryAdd(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.findCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        } else {
            return "404";
        }
    }

    /* PRODUCT SECTION CONTROLLERS START */
    @GetMapping("/admin/products")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String addProductsGet(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String addProductsPost(@ModelAttribute("productDTO") ProductDTO productDTO,
                                  @RequestParam("productImage") MultipartFile file,
                                  @RequestParam("imgName") String imgName) throws IOException {

        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.findCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());

        String imageUUID;
        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(Constants.imagesDirectory, imageUUID);
            Files.write(fileNameAndPath, file.getBytes());
        } else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);
        return Constants.Redirections.adminProducts;
    }

    @GetMapping("/admin/product/delete/{id}")
    public String removeProduct(@PathVariable int id) {
        productService.removeProductById(id);
        return Constants.Redirections.adminProducts;
    }

    @GetMapping(Constants.ApiEndpointsMapping.updateProduct)
    public String updateProduct(@PathVariable long id, Model model) {
        if(productService.getProductById(id).isPresent()){
            Product product = productService.getProductById(id).get();
            ProductDTO productDTO = new ProductDTO();
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setCategoryId(product.getCategory().getId());
            productDTO.setPrice(product.getPrice());
            productDTO.setWeight(product.getWeight());
            productDTO.setDescription(product.getDescription());
            productDTO.setImageName(product.getImageName());

            model.addAttribute(Constants.Functionalities.categories, categoryService.getAllCategories());
            model.addAttribute(Constants.DTO.productDTO, productDTO);
        }

        return Constants.Redirections.productsAdd;
    }

}
