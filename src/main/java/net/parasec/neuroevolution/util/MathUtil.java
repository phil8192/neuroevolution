package net.parasec.neuroevolution.util;

import java.util.Random;

/**
 * utility functions.
 */
public final class MathUtil {

  /**
   * fast inverse square root.
   * originally from quake 3:
   * http://en.wikipedia.org/wiki/Fast_inverse_square_root
   * 
   * works by making a clever guess as to the starting point
   * for newton's method. 1 pass is a good approximation.
   **/
  public static double invSqrt(double x) {
    final double xhalf = 0.5d*x;
    long i = Double.doubleToLongBits(x);
    i = 0x5fe6eb50c7b537a9L - (i >> 1);
    x = Double.longBitsToDouble(i);
    x *= (1.5d - xhalf*x*x); // pass 1
    x *= (1.5d - xhalf*x*x); // pass 2
    x *= (1.5d - xhalf*x*x); // pass 3               
    x *= (1.5d - xhalf*x*x); // pass 4
    return x;
  }

  public static double fastSqrt(final double x) {
    return x*invSqrt(x);
  }

  //
  // note: sigmoid(double) self time = 42.61%.
  // sigmoid calculation is the main hotspot; this is due to the expensive
  // exp function. perhaps the accuracy is not necessary. take a look at
  // http://code.google.com/p/fastapprox/ (approximate and vectorised versions 
  // of functions commonly used in machine learning).
  //
  // this, from: seems good.
  // martin.ankerl.com/2007/02/11/optimized-exponential-functions-for-java/
  //
  //
  //
  private static double fastExp1(final double x) {
    final long tmp = (long) (1512775 * x + 1072632447);
    return Double.longBitsToDouble(tmp << 32);
  }

  //----  standard transfer functions ----//
  public static double sigmoid(final double x) {
    return 1/(1+Math.exp(-x));
  }

  public static double fastSigmoid(final double x) {
    return 1/(1+fastExp1(-x));
  }

  public static double tanh(final double x) {
    return Math.tanh(x);
  }
  //---- ----//

  public static double sigmoidDerivative(final double sOutput) {
    return sOutput*(1-sOutput);
  }

  public static double tanhDerivative(final double thOutput) {
    return 1-(thOutput*thOutput);
  }

  public static double getRandom(final Random prng, final double min, 
      final double max) {
    return prng.nextDouble()*(max-min)+min;
  }

}

