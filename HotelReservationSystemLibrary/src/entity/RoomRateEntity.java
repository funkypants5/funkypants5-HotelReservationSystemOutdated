/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import util.enumeration.RateType;

/**
 *
 * @author admin
 */
@Entity
public class RoomRateEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomRateId;
    
    @Column(nullable = false)
    private BigDecimal rate;
    
    @Enumerated(EnumType.STRING)
    private RateType rateType; // Enum: STANDARD, PEAK, etc.
    
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    
    @Temporal(TemporalType.DATE)
    private Date validTo;
    
    // Many-to-One relationship with RoomType
    @ManyToOne(optional = false)
    private RoomTypeEntity roomType;

    public RoomRateEntity(BigDecimal rateAmount, RateType rateType, Date startDate, Date endDate) {
        this.rate = rate;
        this.rateType = rateType;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public Long getRoomRateId() {
        return roomRateId;
    }

    public void setRoomRateId(Long roomRateId) {
        this.roomRateId = roomRateId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomRateId != null ? roomRateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomRateId fields are not set
        if (!(object instanceof RoomRateEntity)) {
            return false;
        }
        RoomRateEntity other = (RoomRateEntity) object;
        if ((this.roomRateId == null && other.roomRateId != null) || (this.roomRateId != null && !this.roomRateId.equals(other.roomRateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoomRate[ id=" + roomRateId + " ]";
    }

    public void setRoomTypeId(Long roomTypeId) {
         this.roomRateId = roomRateId;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
    
    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }
    
}