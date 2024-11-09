
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util.exception;

/**
 *
 * @author zchoo
 */
public class RecordNotFoundException extends Exception{

    public RecordNotFoundException() {
    }
    
    public RecordNotFoundException(String msg) {
        super(msg);
    }

    public RecordNotFoundException(RecordNotFoundException ex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
