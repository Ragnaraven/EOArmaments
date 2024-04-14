package io.github.ragnaraven.eoarmaments.core.util;

import net.minecraft.util.RandomSource;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class RandomCollection<E>
{	
	private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
    private double total = 0;

    public void add(double weight, E result) 
    {
        if (weight <= 0) return;
        total += weight;
        map.put(total, result);
    }

    public E next(RandomSource random)
    {
        double value = random.nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }
}