package com.speed.parkinglot

import com.speed.parkinglot.Domain.{ParkingException, ParkingLot, TwoWheeler, VehicleType}
import com.speed.parkinglot.Domain.VehicleType.VehicleType
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.immutable.HashMap
import scala.collection.mutable

class DomainTest extends AnyFlatSpec {
  val phoenixCapacity: HashMap[VehicleType, Int] = HashMap((VehicleType.twoWheeler, 100), (VehicleType.hatchBack, 100), (VehicleType.suv, 80))
  val phoenixRateCard: HashMap[VehicleType, Int] = HashMap((VehicleType.twoWheeler, 20), (VehicleType.hatchBack, 50), (VehicleType.suv, 100))
  //  val emptySlots: HashMap[VehicleType, Int] = HashMap((VehicleType.twoWheeler, phoenixCapacity.get(VehicleType.twoWheeler)),(VehicleType.hatchBack, phoenixCapacity.get(VehicleType.hatchBack)), (VehicleType.suv, phoenixCapacity.get(VehicleType.suv)))
  //  val phoenix = ParkingLot("Phoenix Mall", phoenixCapacity, phoenixRateCard, emptySlots)

  "Parking lot check " should "fail if no slot available for that vehicle" in {
    val emptySlots: mutable.HashMap[VehicleType, Int] = mutable.HashMap((VehicleType.twoWheeler, 0), (VehicleType.hatchBack, 100), (VehicleType.suv, 100))
    val phoenix = ParkingLot("Phoenix Mall", phoenixCapacity, phoenixRateCard, emptySlots)

    val activa = TwoWheeler("KA05-DO-1234")
    assertThrows[ParkingException] {
      Domain.park(activa, phoenix)
    }
  }

  "Parking lot check " should "park the vehicle successfully if slot available for that vehicle" in {
    val emptySlots: mutable.HashMap[VehicleType, Int] = mutable.HashMap((VehicleType.twoWheeler, 10), (VehicleType.hatchBack, 100), (VehicleType.suv, 100))
    val phoenix = ParkingLot("Phoenix Mall", phoenixCapacity, phoenixRateCard, emptySlots)

    val activa = TwoWheeler("KA05-DO-1234")
    assert(Domain.park(activa, phoenix))
    assert(phoenix.emptySlots.get(VehicleType.twoWheeler).contains(9))
  }

  "charge of vehicle " should " be acording to hours spent in parking lot" in {
    val emptySlots: mutable.HashMap[VehicleType, Int] = mutable.HashMap((VehicleType.twoWheeler, 10), (VehicleType.hatchBack, 100), (VehicleType.suv, 100))
    val phoenix = ParkingLot("Phoenix Mall", phoenixCapacity, phoenixRateCard, emptySlots)

    val activa = TwoWheeler("KA05-DO-1234")
    Domain.park(activa, phoenix)
    assert(Domain.exit(activa, 3, phoenix) == 60)
    assert(phoenix.emptySlots.get(VehicleType.twoWheeler).contains(10))
  }

  "charge of vehicle " should " be too high 5000 Rs for parking long hourse (>4) " in {
    val emptySlots: mutable.HashMap[VehicleType, Int] = mutable.HashMap((VehicleType.twoWheeler, 10), (VehicleType.hatchBack, 100), (VehicleType.suv, 100))
    val phoenix = ParkingLot("Phoenix Mall", phoenixCapacity, phoenixRateCard, emptySlots)

    val activa = TwoWheeler("KA05-DO-1234")
    Domain.park(activa, phoenix)
    assert(Domain.exit(activa, 5, phoenix) == 5000)
    assert(phoenix.emptySlots.get(VehicleType.twoWheeler).contains(10))
  }

  " Status of parking lot " should " print status of given parking lot" in {
    val emptySlots: mutable.HashMap[VehicleType, Int] = mutable.HashMap((VehicleType.twoWheeler, 10), (VehicleType.hatchBack, 100), (VehicleType.suv, 100))
    val phoenix = ParkingLot("Phoenix Mall", phoenixCapacity, phoenixRateCard, emptySlots)
    Domain.status(phoenix)

    val activa = TwoWheeler("KA05-DO-1234")
    Domain.park(activa, phoenix)
    println(s" We parked a vehicle $activa , Now stats is:")
    Domain.status(phoenix)
  }

  " Status of parking lot " should " print parking history for vehicle" in {
    val emptySlots: mutable.HashMap[VehicleType, Int] = mutable.HashMap((VehicleType.twoWheeler, 10), (VehicleType.hatchBack, 100), (VehicleType.suv, 100))
    val phoenix = ParkingLot("Phoenix Mall", phoenixCapacity, phoenixRateCard, emptySlots)
    Domain.status(phoenix)

    val activa = TwoWheeler("KA05-DO-1234")
    Domain.park(activa, phoenix)
    Domain.exit(activa, 3, phoenix)

    Domain.park(activa, phoenix)
    Domain.exit(activa, 3, phoenix)

    Domain.park(activa, phoenix)
    Domain.exit(activa, 3, phoenix)
    println(s" We parked a vehicle $activa , Now stats is:")
    println(phoenix.parkingHistory.get(activa.regNo))
  }
}
