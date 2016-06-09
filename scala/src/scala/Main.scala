package scala
import scala.collection.mutable.Set

object Main {
  def main(args: Array[String]): Unit = {
    
    val atila = Heroe(1,2,3,4)
    atila.asignarTrabajo(Mago)
    
    println(atila.asignarTrabajo(Mago).job)
 
    val unSet = Set(Mago,Mago,2,3)
    println(unSet)
    
    
    
    
  }
}