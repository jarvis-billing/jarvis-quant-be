package com.megabloques.config;

import com.megabloques.document.Product;
import com.megabloques.document.Supply;
import com.megabloques.repository.ProductRepository;
import com.megabloques.repository.SupplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final SupplyRepository supplyRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        seedSupplies();
        seedProducts();
    }

    private void seedSupplies() {
        if (supplyRepository.count() > 0) {
            log.info("Supplies collection already has data, skipping seed.");
            return;
        }

        List<Supply> supplies = List.of(
                Supply.builder()
                        .name("Arena Amarilla").category("Arena")
                        .purchaseUnit("Metro cúbico").recipeUnit("Lata")
                        .conversionFactor(24).unitCost(12000).recipeCost(500)
                        .build(),
                Supply.builder()
                        .name("Arena Gruesa").category("Arena")
                        .purchaseUnit("Metro cúbico").recipeUnit("Lata")
                        .conversionFactor(24).unitCost(14400).recipeCost(600)
                        .build(),
                Supply.builder()
                        .name("Arena Fina").category("Arena")
                        .purchaseUnit("Metro cúbico").recipeUnit("Lata")
                        .conversionFactor(24).unitCost(13200).recipeCost(550)
                        .build(),
                Supply.builder()
                        .name("Cemento").category("Cemento")
                        .purchaseUnit("Bolsa").recipeUnit("Kilogramo")
                        .conversionFactor(42.5).unitCost(35000).recipeCost(823.53)
                        .build(),
                Supply.builder()
                        .name("Agua").category("Agua")
                        .purchaseUnit("Lata").recipeUnit("Lata")
                        .conversionFactor(1).unitCost(100).recipeCost(100)
                        .build(),
                Supply.builder()
                        .name("Triturado").category("Triturado")
                        .purchaseUnit("Metro cúbico").recipeUnit("Lata")
                        .conversionFactor(24).unitCost(19200).recipeCost(800)
                        .build(),
                Supply.builder()
                        .name("Mano de Obra Producción").category("Mano de Obra")
                        .purchaseUnit("Bolsa").recipeUnit("Bolsa")
                        .conversionFactor(1).unitCost(14000).recipeCost(14000)
                        .build(),
                Supply.builder()
                        .name("Cargue").category("Servicio")
                        .purchaseUnit("Viaje").recipeUnit("Viaje")
                        .conversionFactor(1).unitCost(0).recipeCost(0)
                        .build(),
                Supply.builder()
                        .name("Descargue").category("Servicio")
                        .purchaseUnit("Viaje").recipeUnit("Viaje")
                        .conversionFactor(1).unitCost(0).recipeCost(0)
                        .build()
        );

        supplyRepository.saveAll(supplies);
        log.info("Seeded {} supplies.", supplies.size());
    }

    private void seedProducts() {
        if (productRepository.count() > 0) {
            log.info("Products collection already has data, skipping seed.");
            return;
        }

        List<Product> products = List.of(
                Product.builder()
                        .name("Bloque Hueco 10").type("Bloque hueco")
                        .heightCm(20).lengthCm(40).widthCm(10)
                        .unitsPerBatch(60)
                        .build(),
                Product.builder()
                        .name("Bloque Macizo 10").type("Bloque macizo")
                        .heightCm(14).lengthCm(30).widthCm(10)
                        .unitsPerBatch(40)
                        .build()
        );

        productRepository.saveAll(products);
        log.info("Seeded {} products.", products.size());
    }
}
