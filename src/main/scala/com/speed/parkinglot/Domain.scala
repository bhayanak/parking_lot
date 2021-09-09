package com.speed.parkinglot

import com.speed.parkinglot.Domain.VehicleType.VehicleType

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.collection.immutable.HashMap
import scala.collection.mutable

object Domain extends App {

  @throws(classOf[ParkingException])
  def park(vehicle: Vehicle, parkingLot: ParkingLot): Boolean = {
    vehicle match {
      case TwoWheeler(regNo, twoWheeler) => if (parkingLot.emptySlots.get(twoWheeler).contains(0)) throw ParkingException(s"Parking is full or not available, vehicle $regNo can not be parked")
      case HatchBack(regNo, hatchBack) => if (parkingLot.emptySlots.get(hatchBack).contains(0)) throw ParkingException(s"Parking is full or not available, vehicle $regNo can not be parked")
      case SUV(regNo, suv) => if (parkingLot.emptySlots.get(suv).contains(0)) throw ParkingException(s"Parking is full or not available, vehicle $regNo can not be parked")
    }
    parkingLot.fillSlot(vehicle)
    // update history
    val timeNow = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(LocalDateTime.now)
    val msg = s"parked at $timeNow am"

    if (parkingLot.parkingHistory.contains(vehicle.regNo))
      parkingLot.parkingHistory(vehicle.regNo) = parkingLot.parkingHistory.getOrElseUpdate(vehicle.regNo, List(msg)) :+ msg
    else
      parkingLot.parkingHistory(vehicle.regNo) = parkingLot.parkingHistory.getOrElseUpdate(vehicle.regNo, List(msg))

    return true
  }


  def exit(vehicle: Vehicle, timeSpentInHrs: Int, parkingLot: ParkingLot): Int = {
    val charge = if (timeSpentInHrs > 4) 5000 else parkingLot.rateCard(vehicle.vehicleType) * timeSpentInHrs
    println(s"vehicle ${vehicle.regNo} charges are $charge Rs")

    // now empty that slot from parking lot
    parkingLot.emptySlot(vehicle)
    // update history
    parkingLot.parkingHistory(vehicle.regNo) = parkingLot.parkingHistory(vehicle.regNo) :+ s"Removed after $timeSpentInHrs hours"
    return charge
  }

  def status(parkingLot: ParkingLot): Unit = {
    println(s"Total capacity for parking lot is: ${parkingLot.capacity}")
    println(s"Total space available for parking lot is: ${parkingLot.emptySlots}")
  }


  trait Vehicle {
    def regNo: String

    def vehicleType: VehicleType
  }

  case class TwoWheeler(regNo: String, vehicleType: VehicleType = VehicleType.twoWheeler) extends Vehicle

  case class HatchBack(regNo: String, vehicleType: VehicleType = VehicleType.hatchBack) extends Vehicle

  case class SUV(regNo: String, vehicleType: VehicleType = VehicleType.suv) extends Vehicle

  case class ParkingLot(name: String, capacity: HashMap[VehicleType, Int], rateCard: HashMap[VehicleType, Int], emptySlots: mutable.HashMap[VehicleType, Int]) {
    var parkingHistory: mutable.HashMap[String, List[String]] = new mutable.HashMap[String, List[String]]()

    def fillSlot(vehicle: Vehicle): Unit = emptySlots(vehicle.vehicleType) = emptySlots(vehicle.vehicleType) - 1

    def emptySlot(vehicle: Vehicle): Unit = emptySlots(vehicle.vehicleType) = emptySlots(vehicle.vehicleType) + 1
  }

  case class ParkingException(msg: String) extends Exception

  object VehicleType extends Enumeration {
    type VehicleType = Value
    val twoWheeler = Value("TwoWheeler")
    val hatchBack = Value("HatchBack")
    val suv = Value("SUV")
  }

}
