/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystemclient;

import ejb.session.stateless.RoomRateSessionBeanRemote;
import ejb.session.stateless.RoomSessionBeanRemote;
import entity.RoomEntity;
import entity.RoomRateEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import util.enumeration.RateType;
import util.exception.InvalidInputException;

/**
 *
 * @author admin
 */
public class SalesManagerModule {

    private RoomRateSessionBeanRemote roomRateSessionBeanRemote;
    private RoomSessionBeanRemote roomSessionBeanRemote;

    public SalesManagerModule(RoomRateSessionBeanRemote roomRateSessionBeanRemote, RoomSessionBeanRemote roomSessionBeanRemote) {
        this.roomRateSessionBeanRemote = roomRateSessionBeanRemote;
        this.roomSessionBeanRemote = roomSessionBeanRemote;
    }

    public void runModule() {
        Scanner sc = new Scanner(System.in);
        int response;
        
        while (true) {
            System.out.println("\n*** Welcome to the Sales Manager Module ***\n");
            System.out.println("1: Create New Room Rate");
            System.out.println("2: View Room Details");
            System.out.println("3: Update Room Rate");
            System.out.println("4: Delete Room Rate");
            System.out.println("5: View All Room Rates");
            System.out.println("6: Logout");
            System.out.print("> ");
            response = sc.nextInt();

            if (response == 1) {
                createNewRoomRate();
            } else if (response == 2) {
                viewRoomDetails();
            } else if (response == 3) {
                updateRoomRate();
            } else if (response == 4) {
                deleteRoomRate();
            } else if (response == 5) {
                viewAllRoomRates();
            } else if (response == 6) {
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void createNewRoomRate() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter room type ID: ");
            Long roomTypeId = sc.nextLong();

            System.out.print("Enter rate amount: ");
            BigDecimal rateAmount = sc.nextBigDecimal();

            System.out.println("Select rate type: ");
            System.out.println("1: STANDARD");
            System.out.println("2: PEAK");
            System.out.println("3: PROMOTION");
            System.out.println("4: WALKIN");
            System.out.print("> ");
            int rateTypeOption = sc.nextInt();
            RateType rateType = mapRateType(rateTypeOption);

            System.out.print("Enter start date (yyyy-MM-dd): ");
            Date startDate = new Date();  // Placeholder; would use date parsing utility.

            System.out.print("Enter end date (yyyy-MM-dd): ");
            Date endDate = new Date();  // Placeholder; would use date parsing utility.

            RoomRateEntity newRoomRate = new RoomRateEntity(rateAmount, rateType, startDate, endDate);
            newRoomRate.setRoomTypeId(roomTypeId); // Assuming RoomRateEntity has a reference to RoomType
            System.out.println("New room rate created successfully!");

        } catch (Exception e) {
            System.out.println("Error creating room rate: " + e.getMessage());
        }
    }

    private void viewRoomDetails() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter room ID: ");
        Long roomId = sc.nextLong();
        
        try {
            RoomEntity room = roomSessionBeanRemote.retrieveRoomById(roomId);
            System.out.println("Room Details: " + room);
        } catch (Exception e) {
            System.out.println("Error retrieving room details: " + e.getMessage());
        }
    }

    private void updateRoomRate() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter room rate ID to update: ");
        Long roomRateId = sc.nextLong();

        try {
            RoomRateEntity roomRate = roomRateSessionBeanRemote.retrieveRoomRateById(roomRateId);
            if (roomRate == null) {
                System.out.println("Room rate not found.");
                return;
            }

            System.out.print("Enter new rate amount: ");
            BigDecimal rateAmount = sc.nextBigDecimal();
            roomRate.setRate(rateAmount);

            System.out.println("Select new rate type: ");
            System.out.println("1: STANDARD");
            System.out.println("2: PEAK");
            System.out.println("3: PROMOTION");
            System.out.println("4: WALKIN");
            System.out.print("> ");
            int rateTypeOption = sc.nextInt();
            RateType rateType = mapRateType(rateTypeOption);
            roomRate.setRateType(rateType);

            roomRateSessionBeanRemote.updateRoomRate(roomRate);
            System.out.println("Room rate updated successfully!");

        } catch (Exception e) {
            System.out.println("Error updating room rate: " + e.getMessage());
        }
    }

    private void deleteRoomRate() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter room rate ID to delete: ");
        Long roomRateId = sc.nextLong();

        try {
            roomRateSessionBeanRemote.deleteRoomRate(roomRateId);
            System.out.println("Room rate deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error deleting room rate: " + e.getMessage());
        }
    }

    private void viewAllRoomRates() {
        List<RoomRateEntity> roomRates = roomRateSessionBeanRemote.retrieveAllRoomRates();
        System.out.println("\nList of All Room Rates:\n");
        int count = 0;
        for (RoomRateEntity roomRate : roomRates) {
            count++;
            System.out.println(count + ". " + roomRate);
        }
    }

    private RateType mapRateType(int rateTypeOption) {
        switch (rateTypeOption) {
            case 1: return RateType.STANDARD;
            case 2: return RateType.PEAK;
            case 3: return RateType.PROMOTION;
            case 4: return RateType.WALKIN;
            default: throw new IllegalArgumentException("Invalid rate type option.");
        }
    }
}
