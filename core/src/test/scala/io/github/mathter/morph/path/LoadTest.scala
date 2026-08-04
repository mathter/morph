package io.github.mathter.morph.path

import scala.util.Random

object LoadTest {
  def main(args: Array[String]): Unit = {
    val names: List[String] = Range(0, 10000, 1).map(i => Random.nextInt().toString).toList
    val s = Range(0, 1_000_000, 1)
    val start = System.currentTimeMillis()
    for (i <- s) {
      val p = Path(names(Random.nextInt(names.length)))
    }

    println((System.currentTimeMillis() - start) / 1000.0)
  }
}
