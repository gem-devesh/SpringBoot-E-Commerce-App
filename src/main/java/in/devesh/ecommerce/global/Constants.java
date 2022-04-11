package in.devesh.ecommerce.global;

public class Constants {
    public static final String imagesDirectory = System.getProperty("user.dir") + "/src/main/resources/static/productImages";

    public static final class Functionalities {
        public static final String products = "products";
        public static final String product = "product";
        public static final String categories = "categories";
        public static final String index = "index";
    }

    public static final class DTO {
        public static final String productDTO = "productDTO";
    }

    public static final class ApiEndpointsMapping {
        public static final String updateProduct = "/admin/product/update/{id}";
        public static final String addProduct = "/admin/products/add";
        public static final String shopByCategory = "/shop/category/{id}";
        public static final String viewProduct = "/shop/viewproduct/{id}";

    }

    public static final class Redirections {
        public static final String productsAdd = "productsAdd";
        public static final String adminProducts = "redirect:/admin/products";
        public static final String root = "/";
        public static final String home = "/home";
        public static final String shop = "/shop";
        public static final String viewProduct = "viewProduct";
    }

    public static final class RequestParams {
        public static final String productDTO = "productDTO";
        public static final String productImage = "productImage";
        public static final String imgName = "imgName";
    }
}
