package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getProductId() == null) {
            product.setProductId(java.util.UUID.randomUUID().toString());
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        for (Product product : productData) {
            if (product.getProductId().equals(id))  {
                return product;
            }
        }
        return null;
    }

    public Product update(Product editedProduct) {
        for (int i=0; i<productData.size(); i++) {
            if (productData.get(i).getProductId().equals(editedProduct.getProductId())) {
                productData.set(i, editedProduct);
                return editedProduct;
            }
        }
        return null;
    }

    public Product delete(String id) {
        Product product = findById(id);
        if (product != null) {
            productData.removeIf(p -> p.getProductId().equals(id));
        }
        return product;
    }
}
