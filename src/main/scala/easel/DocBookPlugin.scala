package easel
import sbt._

trait DocBookPlugin extends Project {
  lazy val hello = task { log.info("Hello World!"); None }
}

