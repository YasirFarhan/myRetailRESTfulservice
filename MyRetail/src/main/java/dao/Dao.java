/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.util.List;
import model.Product;

/**
 *
 * @author Kanwal
 */
public interface Dao {

    public List<Product> getAllProducts() throws FilePersistanceException, IOException;

    public Product getProduct(long id) throws FilePersistanceException, IOException;

//    public void loadProductFile() throws FilePersistanceException;
    public Product updateProduct(Product product) throws IOException, FilePersistanceException;
}