package com.jcrdev.franquicias_test;

import com.jcrdev.franquicias_test.application.port.out.BranchRepositoryPort;
import com.jcrdev.franquicias_test.application.port.out.FranchiseRepositoryPort;
import com.jcrdev.franquicias_test.application.port.out.ProductRepositoryPort;
import com.jcrdev.franquicias_test.application.usecase.FranchiseUseCase;
import com.jcrdev.franquicias_test.domain.model.Branch;
import com.jcrdev.franquicias_test.domain.model.Franchise;
import com.jcrdev.franquicias_test.domain.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FranquiciasTestApplicationTests {

    @Mock
    private FranchiseRepositoryPort franchiseRepository;

    @Mock
    private BranchRepositoryPort branchRepository;

    @Mock
    private ProductRepositoryPort productRepository;

    @InjectMocks
    private FranchiseUseCase franchiseUseCase;


	@Test
	void shouldCreateFranchise() {
		Franchise saved = new Franchise(UUID.randomUUID(), "Franquicia Uno", LocalDateTime.now());
		when(franchiseRepository.save(any(Franchise.class))).thenReturn(Mono.just(saved));

		StepVerifier.create(franchiseUseCase.createFranchise("Franquicia Uno"))
				.expectNextMatches(result -> result.name().equals("Franquicia Uno") && result.id() != null)
				.verifyComplete();
	}

	@Test
	void shouldAddProductToBranch() {
		UUID branchId = UUID.randomUUID();
		Branch branch = new Branch(branchId, "Sucursal Centro", UUID.randomUUID(), LocalDateTime.now());
		Product product = new Product(UUID.randomUUID(), "Laptop", 20, branchId, LocalDateTime.now());

		when(branchRepository.findById(branchId)).thenReturn(Mono.just(branch));
		when(productRepository.save(any(Product.class))).thenReturn(Mono.just(product));

		StepVerifier.create(franchiseUseCase.addProductToBranch(branchId, "Laptop", 20))
				.expectNextMatches(result -> result.name().equals("Laptop") && result.stock() == 20)
				.verifyComplete();
	}

	@Test
	void shouldUpdateStock() {
		UUID productId = UUID.randomUUID();
		Product updated = new Product(productId, "Mouse", 99, UUID.randomUUID(), LocalDateTime.now());

		when(productRepository.updateStock(eq(productId), eq(99))).thenReturn(Mono.just(updated));

		StepVerifier.create(franchiseUseCase.updateProductStock(productId, 99))
				.expectNextMatches(result -> result.id().equals(productId) && result.stock() == 99)
				.verifyComplete();
	}

}
