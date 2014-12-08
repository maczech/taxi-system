package com.mcz.management

import com.mcz.management.message.Location
import org.log4s._

class ManagementCenterAdaptor {
  
  private[this] val logger = getLogger
  
  def logPosition(location:Location) {
    logger.info("Location for taxi: "+location)
  }

}