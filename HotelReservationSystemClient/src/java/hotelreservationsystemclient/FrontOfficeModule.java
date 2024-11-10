/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotelreservationsystemclient;

import ejb.session.stateless.GuestSessionBeanRemote;
import ejb.session.stateless.ReservationSessionBeanRemote;

/**
 *
 * @author zchoo
 */
public class FrontOfficeModule {

    private GuestSessionBeanRemote guestSessionBeanRemote;
    private ReservationSessionBeanRemote reservationSessionBeanRemote;

    public FrontOfficeModule(GuestSessionBeanRemote guestSessionBeanRemote, ReservationSessionBeanRemote reservationSessionBeanRemote) {
        this.guestSessionBeanRemote = guestSessionBeanRemote;
        this.reservationSessionBeanRemote = reservationSessionBeanRemote;
    }

    void runModule() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
