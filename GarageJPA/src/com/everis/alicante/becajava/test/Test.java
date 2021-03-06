package com.everis.alicante.becajava.test;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

import com.everis.alicante.becajava.domain.Booking;
import com.everis.alicante.becajava.domain.Client;
import com.everis.alicante.becajava.domain.Parkingplace;
import com.everis.alicante.becajava.domain.Vehicle;
import com.everis.alicante.becajava.implementaciones.BookingDAOJPAImpl;
import com.everis.alicante.becajava.implementaciones.ClientDAOJPAImpl;
import com.everis.alicante.becajava.implementaciones.ParkingPlacecDAOJPAImpl;
import com.everis.alicante.becajava.implementaciones.VehicleDAOJPAImpl;
import com.everis.alicante.becajava.interfaces.BookingDAO;
import com.everis.alicante.becajava.interfaces.ClientDAO;
import com.everis.alicante.becajava.interfaces.ParkingPlaceDAO;
import com.everis.alicante.becajava.interfaces.VehicleDAO;
import com.everis.alicante.becajava.services.implementaciones.BookingServiceImpl;



public class Test {
	
	static Logger logger= Logger.getLogger(Test.class);
	
	public static void main(String[] args){
		
	
	
		logger.info(" INIT TEST ************************************************** ");
		
		
		EntityManager em=createEntityMananger();
				
		ClientDAO daoClient=new ClientDAOJPAImpl(em);
		VehicleDAO daoVehicle= new VehicleDAOJPAImpl(em);
		BookingDAO daoBooking=new BookingDAOJPAImpl(em);
		ParkingPlaceDAO daoParking= new ParkingPlacecDAOJPAImpl(em);
		
		BookingServiceImpl service=new BookingServiceImpl(daoBooking);
		service.setBookingDao(daoBooking);
			

		//creamos una reserva entera
		
		Client client= new Client();
		
		client.setName("Paquito");		
		client.setSurname("Chuletas");
		client.setNif("45645645Y");
		client.setTelephone("610616980");
		
		Set<Vehicle> vehicles= new HashSet<Vehicle>();
		
		
		Vehicle vehicle= new Vehicle();
		vehicle.setVehicleModel("moto");
		vehicle.setVehiclePlate("4455-ghx");
		vehicle.setClient(client);
		
		vehicles.add(vehicle);	
		
		client.setVehicles(vehicles);;
		
//		daoClient.create(client);
		
		// crear reserva con todo incluido
		
			Parkingplace place= daoParking.readById(3);
//			place.setParkingState((byte) 1);
			
			Booking booking= new Booking();
			booking.setBookingdate(Calendar.getInstance().getTime());
			booking.setParkingplace(place);
			
			Set<Booking> bookings = new HashSet<>();
			bookings.add(booking);
		
			vehicle.setBookings(bookings);
			client.setBookings(bookings);
			place.setBookings(bookings);
			
			booking.setVehicle(vehicle);
			booking.setClient(client);
			booking.setParkingplace(place);	
		
		
			service.create(booking);
			
		
		logger.info(" END TEST *************************************************** ");
		
		
	}
	
		static EntityManager createEntityMananger(){
		
		return Persistence.createEntityManagerFactory("GARAGE_JPA").createEntityManager();
		
	}

}
