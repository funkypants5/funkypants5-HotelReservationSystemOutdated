/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomTypeEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.RecordNotFoundException;

/**
 *
 * @author zchoo
 */
@Remote
public interface RoomTypeSessionBeanRemote {

    public Long createNewRoomType(RoomTypeEntity roomType);

    public RoomTypeEntity retrieveRoomType(String roomTypeName) throws RecordNotFoundException;

    public List<RoomTypeEntity> viewAllRoomTypes() throws RecordNotFoundException;

    public void updateRoomTypeName(Long roomTypeId, String newName);

    public void updateRoomTypeDescription(Long roomTypeId, String newDescription);

    public void updateRoomTypeBeds(Long roomTypeId, String newBed);

    public void updateRoomTypeCapacity(Long roomTypeId, String newCapacity);

    public void updateRoomTypeAmenities(Long roomTypeId, String newAmenities);

    public void updateRoomTypeSize(Long roomTypeId, String newSize);

    public RoomTypeEntity retrieveRoomTypeEntityById (Long roomTypeId) throws RecordNotFoundException ;

    

}