/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

/**
 *
 * @author Kanwal
 */
public class FilePersistanceException extends Exception  {

    public FilePersistanceException(String message) {
        super(message);
    }

    public FilePersistanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
