package scala

trait Recompensa {
  def cobrar(equipo: Equipo): Equipo
}

case class GanarOroParaElPozoComun(val oro: Double) extends Recompensa {
  def cobrar(equipo: Equipo) = equipo.incrementarPozo(oro) 
}

case class EncontrarUnItem(val item: Item) extends Recompensa {
  def cobrar(equipo: Equipo) = equipo.obtenerItem(item)
}

case class IncrementarStats(val condicion: Heroe => Boolean, recompensa: StatsRecompensa) extends Recompensa {
  def cobrar(equipo: Equipo): Equipo = {
    equipo.incrementarStatsMiembros(equipo.heroes.filter(h => condicion(h)), recompensa)
  }
}

case class EncontrarNuevoMiembro(val heroe: Heroe) extends Recompensa {  
  def cobrar(equipo: Equipo) = equipo.agregarMiembro(heroe)
}

class StatsRecompensa(val HP: Double = 0, val fuerza: Double = 0, val velocidad: Double = 0, val inteligencia: Double = 0)

class Mision(val tareas: List[Tarea], val recompensa: Recompensa)