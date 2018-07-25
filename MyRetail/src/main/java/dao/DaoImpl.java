package dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import model.Product;

/**
 *
 * @author Yasir Farhan
 */
public class DaoImpl implements Dao {

    public static final String PRODUCT_FILE = "E:\\GItHub\\myRetailRESTfulservice\\MyRetail\\Products.txt";
    public static final String DELIMITER = "::";
    private Map<Long, Product> productMap = new HashMap<>();

    @Override
    public List<Product> getAllProducts() throws FilePersistanceException, IOException {

        loadProductFile();
        return productMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Product getProduct(long product_id) throws FilePersistanceException, IOException {
        loadProductFile();
        return productMap.get(product_id);
    }

    @Override
    public Product updateProduct(Product product) throws IOException, FilePersistanceException {
        loadProductFile();
        if (productMap.get(product.getId()) != null) {
            productMap.put(product.getId(), product);
            updateProductFile();
            loadProductFile();
        }

        return product;
    }

    private void loadProductFile() throws FilePersistanceException, IOException {

        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FilePersistanceException(
                    "*****    Could not load Product Information Please update file file path in class DaoImpl.java   -_-    *****", e);
        }
        String currentLine;
        String[] currentTokens;
        int counter = 0;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            if (counter > 0) {
                currentTokens = currentLine.split(DELIMITER);
                Product currentProduct = new Product();
                currentProduct.setId(Long.parseLong(currentTokens[0]));
                currentProduct.setCurrent_price(Double.parseDouble(currentTokens[1]));
                currentProduct.setCurrency_code(currentTokens[2]);
                productMap.put(currentProduct.getId(), currentProduct);
            }
            counter++;
        }
        scanner.close();
    }

    // update the text file with price
    private void updateProductFile() throws IOException, FilePersistanceException {
        PrintWriter out = new PrintWriter(new FileWriter(PRODUCT_FILE));
        try {
            out = new PrintWriter(new FileWriter(PRODUCT_FILE));
            out.println("id ::current_price::currency_code");
        } catch (IOException e) {
            throw new FilePersistanceException("Could not save item data.", e);
        }
        List<Product> itemList = getAllProducts();
        for (Product p : itemList) {
            out.println(p.getId() + DELIMITER + p.getCurrent_price()
                    + DELIMITER + p.getCurrency_code()
            );
            out.flush();
        }
        out.close();
    }

}
