/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomEntity;
import entity.RoomTypeEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.exception.RecordNotFoundException;

/**
 *
 * @author zchoo
 */
@Stateless
public class RoomTypeSessionBean implements RoomTypeSessionBeanRemote, RoomTypeSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewRoomType(RoomTypeEntity roomType) {
        em.persist(roomType);
        em.flush();
        return roomType.getRoomTypeId();
    }

    @Override
    public RoomTypeEntity retrieveRoomType(String roomTypeName) throws RecordNotFoundException {
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.roomTypeName = :roomTypeName");
        query.setParameter("roomTypeName", roomTypeName);
        if (query == null) {
            throw new RecordNotFoundException("Room type not found! \n");
        }
        return (RoomTypeEntity) query.getSingleResult();
    }

    private RoomTypeEntity retrieveManagedRoomType(Long roomTypeId) {
        return em.find(RoomTypeEntity.class, roomTypeId);
    }

    @Override
    public void updateRoomTypeName(Long roomTypeId, String newName) {
        RoomTypeEntity rt = retrieveManagedRoomType(roomTypeId);
        rt.setRoomTypeName(newName);
    }

    @Override
    public void updateRoomTypeDescription(Long roomTypeId, String newDescription) {
        RoomTypeEntity rt = retrieveManagedRoomType(roomTypeId);
        rt.setDescription(newDescription);
    }

    @Override
    public void updateRoomTypeBeds(Long roomTypeId, String newBed) {
        RoomTypeEntity rt = retrieveManagedRoomType(roomTypeId);
        rt.setBed(newBed);
    }

    @Override
    public void updateRoomTypeCapacity(Long roomTypeId, String newCapacity) {
        RoomTypeEntity rt = retrieveManagedRoomType(roomTypeId);
        rt.setCapacity(newCapacity);
    }

    @Override
    public void updateRoomTypeAmenities(Long roomTypeId, String newAmenities) {
        RoomTypeEntity rt = retrieveManagedRoomType(roomTypeId);
        rt.setAmenities(newAmenities);
    }

    @Override
    public void updateRoomTypeSize(Long roomTypeId, String newSize) {
        RoomTypeEntity rt = retrieveManagedRoomType(roomTypeId);
        rt.setRoomSize(newSize);
    }

    @Override
    public List<RoomTypeEntity> viewAllRoomTypes() throws RecordNotFoundException {
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt");
        List<RoomTypeEntity> roomTypes = query.getResultList();
        if (roomTypes == null) {
            throw new RecordNotFoundException("No room types found \n");
        }

        return roomTypes;
    }

    @Override
    public RoomTypeEntity retrieveRoomTypeEntityById(Long roomTypeId) throws RecordNotFoundException {
        RoomTypeEntity rt = em.find(RoomTypeEntity.class, roomTypeId);
        if (rt == null) {
            throw new RecordNotFoundException("Room type does not exist");
        }

        return rt;

    }

    @Override
    public String deleteRoomType(Long roomTypeId, Long newRoomTypeId) throws RecordNotFoundException {
        RoomTypeEntity oldRoomtype = em.find(RoomTypeEntity.class, roomTypeId);
        if (oldRoomtype == null) {
            throw new RecordNotFoundException("Old room type does not exist");
        }
        RoomTypeEntity newRoomType = em.find(RoomTypeEntity.class, newRoomTypeId);
        if (newRoomType == null) {
            throw new RecordNotFoundException("New room type does not exist");
        }
        Query query = em.createQuery("SELECT r from RoomEntity r WHERE r.roomType.roomTypeId = :roomTypeId");
        query.setParameter("roomTypeId", roomTypeId);
        List<RoomEntity> rooms = query.getResultList();
        String oldRoomTypeName = oldRoomtype.getRoomTypeName();

        for (RoomEntity room : rooms) {
            room.setRoomType(newRoomType);
        }
        em.remove(oldRoomtype);
        return oldRoomTypeName;
    }

}
