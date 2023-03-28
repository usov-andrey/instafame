/*
 * All rights reserved by Xalap.
 * Please email contact@xalap.com if you would like permission to do something with the contents of this repository.
 */

package com.xalap.crm.service.integration.datalayer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Усов Андрей
 * @since 18/01/2019
 */
public class DataLayerService {

    public DataLayer purchase(String transactionId, Double revenueWithTaxes, Double tax) {
        DataLayer dataLayer = new DataLayer();
        dataLayer.setEvent("purchase");
        Ecommerce ecommerce = new Ecommerce();
        Purchase purchase = new Purchase();
        ActionField actionField = new ActionField();
        dataLayer.setEcommerce(ecommerce);
        ecommerce.setPurchase(purchase);
        purchase.setActionField(actionField);
        actionField.setId(transactionId);
        actionField.setRevenue(Double.toString(revenueWithTaxes));
        actionField.setTax(Double.toString(tax));
        return dataLayer;
    }

    public void addProduct(DataLayer dataLayer, Integer productId, String productName, Double price, int quantity) {
        List<Product> products = dataLayer.getEcommerce().getPurchase().getProducts();
        if (products == null) {
            products = new ArrayList<>();
            dataLayer.getEcommerce().getPurchase().setProducts(products);
        }
        Product product = new Product();
        product.setId(Integer.toString(productId));
        product.setName(productName);
        product.setPrice(Double.toString(price));
        product.setQuantity(quantity);
        products.add(product);
    }
}
