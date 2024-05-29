package com.jalexisgarcia.beststore.controllers;

import com.jalexisgarcia.beststore.models.Product;
import com.jalexisgarcia.beststore.models.ProductDto;
import com.jalexisgarcia.beststore.services.IProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.*;

@Controller
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private IProductRepository iProductRepository;

    @GetMapping({"", "/"})
    public String showProductList(Model model) {

        List<Product> products = iProductRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products", products);
        return "products/index";
    }
    @GetMapping("/create")
    public String showCreatePage(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/CreateProduct";
    }
    @PostMapping("/create")
    public String createProduct(@Valid @ModelAttribute ProductDto productDto, BindingResult result) {
        //validar si la imagen no se sube

        if (productDto.getImageFile().isEmpty()) {
            result.addError(new FieldError("productDto", "imageFile", "the image file is required"));
        }
        if (result.hasErrors()) {
            return "products/CreateProduct";
        }

        //save image
        MultipartFile image = productDto.getImageFile();
        Date createAt = new Date();
        String storageFileName = createAt.getTime() + "_" + image.getOriginalFilename();
        try {
            String uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);
            if (!exists(uploadPath)) {
                createDirectories(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
                copy(inputStream, Paths.get(uploadDir + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (Exception ex) {
            System.out.println("Exception: "+ ex.getMessage());
        }

        //agregar todo a la bd
        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCreateAt(createAt);
        product.setImageFileName(storageFileName);
        iProductRepository.save(product);

        return "redirect:/products";

    }
    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        try {
            Optional<Product> optionalProduct = iProductRepository.findById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                ProductDto productDto = new ProductDto();
                productDto.setName(product.getName());
                productDto.setBrand(product.getBrand());
                productDto.setCategory(product.getCategory());
                productDto.setPrice(product.getPrice());
                productDto.setDescription(product.getDescription());

                model.addAttribute("product", product);
                model.addAttribute("productDto", productDto);
            } else {
                return "redirect:/products";
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/products";
        }
        return "products/EditProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(Model model, @RequestParam int id, @Valid @ModelAttribute ProductDto productDto,
                                BindingResult result) {
        try {
            Product product = iProductRepository.findById(id).get();
            model.addAttribute("product", product);
            if (result.hasErrors()) {
                return "products/EditProduct";
            }
        } catch (Exception ex) {
            System.out.println("Exception: " +ex.getMessage());
        }
        return "redirect:/products";
    }
}
