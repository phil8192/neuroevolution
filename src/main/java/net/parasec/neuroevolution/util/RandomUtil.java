package net.parasec.neuroevolution.util;

import java.util.Random;


public final class RandomUtil {

  public static double getRandom(final Random prng, final double min,
      final double max) {
    return prng.nextDouble()*(max-min)+min;
  }

  public static void shuffle(final int[] arr, final Random prng) {
    for(int i = 0, len = arr.length; i < len; i++) {
      final int rnd = prng.nextInt(len);
      final int tmp = arr[i];
      arr[i] = arr[rnd];
      arr[rnd] = tmp;
    }
  }

  public static int[] sequence(final int len, final Random prng) {
    final int[] arr = new int[len];
    for(int i = 1; i < len; i++) 
      arr[i] = i;
    shuffle(arr, prng);
    return arr;
  }

}

