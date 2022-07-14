import wvlet.airframe._

case class X(name: String)

class App1(x: X) {
  // Do something with x, y, and z

  def printName = {
    println(s"x ${x}")
  }
}

object AirFrame extends App {

  def run() = {
    val design: Design =
      newDesign
        .bind[X]
        .toInstance(X("xx")) // Bind type X to a concrete instance

    design.build[App1] { app =>
      // Do something with App
      app.printName
    }
  }
  run()
}
