
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatelessEjbClass.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomEntity;
import entity.RoomRateEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author admin
 */
@Stateless
public class RoomRateSessionBean implements RoomRateSessionBeanRemote, RoomRateSessionBeanLocal {

    @PersistenceContext(unitName = "HotelReservationSystem-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    @Override
    public Long createRoomRate(RoomRateEntity roomRate) {
        em.persist(roomRate);
        em.flush(); // Ensure the entity ID is generated
        return roomRate.getRoomRateId();
    }
    

    @Override
    public RoomRateEntity retrieveRoomRateById(Long roomRateId) {
        return em.find(RoomRateEntity.class, roomRateId);
    }

    @Override
    public List<RoomRateEntity> retrieveAllRoomRates() {
        return em.createQuery("SELECT r FROM RoomRate r", RoomRateEntity.class).getResultList();
    }

    @Override
    public void updateRoomRate(RoomRateEntity roomRate) {
        em.merge(roomRate);
    }

    @Override
    public void deleteRoomRate(Long roomRateId) {
        RoomRateEntity roomRate = em.find(RoomRateEntity.class, roomRateId);
        if (roomRate != null) {
            em.remove(roomRate);
        }
    }
    
    public RoomEntity retrieveRoomDetails(Long roomId) {
     return em.find(RoomEntity.class, roomId);
    }
}
