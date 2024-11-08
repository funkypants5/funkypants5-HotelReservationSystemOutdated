/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package ejb.session.stateless;

import entity.RoomEntity;
import javax.ejb.Local;
import util.exception.RecordNotFoundException;

/**
 *
 * @author zchoo
 */
@Local
public interface RoomSessionBeanLocal {
    public Long createNewRoom(Long roomTypeId, RoomEntity roomEntity) throws RecordNotFoundException;
}
