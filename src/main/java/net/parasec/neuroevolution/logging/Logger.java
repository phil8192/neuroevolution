package net.parasec.neuroevolution.logging;

/**
 * basic logging.
 */
public final class Logger {

  private final String className;


  public Logger(final Class c) {
    className = c.getSimpleName();
  }

  public void info(final Object o) {
    System.out.println("[" +System.currentTimeMillis() + " " + className + " "
        + Thread.currentThread().getName() + "] " + o);
  }

  public void error(final Object o, final Throwable t) {
    System.err.print("[" +System.currentTimeMillis() + " " + className + " " +
        Thread.currentThread().getName() + "] " + o + " " + t.getMessage());
  }

  public void debug(final Object o) {
    info(o); 
  }
  
  public static Logger getLogger(final Class c) {
    return new Logger(c);
  }

}

