package com.mcz.management.message

import java.util.Date

case class Location(taxiId:Int,latitude:Float,longitude:Float, timestamp:Date) {
  override def toString() = {
    s"taxi id:${this.taxiId},[lat:${this.latitude}, lon:${this.longitude}], date:${this.timestamp}"
  }
}