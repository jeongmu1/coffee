package com.dnlab.coffee.order.exception

class OutOfStockException(itemName: String) : IllegalArgumentException("${itemName}의 재고가 충분하지 않습니다.")