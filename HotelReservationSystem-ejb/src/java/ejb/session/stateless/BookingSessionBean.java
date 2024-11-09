/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.ReservationEntity;
import entity.RoomTypeEntity;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author zchoo
 */
@Stateless
public class BookingSessionBean implements BookingSessionBeanRemote, BookingSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public List<RoomTypeEntity> searchAvailableRoomTypes(Date checkInDate, Date checkOutDate) {
        Query query = em.createQuery(
            "SELECT rt FROM RoomTypeEntity rt WHERE rt IN " +
            "(SELECT r.roomType FROM RoomEntity r WHERE r NOT IN " +
            "(SELECT res.room FROM ReservationEntity res " +
            "WHERE res.checkInDate < :checkOutDate AND res.checkOutDate > :checkInDate))"
        );
        query.setParameter("checkInDate", checkInDate);
        query.setParameter("checkOutDate", checkOutDate);
        
        return query.getResultList();
    }

    @Override
    public BigDecimal calculateRate(Long roomTypeId, Date checkInDate, Date checkOutDate) {
        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);
        if (roomType == null) {
            throw new IllegalArgumentException("Room type ID " + roomTypeId + " does not exist.");
        }
        
        long days = (checkOutDate.getTime() - checkInDate.getTime()) / (1000 * 60 * 60 * 24);
        BigDecimal dailyRate = roomType.getPublishedRate();
        
        return dailyRate.multiply(BigDecimal.valueOf(days));
    }

    @Override
    public ReservationEntity createReservation(Long roomTypeId, Date checkInDate, Date checkOutDate, int numRooms, BigDecimal totalAmount) throws InsufficientRoomException {
        RoomTypeEntity roomType = em.find(RoomTypeEntity.class, roomTypeId);
        
        // Check for sufficient room inventory
        long availableRooms = (long) em.createQuery(
            "SELECT COUNT(r) FROM RoomEntity r WHERE r.roomType.roomTypeId = :roomTypeId AND r NOT IN " +
            "(SELECT res.room FROM ReservationEntity res WHERE res.checkInDate < :checkOutDate AND res.checkOutDate > :checkInDate)"
        ).setParameter("roomTypeId", roomTypeId)
         .setParameter("checkInDate", checkInDate)
         .setParameter("checkOutDate", checkOutDate)
         .getSingleResult();

        if (availableRooms < numRooms) {
            throw new InsufficientRoomInventoryException("Not enough rooms available for the selected dates.");
        }
        
        ReservationEntity reservation = new ReservationEntity(checkInDate, checkOutDate, totalAmount);
        reservation.setRoomType(roomType);
        reservation.setNumRooms(numRooms);
        em.persist(reservation);
        
        return reservation;
    }

    @Override
    public void checkInGuest(Long reservationId) throws ReservationNotFoundException {
        ReservationEntity reservation = em.find(ReservationEntity.class, reservationId);
        if (reservation == null) {
            throw new ReservationNotFoundException("Reservation ID " + reservationId + " does not exist.");
        }
        
        reservation.setCheckedIn(true);
    }

    @Override
    public void checkOutGuest(Long reservationId) throws ReservationNotFoundException {
        ReservationEntity reservation = em.find(ReservationEntity.class, reservationId);
        if (reservation == null) {
            throw new ReservationNotFoundException("Reservation ID " + reservationId + " does not exist.");
        }
        
        reservation.setCheckedOut(true);
    }
}
