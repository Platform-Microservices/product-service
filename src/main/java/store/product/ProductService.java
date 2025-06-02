package store.product;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product create(Product product) {
        if (product.name() == null || product.name().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product name is required!");
        }

        if (product.price() == null || product.price() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid price!");
        }

        return productRepository.save(new ProductModel(product)).to();
    }

    @Cacheable("products")
    public Product findById(String id) {
        return productRepository.findById(id)
            .map(ProductModel::to)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    public List<Product> findAll() {
        return StreamSupport
            .stream(productRepository.findAll().spliterator(), false)
            .map(ProductModel::to)
            .toList();
    }

    public void delete(String id) {
        if (!productRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        productRepository.deleteById(id);
    }
    
}