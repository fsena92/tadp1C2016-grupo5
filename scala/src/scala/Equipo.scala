package scala

class Equipo(val nombre: String, var heroes: List[Heroe] = Nil, var pozoComun: Double = 0, 
             val misiones: List[Mision] = Nil) {
  
  def agregarMiembro(unMiembro: Heroe) {
    heroes = unMiembro :: heroes 
  }

  def reemplazarMiembro(unMiembro: Heroe, nuevoMiembro: Heroe) {
    heroes = heroes.filterNot(h => h equals unMiembro)
    agregarMiembro(nuevoMiembro)
  }
  
  def lider: Option[Heroe] = {    
    mejorHeroeSegun(heroe => heroe.job match {
      case None => 0
      case _ => heroe.job.get.statPrincipal
    })
  }
 
  def mejorHeroeSegun(cuantificador: Heroe => Double): Option[Heroe] = {
    val maximo = heroes.map(h => cuantificador(h)).max
    val heroe = heroes.filter(h => cuantificador(h) == maximo)
    if (heroe.size > 1) None
    else Some(heroe.head)
  }
  
  def incrementarPozo(item: Item) = pozoComun = pozoComun + item.valor 
  
  def obtenerItem(item: Item) = {
    val maximoHeroe = mejorHeroeSegun(heroe => {
      if(heroe.job.isDefined)
        heroe.equipar(item).job.get.statPrincipal - heroe.job.get.statPrincipal
      else 0
    })  
    if(maximoHeroe.isDefined) reemplazarMiembro(maximoHeroe.get, maximoHeroe.get.equipar(item))
    else incrementarPozo(item)
  }
  
}