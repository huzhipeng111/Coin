package com.binance.client.impl.utils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


public class EnumLookup<T extends Enum<T>> {


  private final Map<String, T> map = new HashMap<>();
  private final String enumName;

  public EnumLookup(Class<T> clazz) {
    enumName = clazz.getName();
    for (T item : EnumSet.allOf(clazz)) {
      map.put(item.toString(), item);
    }
  }

  public T lookup(String name) {
    if (!map.containsKey(name)) {
      return null;
    }
    return map.get(name);
  }
}
