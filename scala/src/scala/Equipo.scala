package scala

import scala.collection.mutable.ListBuffer

class Equipo(val nombre: String, val pozoComun: Double, val heroes: ListBuffer[Heroe] = ListBuffer()) {
  
  def obtenerMiembro(unMiembro: Heroe) = heroes += unMiembro

  def reemplazarMiembro(unMiembro: Heroe, nuevoMiembro: Heroe) = {
    heroes -= unMiembro
    obtenerMiembro(nuevoMiembro)
  }
  
  def lider = {
    val maximo = heroes.map(h => h.job.statPrincipal).max
    val lider = heroes.filter(h => h.job.statPrincipal == maximo)
    if (lider.size == 1) lider.head 
    else null
  }
  
  
  
  
 
  
  
  
  
}