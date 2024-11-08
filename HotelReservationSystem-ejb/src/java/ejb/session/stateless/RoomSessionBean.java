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
public class RoomSessionBean implements RoomSessionBeanRemote, RoomSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Long createNewRoom(Long roomTypeId, RoomEntity roomEntity) throws RecordNotFoundException {
        RoomTypeEntity rt = em.find(RoomTypeEntity.class, roomTypeId);
        if (rt == null) {
            throw new RecordNotFoundException("Room type Id is invalid, no room type found");
        }
        roomEntity.setRoomType(rt);
        em.persist(roomEntity);
        return roomEntity.getRoomId();

    }

    @Override
    public RoomEntity retrieveRoom(String roomNumber) throws RecordNotFoundException {
        Query query = em.createQuery("SELECT r FROM RoomEntity r WHERE r.roomNumber = :roomNumber");
        query.setParameter("roomNumber", roomNumber);

        if (query == null) {
            throw new RecordNotFoundException("Room not found");
        }

        return (RoomEntity) query.getSingleResult();
    }

    @Override
    public void updateRoomNumber(Long roomId, String newRoomNumber) {
        RoomEntity room = em.find(RoomEntity.class, roomId);
        room.setRoomNumber(newRoomNumber);
    }

    @Override
    public void updateRoomType(Long roomId, Long newRoomTypeId) {
        RoomEntity room = em.find(RoomEntity.class, roomId);
        RoomTypeEntity rt = em.find(RoomTypeEntity.class, newRoomTypeId);
        room.setRoomType(rt);
    }

    @Override
    public void setNotAvilable(Long roomId) {
        RoomEntity room = em.find(RoomEntity.class, roomId);
        room.setRoomStatusNotAvailable();
    }

    @Override
    public void setAvailable(Long roomId) {
        RoomEntity room = em.find(RoomEntity.class, roomId);
        room.setRoomStatusAvailable();
    }

    @Override
    public List<RoomEntity> viewAllRoom() throws RecordNotFoundException {
       Query query = em.createQuery("SELECT r FROM RoomEntity r");
       if (query == null) {
           throw new RecordNotFoundException("No rooms found ");
       }
       return query.getResultList();
    }


}
