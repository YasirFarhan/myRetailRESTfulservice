1. API retrieves product information from https://redsky.target.com, combines it with a locally stored  price and returns it to the caller. <br/>
2. Client does a GET or a PUT request at path '/products/{id}       <br/>
3. Product price information is locally stored in a text file named Product.txt<br/>
4. In  class DaoImpl package Dao, variable "PRODUCT_FILE"  stores the absolute path to the Product.txt file. This path will have to be updated to point to the text file.<br/>
5. Exceptions:<br/>
(a). UpdateIntegrityException is thrown when making a PUT request and there is a mismatch in product id in the url and product id supplied in the request body<br/>
(b). ProductNotFoundEexception is thrown when the product id supplied by the caller does not exist in the local storage<br/>
(c). RestClientException is thrown when product information is not supplied by https://redsky.target.com<br/>
(d). FilePersistanceException is thrown when Product.txt file either does not exist or the application is not pointed to the correct location.<br/>
Please read step 3 to fix this error.

6. Clippings of test results are provided in .BMP files.



![title](1.TestPUTUpdateIntegrityException.BMP)