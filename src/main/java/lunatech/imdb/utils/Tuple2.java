package lunatech.imdb.utils;

/** Tuple of 2 generic elements
 */
public class Tuple2<A, B> {
  
  public final A _1;
  public final B _2;

  private Tuple2(A a, B b) {
    this._1 = a;
    this._2 = b;
  }

  public static <A, B> Tuple2<A, B> of(A a, B b) {
    return new Tuple2<>(a, b);
  }
}
