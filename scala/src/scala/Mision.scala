package scala

trait Recompensa {
  type TiposRecompensa = Equipo => Equipo
  def cobrar: TiposRecompensa
}

case class GanarOroParaElPozoComun(val oro: Double) extends Recompensa {
  def cobrar = _ incrementarPozo oro 
}

case class EncontrarUnItem(val item: Item) extends Recompensa {
  def cobrar = _ obtenerItem item
}

case class IncrementarStats(val condicion: Heroe => Boolean, recompensa: StatsRecompensa) extends Recompensa {
  def cobrar = _ incrementarStatsMiembros(condicion, recompensa)
}

case class EncontrarNuevoMiembro(val heroe: Heroe) extends Recompensa {  
  def cobrar = _ agregarMiembro heroe
}

class StatsRecompensa(val HP: Double = 0, val fuerza: Double = 0, val velocidad: Double = 0, val inteligencia: Double = 0)

class Mision(val tareas: List[Tarea], val recompensa: Recompensa)