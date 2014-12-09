package com.mcz.gps.message

import java.util.Date

case class Coordinate(latitude:Float,longitude:Float, timestamp:Date) {
  
  override def toString() = {
    s"lat:${this.latitude}, lon:${this.longitude}, date:${this.timestamp}"
  }
}