/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionRemote.java to edit this template
 */
package ejb.session.stateless;

import entity.PartnerEntity;
import java.util.List;
import javax.ejb.Remote;
import util.exception.InvalidInputException;

/**
 *
 * @author zchoo
 */
@Remote
public interface PartnerSessionBeanRemote {
    public void createNewPartner(PartnerEntity partner);

    public List<PartnerEntity> viewAllPartners();
}
