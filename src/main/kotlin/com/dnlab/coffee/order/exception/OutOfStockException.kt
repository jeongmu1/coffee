package com.dnlab.coffee.order.exception

class OutOfStockException(itemName: String) : RuntimeException("$itemName is out of stock.")