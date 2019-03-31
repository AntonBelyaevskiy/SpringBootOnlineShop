package my.home.geekmarket.controllers;

import my.home.geekmarket.entites.Product;
import my.home.geekmarket.entites.ProductImage;
import my.home.geekmarket.services.CategoryService;
import my.home.geekmarket.services.ImageSaverService;
import my.home.geekmarket.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private CategoryService categoryService;
    private ImageSaverService imageSaverService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setImageSaverService(ImageSaverService imageSaverService) {
        this.imageSaverService = imageSaverService;
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable(name = "id") Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            product = new Product();
            product.setId(0L);
        }
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());

        return "/edit-product";
    }

    @PostMapping("/edit")
    public String processProductAddForm(
            @Valid @ModelAttribute("product") Product product,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file
    ) {
        if (product.getId() == 0 && productService.isProductWithTitleExists(product.getTitle())) {
            bindingResult.addError(
                    new ObjectError("product.title",
                            "Товар с таким названием уже существует"));// todo не отображает сообщение
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "edit-product";
        }

        if (!file.isEmpty()) {
            String pathToSavedImage = imageSaverService.saveFile(file);
            ProductImage productImage = new ProductImage();
            productImage.setPath(pathToSavedImage);
            productImage.setProduct(product);
            product.addImage(productImage);
        }

        productService.saveProduct(product);
        return "redirect:/shop";
    }
}
