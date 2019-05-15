package com.myRetailRESTfulservice.myRetailRESTfulservice.controller;

import com.myRetailRESTfulservice.myRetailRESTfulservice.exceptions.ProductNotFoundException;
import com.myRetailRESTfulservice.myRetailRESTfulservice.exceptions.UpdateIntegrityException;
import com.myRetailRESTfulservice.myRetailRESTfulservice.model.Product;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;

@RestController
public class RESTController {

    @GetMapping(value = "/products/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable("id") long id) throws Exception{
        Product product = dao.getProduct(id);
        if (product == null) {
            throw new ProductNotFoundException("Product does not exist in local storage");
        }
        try {
            product = callExternalApi(product);
        } catch (Exception e) {
            throw new ProductNotFoundException(e.getMessage());
        }
        return product;
    }

    @PutMapping(value = "/products/{id}")
    public void updateProduct(@PathVariable("id") long id, @RequestBody Product product)
            throws Exception{
        if (id != product.getId()) {
            throw new UpdateIntegrityException("Product id on the url must match Product id in the submitted data.");
        }
        if (dao.getProduct(id) == null) {
            throw new ProductNotFoundException("Product does not exist in local storage");
        }
        try {
            dao.updateProduct(product);
        } catch (Exception e) {
            throw new ProductNotFoundException(e.getMessage());
        }
    }


    private Product callExternalApi(Product prod) throws IOException, ProductNotFoundException {

        String productId = Long.toString(prod.getId());
        String url = "https://redsky.target.com/v2/pdp/tcin/"
                + productId
                + "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
        Response restTemplate = new res();
        Map<String, String> idMap = new HashMap<>();
        idMap.put("id", productId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ResponseEntity<String> jsonResponse = restTemplate.getForEntity(url, String.class, idMap);
            Map<String, Map> infoMap = infoMap = objectMapper.readValue(jsonResponse.getBody(), Map.class);
            Map<String, Map> productMap = infoMap.get("product");
            Map< String, Map<String, String>> itemMap = productMap.get("item");
            String prodDesc = itemMap.get("product_description").get("title");
            prod.setName(prodDesc);
        } catch (IOException | RestClientException e) {
            throw new ProductNotFoundException("Product does not exist at redsky.target.com.   Error Mesage :  " + e.getMessage());
        }

        return prod;

    }
}
