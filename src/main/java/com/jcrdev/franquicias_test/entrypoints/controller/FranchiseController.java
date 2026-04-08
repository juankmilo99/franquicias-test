package com.jcrdev.franquicias_test.entrypoints.controller;

import com.jcrdev.franquicias_test.application.model.BranchTopProduct;
import com.jcrdev.franquicias_test.application.usecase.FranchiseUseCase;
import com.jcrdev.franquicias_test.domain.model.Branch;
import com.jcrdev.franquicias_test.domain.model.Franchise;
import com.jcrdev.franquicias_test.domain.model.Product;
import com.jcrdev.franquicias_test.entrypoints.dto.AddBranchRequest;
import com.jcrdev.franquicias_test.entrypoints.dto.AddProductRequest;
import com.jcrdev.franquicias_test.entrypoints.dto.BranchResponse;
import com.jcrdev.franquicias_test.entrypoints.dto.CreateFranchiseRequest;
import com.jcrdev.franquicias_test.entrypoints.dto.FranchiseResponse;
import com.jcrdev.franquicias_test.entrypoints.dto.ProductResponse;
import com.jcrdev.franquicias_test.entrypoints.dto.TopStockProductResponse;
import com.jcrdev.franquicias_test.entrypoints.dto.UpdateNameRequest;
import com.jcrdev.franquicias_test.entrypoints.dto.UpdateStockRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FranchiseController {

    private final FranchiseUseCase franchiseUseCase;

    public FranchiseController(FranchiseUseCase franchiseUseCase) {
        this.franchiseUseCase = franchiseUseCase;
    }

    @PostMapping("/franchises")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> createFranchise(@Valid @RequestBody CreateFranchiseRequest request) {
        return franchiseUseCase.createFranchise(request.name()).map(this::toResponse);
    }

    @PostMapping("/franchises/{franchiseId}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchResponse> addBranch(
            @PathVariable UUID franchiseId,
            @Valid @RequestBody AddBranchRequest request
    ) {
        return franchiseUseCase.addBranchToFranchise(franchiseId, request.name()).map(this::toResponse);
    }

    @PostMapping("/branches/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> addProduct(
            @PathVariable UUID branchId,
            @Valid @RequestBody AddProductRequest request
    ) {
        return franchiseUseCase.addProductToBranch(branchId, request.name(), request.stock()).map(this::toResponse);
    }

    @DeleteMapping("/branches/{branchId}/products/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteProduct(@PathVariable UUID branchId, @PathVariable UUID productId) {
        return franchiseUseCase.deleteProductFromBranch(branchId, productId);
    }

    @PatchMapping("/products/{productId}/stock")
    public Mono<ProductResponse> updateStock(
            @PathVariable UUID productId,
            @Valid @RequestBody UpdateStockRequest request
    ) {
        return franchiseUseCase.updateProductStock(productId, request.stock()).map(this::toResponse);
    }

    @GetMapping("/franchises/{franchiseId}/branches/top-stock-product")
    public Flux<TopStockProductResponse> getTopStockByBranch(@PathVariable UUID franchiseId) {
        return franchiseUseCase.getTopStockProductByBranch(franchiseId).map(this::toResponse);
    }

    @PatchMapping("/franchises/{franchiseId}/name")
    public Mono<FranchiseResponse> updateFranchiseName(
            @PathVariable UUID franchiseId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        return franchiseUseCase.updateFranchiseName(franchiseId, request.name()).map(this::toResponse);
    }

    @PatchMapping("/branches/{branchId}/name")
    public Mono<BranchResponse> updateBranchName(
            @PathVariable UUID branchId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        return franchiseUseCase.updateBranchName(branchId, request.name()).map(this::toResponse);
    }

    @PatchMapping("/products/{productId}/name")
    public Mono<ProductResponse> updateProductName(
            @PathVariable UUID productId,
            @Valid @RequestBody UpdateNameRequest request
    ) {
        return franchiseUseCase.updateProductName(productId, request.name()).map(this::toResponse);
    }

    private FranchiseResponse toResponse(Franchise franchise) {
        return new FranchiseResponse(franchise.id(), franchise.name());
    }

    private BranchResponse toResponse(Branch branch) {
        return new BranchResponse(branch.id(), branch.name(), branch.franchiseId());
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(product.id(), product.name(), product.stock(), product.branchId());
    }

    private TopStockProductResponse toResponse(BranchTopProduct topProduct) {
        return new TopStockProductResponse(
                topProduct.branchId(),
                topProduct.branchName(),
                topProduct.productId(),
                topProduct.productName(),
                topProduct.stock()
        );
    }
}
