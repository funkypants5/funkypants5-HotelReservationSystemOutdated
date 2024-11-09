/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystemclient;

import ejb.session.stateless.BookingSessionBeanRemote;
import ejb.session.stateless.GuestSessionBeanRemote;
import entity.ReservationEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author zchoo
 */
public class FrontOfficeModule {

    private GuestSessionBeanRemote guestSessionBeanRemote;
    private BookingSessionBeanRemote bookingSessionBeanRemote;
    private Scanner sc;

    public FrontOfficeModule(GuestSessionBeanRemote guestSessionBeanRemote, BookingSessionBeanRemote bookingSessionBeanRemote) {
        this.guestSessionBeanRemote = guestSessionBeanRemote;
        this.bookingSessionBeanRemote = bookingSessionBeanRemote;
        this.sc = new Scanner(System.in);
    }

    public void runModule() {
        int response;
        
        while (true) {
            System.out.println("\n*** Front Office Module ***\n");
            System.out.println("1: Walk-in Search Room");
            System.out.println("2: Walk-in Reserve Room");
            System.out.println("3: Check-in Guest");
            System.out.println("4: Check-out Guest");
            System.out.println("5: Logout");
            System.out.print("> ");
            response = sc.nextInt();

            if (response == 1) {
                searchAvailableRooms();
            } else if (response == 2) {
                reserveRoom();
            } else if (response == 3) {
                checkInGuest();
            } else if (response == 4) {
                checkOutGuest();
            } else if (response == 5) {
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void searchAvailableRooms() {
        System.out.print("Enter check-in date (yyyy-MM-dd): ");
        Date checkInDate = new Date();  // Placeholder: use date parsing utility here.
        
        System.out.print("Enter check-out date (yyyy-MM-dd): ");
        Date checkOutDate = new Date();  // Placeholder: use date parsing utility here.
        
        try {
            List<RoomTypeEntity> availableRoomTypes = bookingSessionBeanRemote.searchAvailableRoomTypes(checkInDate, checkOutDate);
            
            System.out.println("Available Room Types:");
            for (RoomTypeEntity roomType : availableRoomTypes) {
                BigDecimal rate = bookingSessionBeanRemote.calculateRate(roomType.getRoomTypeId(), checkInDate, checkOutDate);
                System.out.println(roomType + " - Rate: " + rate);
            }
        } catch (Exception e) {
            System.out.println("Error searching for available rooms: " + e.getMessage());
        }
    }

    private void reserveRoom() {
        System.out.print("Enter number of rooms to reserve: ");
        int numRooms = sc.nextInt();
        
        System.out.print("Enter check-in date (yyyy-MM-dd): ");
        Date checkInDate = new Date();  // Placeholder: use date parsing utility here.
        
        System.out.print("Enter check-out date (yyyy-MM-dd): ");
        Date checkOutDate = new Date();  // Placeholder: use date parsing utility here.
        
        System.out.print("Enter room type ID: ");
        Long roomTypeId = sc.nextLong();

        try {
            BigDecimal totalAmount = bookingSessionBeanRemote.calculateRate(roomTypeId, checkInDate, checkOutDate).multiply(new BigDecimal(numRooms));
            ReservationEntity reservation = bookingSessionBeanRemote.createReservation(roomTypeId, checkInDate, checkOutDate, numRooms, totalAmount);
            
            System.out.println("Reservation successful! Reservation ID: " + reservation.getReservationId());
        } catch (Exception e) {
            System.out.println("Error reserving room: " + e.getMessage());
        }
    }

    private void checkInGuest() {
        System.out.print("Enter reservation ID for check-in: ");
        Long reservationId = sc.nextLong();
        
        try {
            bookingSessionBeanRemote.checkInGuest(reservationId);
            System.out.println("Guest checked in successfully.");
        } catch (Exception e) {
            System.out.println("Error checking in guest: " + e.getMessage());
        }
    }

    private void checkOutGuest() {
        System.out.print("Enter reservation ID for check-out: ");
        Long reservationId = sc.nextLong();
        
        try {
            bookingSessionBeanRemote.checkOutGuest(reservationId);
            System.out.println("Guest checked out successfully.");
        } catch (Exception e) {
            System.out.println("Error checking out guest: " + e.getMessage());
        }
    }
}
