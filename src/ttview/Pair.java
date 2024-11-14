package ttview;

/**
 * A generic class representing an immutable pair of values.
 * Each value can be of a different type, specified by type parameters T and U.
 *
 * @param <T> the type of the first element in the pair
 * @param <U> the type of the second element in the pair
 */
public class Pair<T, U> {

  private final T first;
  private final U second;

  /**
   * Constructs a new Pair with the specified elements.
   *
   * @param first the first element of the pair
   * @param second the second element of the pair
   */
  public Pair(T first, U second) {
    this.first = first;
    this.second = second;
  }

  /**
   * Returns the first element of the pair.
   *
   * @return the first element
   */
  public T getFirst() { return first; }

  /**
   * Returns the second element of the pair.
   *
   * @return the second element
   */
  public U getSecond() { return second; }
}
