package scala

trait Recompensa
case class GanarOroParaElPozoComun(val oro: Double) extends Recompensa
case class EncontrarUnItem(val item: Item) extends Recompensa
case class IncrementarStats(val condicion: Heroe => Boolean, statsRecompensa: StatsRecompensa) extends Recompensa
case class EncontrarNuevoMiembro(val heroe: Heroe) extends Recompensa

class StatsRecompensa(val HP: Double = 0, val fuerza: Double = 0, val velocidad: Double = 0, val inteligencia: Double = 0)

class Mision(val tareas: List[Tarea], val recompensa: Recompensa)