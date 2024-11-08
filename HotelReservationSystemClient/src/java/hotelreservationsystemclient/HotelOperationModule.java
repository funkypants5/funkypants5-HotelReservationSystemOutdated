/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystemclient;

import ejb.session.stateless.RoomSessionBeanRemote;
import ejb.session.stateless.RoomTypeSessionBeanRemote;
import entity.EmployeeEntity;
import entity.RoomTypeEntity;
import java.util.List;
import java.util.Scanner;
import util.enumeration.AccessRights;
import util.exception.RecordNotFoundException;
import ejb.session.stateless.RoomRateSessionBeanRemote;
import entity.RoomEntity;

/**
 *
 * @author zchoo
 */
public class HotelOperationModule {

    private RoomSessionBeanRemote roomSessionBeanRemote;
    private RoomRateSessionBeanRemote ratesSessionBeanRemote;
    private RoomTypeSessionBeanRemote roomTypeSessionBeanRemote;
    private EmployeeEntity employee;

    public HotelOperationModule(RoomSessionBeanRemote roomSessionBeanRemote, RoomRateSessionBeanRemote ratesSessionBeanRemote, RoomTypeSessionBeanRemote roomTypeSessionBeanRemote, EmployeeEntity employee) {
        this.roomSessionBeanRemote = roomSessionBeanRemote;
        this.ratesSessionBeanRemote = ratesSessionBeanRemote;
        this.roomTypeSessionBeanRemote = roomTypeSessionBeanRemote;
        this.employee = employee;
    }

    void runModule() {
        Scanner sc = new Scanner(System.in);
        int response = 0;
        System.out.println("\n ***Welcome " + employee.getFirstName() + " to Hotel Operations Module*** \n");
        if (employee.getAccessRightsEnum() == AccessRights.OPERATIONMANAGER) {
            runOperationManagerMenu();
        }
        if (employee.getAccessRightsEnum() == AccessRights.SALESMANAGER) {
            runSalesManagerMenu();
        }
    }

    private void runOperationManagerMenu() {
        Scanner sc = new Scanner(System.in);
        int response;

        while (true) {
            System.out.println("\n*** Welcome to the Sales Manager Module ***\n");
            System.out.println("1: Create New Room Type");
            System.out.println("2: View Room Type Details");
            System.out.println("3: Update Room Type");
            System.out.println("4: Delete Room Type");
            System.out.println("5: View All Room Types");
            System.out.println("6: Create New Room");
            System.out.println("7: Update Room");
            System.out.println("8: Delete Room");
            System.out.println("9: View All Rooms");
            System.out.println("10: View Room Allocation Exception Report");
            System.out.println("11: Logout");
            System.out.print("> ");
            response = sc.nextInt();

            if (response == 1) {
                createNewRoomType();
            } else if (response == 2) {;
                System.out.print("Please enter room type name: ");
                String roomTypeName = sc.nextLine();
                viewRoomTypeDetails(roomTypeName);
            } else if (response == 3) {
                updateRoomType();
            } else if (response == 4) {
                deleteRoomType();
            } else if (response == 5) {
                viewAllRoomTypes();
            } else if (response == 6) {
                createNewRoom();
            } else if (response == 7) {
                updateRoom();
            } else if (response == 8) {
                deleteRoom();
            } else if (response == 9) {
                viewAllRooms();
            } else if (response == 10) {
                viewRoomAllocationExceptionReport();
            } else if (response == 11) {
                break;
            }
            {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    private void createNewRoomType() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter room type name: ");
        String roomName = sc.nextLine();
        System.out.print("Please enter room type description (max 255 characters): ");
        String description = sc.nextLine();
        System.out.print("Please enter room type size: ");
        String roomSize = sc.nextLine();
        System.out.print("Please enter number of beds for each bed type (eg. 1Queen, 2King): ");
        String bed = sc.nextLine();
        System.out.print("Please enter capacity of room type in square meters (please exclude typing in the units): ");
        String capacity = sc.nextLine();
        System.out.print("Please enter ammenities of the room type: ");
        String ammenities = sc.nextLine();
        Long roomId = roomTypeSessionBeanRemote.createNewRoomType(new RoomTypeEntity(roomName, description, roomSize, bed, capacity, ammenities));
        System.out.println("New room type created with roomType Id: " + roomId + "\n");
    }

    private Long viewRoomTypeDetails(String roomTypeName) {

        try {
            RoomTypeEntity roomTypeEntity = roomTypeSessionBeanRemote.retrieveRoomType(roomTypeName);
            System.out.println("Room type details as follows: \n" + roomTypeEntity);
            return roomTypeEntity.getRoomTypeId();
        } catch (RecordNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        return new Long(0); //dummy value
    }

    private void updateRoomType() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter name of room type you wish to update: ");
        String roomTypeName = sc.nextLine();
        Long roomTypeId = viewRoomTypeDetails(roomTypeName);
        System.out.print("Please select the room type detail you wish to update: ");
        System.out.println("1. Name");
        System.out.println("2. Description");
        System.out.println("3. Size");
        System.out.println("4. Beds");
        System.out.println("5. Capacity");
        System.out.println("6. Amenities");
        System.out.print("Enter your choice (1-6): ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        // Update the selected field based on user choice
        switch (choice) {
            case 1:
                System.out.print("Enter new room name: ");
                String newName = sc.nextLine();
                roomTypeSessionBeanRemote.updateRoomTypeName(roomTypeId, newName);
                break;
            case 2:
                System.out.print("Enter new description: ");
                String newDescription = sc.nextLine();
                roomTypeSessionBeanRemote.updateRoomTypeDescription(roomTypeId, newDescription);
                break;
            case 3:
                System.out.print("Enter new room size(Square Meters)s: ");
                String newSize = sc.nextLine();
                roomTypeSessionBeanRemote.updateRoomTypeSize(roomTypeId, newSize);
                break;
            case 4:
                System.out.print("Enter new number of beds for each bed type (eg. 1Queen, 2King): ");
                String newBed = sc.nextLine();
                roomTypeSessionBeanRemote.updateRoomTypeBeds(roomTypeId, newBed);
                break;
            case 5:
                System.out.print("Enter new capacity: ");
                String newCapacity = sc.nextLine();
                roomTypeSessionBeanRemote.updateRoomTypeCapacity(roomTypeId, newCapacity);
                break;
            case 6:
                System.out.print("Enter new amenities: ");
                String newAmenities = sc.nextLine();
                roomTypeSessionBeanRemote.updateRoomTypeAmenities(roomTypeId, newAmenities);
                break;
            default:
                System.out.println("Invalid selection. Please choose a valid room type detail.");
                break;
        }

        System.out.println("Room type with id " + roomTypeId + " updated successfully.");
    }

    private void deleteRoomType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewAllRoomTypes() {
        try {
            List<RoomTypeEntity> roomTypes = roomTypeSessionBeanRemote.viewAllRoomTypes();
            int count = 0;
            for (RoomTypeEntity roomType : roomTypes) {
                System.out.println(count + " " + roomType);
            }
        } catch (RecordNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void createNewRoom() {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.print("Please enter the room number (e.g., 2015 for the fifteenth room on floor twenty): ");
            String roomNumber = sc.nextLine().trim();

            System.out.println("Please enter room type id from this list");
            viewAllRoomTypes();
            System.out.print("> ");
            Long roomTypeId = sc.nextLong();

            RoomEntity newRoom = new RoomEntity(roomNumber);

            // Call the method from the session bean to persist the new room
            Long roomId = roomSessionBeanRemote.createNewRoom(roomTypeId, newRoom);

            System.out.println("\nNew room created successfully!\n");

        } catch (RecordNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateRoom() {
        Scanner sc = new Scanner(System.in);
        viewAllRooms();
        System.out.print("Please enter the room number you wish to update: ");
        String roomNumber = sc.nextLine().trim();
        Long roomId = new Long(0); //place holder
        try {
            RoomEntity room = roomSessionBeanRemote.retrieveRoom(roomNumber);
            roomId = room.getRoomId();
        } catch (RecordNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        System.out.print("Please select the room detail you wish to update: ");
        System.out.println("1. Room Number");
        System.out.println("2. Room Type");
        System.out.println("3. Room Status");
        System.out.print("Enter your choice (1-3): ");
        int choice = sc.nextInt();
        sc.nextLine(); // Consume newline

        // Update the selected field based on user choice
        switch (choice) {
            case 1:
                System.out.print("Enter new room number: ");
                String newRoomNumber = sc.nextLine().trim();
                roomSessionBeanRemote.updateRoomNumber(roomId, newRoomNumber);
                break;
            case 2:
                System.out.print("Enter new room type ID: ");
                Long newRoomTypeId = sc.nextLong();
                roomSessionBeanRemote.updateRoomType(roomId, newRoomTypeId);
                break;
            case 3:
                while (true) {
                    System.out.println("Enter new room status: ");
                    System.out.println("1: Available");
                    System.out.println("2: Not Available");
                    System.out.print("> ");
                    int response = sc.nextInt();
                    if (response == 1) {
                        roomSessionBeanRemote.setAvailable(roomId);
                        break;
                    } else if (response == 2) {
                        roomSessionBeanRemote.setNotAvilable(roomId);
                        break;
                    } else {
                        System.out.println("Invalide option");
                    }

                }
                break;
            default:
                System.out.println("Invalid selection. Please choose a valid room detail.");
                break;
        }

        System.out.println("Room with ID " + roomId + " updated successfully.");
    }

    private void deleteRoom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void viewAllRooms() {
        int count = 0;
        try{
        List<RoomEntity> rooms = roomSessionBeanRemote.viewAllRoom();
        System.out.println("List of all rooms: ");
        for(RoomEntity room : rooms) {
            count++;
            System.out.println(count + " " + room);
        } 
        } catch(RecordNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void viewRoomAllocationExceptionReport() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void runSalesManagerMenu() {
        Scanner sc = new Scanner(System.in);
        int response;
        while (true) {

            System.out.println("1: Create New Room Type");
            System.out.println("2: View Room Rate Details");
            System.out.println("3: Update Room Rate");
            System.out.println("4: Delete Room Rate");
            System.out.println("5: View All Room Rates");
            System.out.println("6: Logout");
            System.out.print("> ");
            response = sc.nextInt();

            /*if (response == 1) {
                createNewRoomType();
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
            }*/
        }
    }

    /*  private void createNewRoomType() {
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
            case 1:
                return RateType.STANDARD;
            case 2:
                return RateType.PEAK;
            case 3:
                return RateType.PROMOTION;
            case 4:
                return RateType.WALKIN;
            default:
                throw new IllegalArgumentException("Invalid rate type option.");
        }
    }*/
}
