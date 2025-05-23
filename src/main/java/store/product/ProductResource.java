package store.product;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductResource implements ProductController {

    @Autowired
    private ProductService productService;

    @Override
    public ResponseEntity<ProductOut> create(@RequestBody ProductIn productin) {
        Product created = productService.create(ProductParser.to(productin));
        return ResponseEntity.ok().body(ProductParser.to(created));
    }

    @Override
    public ResponseEntity<List<ProductOut>> findAll() {
        return ResponseEntity
            .ok()
            .body(productService.findAll().stream().map(ProductParser::to).toList());
    }

    @Override
    public ResponseEntity<ProductOut> findById(@PathVariable String idProduct) {
        Product product = productService.findById(idProduct);
        return ResponseEntity.ok().body(ProductParser.to(product));
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable String idProduct) {
        productService.delete(idProduct);
        return ResponseEntity.noContent().build();
    }
    
}
