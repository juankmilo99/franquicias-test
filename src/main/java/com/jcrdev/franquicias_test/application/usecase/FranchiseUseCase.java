package com.jcrdev.franquicias_test.application.usecase;

import com.jcrdev.franquicias_test.application.model.BranchTopProduct;
import com.jcrdev.franquicias_test.application.port.out.BranchRepositoryPort;
import com.jcrdev.franquicias_test.application.port.out.FranchiseRepositoryPort;
import com.jcrdev.franquicias_test.application.port.out.ProductRepositoryPort;
import com.jcrdev.franquicias_test.domain.exception.BusinessException;
import com.jcrdev.franquicias_test.domain.exception.NotFoundException;
import com.jcrdev.franquicias_test.domain.model.Branch;
import com.jcrdev.franquicias_test.domain.model.Franchise;
import com.jcrdev.franquicias_test.domain.model.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FranchiseUseCase {

    private final FranchiseRepositoryPort franchiseRepository;
    private final BranchRepositoryPort branchRepository;
    private final ProductRepositoryPort productRepository;

    public FranchiseUseCase(
            FranchiseRepositoryPort franchiseRepository,
            BranchRepositoryPort branchRepository,
            ProductRepositoryPort productRepository
    ) {
        this.franchiseRepository = franchiseRepository;
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
    }

    public Mono<Franchise> createFranchise(String name) {
        Franchise franchise = new Franchise(null, name, LocalDateTime.now());
        return franchiseRepository.save(franchise);
    }

    public Mono<Branch> addBranchToFranchise(UUID franchiseId, String name) {
        return requireFranchise(franchiseId)
                .then(Mono.defer(() -> {
                    Branch branch = new Branch(null, name, franchiseId, LocalDateTime.now());
                    return branchRepository.save(branch);
                }));
    }

    public Mono<Product> addProductToBranch(UUID branchId, String name, Integer stock) {
        validateStock(stock);
        return requireBranch(branchId)
                .then(Mono.defer(() -> {
                    Product product = new Product(null, name, stock, branchId, LocalDateTime.now());
                    return productRepository.save(product);
                }));
    }

    public Mono<Void> deleteProductFromBranch(UUID branchId, UUID productId) {
        return requireBranch(branchId)
                .then(productRepository.findByIdAndBranchId(productId, branchId)
                        .switchIfEmpty(Mono.error(new NotFoundException("Producto no encontrado en la sucursal"))))
                .flatMap(product -> productRepository.deleteById(product.id()));
    }

    public Mono<Product> updateProductStock(UUID productId, Integer stock) {
        validateStock(stock);
        return productRepository.updateStock(productId, stock)
                .switchIfEmpty(Mono.error(new NotFoundException("Producto no encontrado")));
    }

    public Flux<BranchTopProduct> getTopStockProductByBranch(UUID franchiseId) {
        return requireFranchise(franchiseId)
                .thenMany(branchRepository.findByFranchiseId(franchiseId))
                .flatMap(branch -> productRepository.findTopByBranchId(branch.id())
                        .map(product -> new BranchTopProduct(
                                branch.id(),
                                branch.name(),
                                product.id(),
                                product.name(),
                                product.stock()
                        )));
    }

    public Mono<Franchise> updateFranchiseName(UUID franchiseId, String name) {
        return franchiseRepository.updateName(franchiseId, name)
                .switchIfEmpty(Mono.error(new NotFoundException("Franquicia no encontrada")));
    }

    public Mono<Branch> updateBranchName(UUID branchId, String name) {
        return branchRepository.updateName(branchId, name)
                .switchIfEmpty(Mono.error(new NotFoundException("Sucursal no encontrada")));
    }

    public Mono<Product> updateProductName(UUID productId, String name) {
        return productRepository.updateName(productId, name)
                .switchIfEmpty(Mono.error(new NotFoundException("Producto no encontrado")));
    }

    private Mono<Franchise> requireFranchise(UUID franchiseId) {
        return franchiseRepository.findById(franchiseId)
                .switchIfEmpty(Mono.error(new NotFoundException("Franquicia no encontrada")));
    }

    private Mono<Branch> requireBranch(UUID branchId) {
        return branchRepository.findById(branchId)
                .switchIfEmpty(Mono.error(new NotFoundException("Sucursal no encontrada")));
    }

    private void validateStock(Integer stock) {
        if (stock == null || stock < 0) {
            throw new BusinessException("El stock debe ser mayor o igual a cero");
        }
    }
}
