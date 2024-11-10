/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.RecordNotFoundException;

/**
 *
 * @author zchoo
 */
@Remote
public interface RoomSessionBeanRemote {
    public Long createNewRoom(Long roomTypeId, RoomEntity roomEntity) throws RecordNotFoundException;

    public RoomEntity retrieveRoom(String roomNumber) throws RecordNotFoundException;

    public void updateRoomNumber(Long roomId, String newRoomNumber);

    public void updateRoomType(Long roomId, Long newRoomTypeId);

    public void setNotAvilable(Long roomId);

    public void setAvailable(Long roomId);

    public List<RoomEntity> viewAllRoom() throws RecordNotFoundException;

    public Long deleteRoom(String roomNumber) throws RecordNotFoundException;
}
