package com.jspiders.cardekho_servlets.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.jspiders.cardekho_servlets.entity.Car;

public class CarDB {

	private static Driver driver;
	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;
	private static String query;

	private static void openConnection() throws SQLException {
		driver = new com.mysql.cj.jdbc.Driver();
		DriverManager.registerDriver(driver);
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/weja4", "root", "root");
	}

	private static void closeConnection() throws SQLException {
		if (resultSet != null) {
			resultSet.close();
		}
		if (preparedStatement != null) {
			preparedStatement.close();
		}
		if (connection != null) {
			connection.close();
		}
		DriverManager.deregisterDriver(driver);
	}

//	Add Car
	public static int addCar(int id, String name, String brand, double price) {

		int res = 0;
		try {
			openConnection();

			query = "INSERT INTO CARS1 VALUES(?,?,?,?)";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, brand);
			preparedStatement.setDouble(4, price);
			res = preparedStatement.executeUpdate();
			if(res == 1) {
				System.out.println("Car Add successfully!!!");
			}

			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;

	}

//	Find All Cars
	public static List<Car> searchCar() {
		List<Car> cars = new ArrayList<Car>();

		try {
			openConnection();
			
			query = "SELECT * FROM CARS1";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				Car car = new Car();
				car.setId(resultSet.getInt(1));
				car.setName(resultSet.getString(2));
				car.setBrand(resultSet.getString(3));
				car.setPrice(resultSet.getDouble(4));
				cars.add(car);
			}
			
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return cars;
		
	}

//	Edit Car
	public static int editCar(int id, String name, String brand, double price) {
		int res = 0;
		try {
			openConnection();

			query = "Update cars1 set name = ?, brand = ?,  price = ? where id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, brand);
			preparedStatement.setDouble(3, price);
			preparedStatement.setInt(4, id);
			res = preparedStatement.executeUpdate();
			if(res == 1) {
				System.out.println("Car Edited successfully!!!");
			}

			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

//	Delete Car
	public static int deleteCar(int id) {
		int res = 0;
		
		try {
			openConnection();
			
			query = "delete from cars1 where id = ?";
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, id);
			res = preparedStatement.executeUpdate();
			if(res == 1) {
				System.out.println("Car Deleted successfully!!!");
			}
			
			closeConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
		
	}


}
