/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package util.exception;

/**
 *
 * @author admin
 */
public class RoomNotFoundException extends Exception {

    // Default constructor
    public RoomNotFoundException() {
    }

    // Constructor that accepts a custom message
    public RoomNotFoundException(String message) {
        super(message);
    }
}
