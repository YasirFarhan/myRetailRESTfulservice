package myRetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.Dao;
import dao.FilePersistanceException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Yasir Farhan
 */
@CrossOrigin
@Controller
public class RESTController {

    Dao dao;

    public RESTController(Dao dao) {
        this.dao = dao;
    }

    /**
     * @param id
     * @return
     * @throws IOException
     * @throws ProductNotFoundException
     * @throws FilePersistanceException
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Product getProduct(@PathVariable("id") long id) throws IOException, ProductNotFoundException, FilePersistanceException {
        Product product = dao.getProduct(id);
        if (product == null) {
            throw new ProductNotFoundException("Product does not exist in local storage");
        }
        try {
            product = callExternalApi(product);
        } catch (ProductNotFoundException | IOException e) {
            throw new ProductNotFoundException(e.getMessage());
        }
        return product;
    }

    /**
     * @param id
     * @param product
     * @throws IOException
     * @throws ProductNotFoundException
     * @throws FilePersistanceException
     * @throws UpdateIntegrityException updates product price in local database
     */
    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduct(@PathVariable("id") long id, @RequestBody Product product)
            throws IOException, ProductNotFoundException, FilePersistanceException, UpdateIntegrityException {
        if (id != product.getId()) {
            throw new UpdateIntegrityException("Product id on the url must match Product id in the submitted data.");
        }
        if (dao.getProduct(id) == null) {
            throw new ProductNotFoundException("Product does not exist in local storage");
        }
        try {
            dao.updateProduct(product);
        } catch (FilePersistanceException | IOException e) {
            throw new ProductNotFoundException(e.getMessage());
        }
    }

    /**
     * Method to consume REST API using RestTemplate obtains information from
     * redsky.target.com
     */
    private Product callExternalApi(Product prod) throws IOException, ProductNotFoundException {

        String productId = Long.toString(prod.getId());
        String url = "https://redsky.target.com/v2/pdp/tcin/"
                + productId
                + "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
        RestTemplate restTemplate = new RestTemplate();
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
