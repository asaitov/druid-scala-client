package com.tapad.druid.client

import org.scalatest.{Matchers, FlatSpec}

class NotExample extends FlatSpec with Matchers {

    val filterDimension = QueryFilter.where(dimension = "toto", value = "tata")

    "The not filter " should "create a not filter" in {
      Not(filterDimension) should be(filterDimension.not)
    }
}
