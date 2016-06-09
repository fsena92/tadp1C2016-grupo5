package scala
import scala.collection.mutable.Set

object Main {
  def main(args: Array[String]): Unit = {
    
    val equipo = new Equipo("El Escuadron Suicida",10)
    val atila = new Heroe(1,2,10,4)
    val aquiles = new Heroe(9,2,3,4)
    
    equipo.obtenerMiembro(atila.asignarTrabajo(Guerrero))
    equipo.obtenerMiembro(aquiles.asignarTrabajo(Mago))
    println(aquiles.asignarTrabajo(Mago).job)
    println(equipo.heroes)
    println(equipo.lider)
    
    
    
    
    
  }
}