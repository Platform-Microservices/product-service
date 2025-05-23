package store.product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table(name = "product")
@Setter @Accessors(fluent = true)
@NoArgsConstructor
public class ProductModel {

    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "tx_name")
    private String name;

    @Column(name = "tx_unit")
    private String unit;

    @Column(name = "double_price")
    private Double price;

    public ProductModel(Product p) {
        this.id = p.id();
        this.name = p.name();
        this.unit = p.unit();
        this.price = p.price();
    }

    public Product to() {
        return Product.builder()
            .id(this.id)
            .name(this.name)
            .unit(this.unit)
            .price(this.price)
            .build();
        }

}
