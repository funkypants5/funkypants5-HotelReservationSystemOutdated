/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author zchoo
 */
@Entity
public class RoomTypeEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long RoomTypeId;

    public Long getRoomTypeId() {
        return RoomTypeId;
    }

    public void setRoomTypeId(Long RoomTypeId) {
        this.RoomTypeId = RoomTypeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (RoomTypeId != null ? RoomTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the RoomTypeId fields are not set
        if (!(object instanceof RoomTypeEntity)) {
            return false;
        }
        RoomTypeEntity other = (RoomTypeEntity) object;
        if ((this.RoomTypeId == null && other.RoomTypeId != null) || (this.RoomTypeId != null && !this.RoomTypeId.equals(other.RoomTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomTypeEntity[ id=" + RoomTypeId + " ]";
    }
    
}
