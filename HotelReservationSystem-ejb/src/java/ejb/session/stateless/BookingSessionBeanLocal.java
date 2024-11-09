/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.ReservationEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import util.exception.InsufficientRoomException;
import util.exception.ReservationNotFoundException;

/**
 * Local Interface for BookingSessionBean
 */
@Local
public interface BookingSessionBeanLocal {

    List<RoomTypeEntity> searchAvailableRoomTypes(Date checkInDate, Date checkOutDate);
    
    BigDecimal calculateRate(Long roomTypeId, Date checkInDate, Date checkOutDate);
    
    ReservationEntity createReservation(Long roomTypeId, Date checkInDate, Date checkOutDate, int numRooms, BigDecimal totalAmount) throws InsufficientRoomException;
    
    void checkInGuest(Long reservationId) throws ReservationNotFoundException;
    
    void checkOutGuest(Long reservationId) throws ReservationNotFoundException;
}
