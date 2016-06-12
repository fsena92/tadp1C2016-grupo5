package scala

class Equipo(val nombre: String, var pozoComun: Double, var heroes: List[Heroe] = Nil, val misiones: List[Mision]) {
  
  def agregarMiembro(unMiembro: Heroe) {
    heroes = unMiembro :: heroes 
  }

  def reemplazarMiembro(unMiembro: Heroe, nuevoMiembro: Heroe) {
    heroes = heroes.filter(h => h != unMiembro)
    agregarMiembro(nuevoMiembro)
  }
  
  def lider: Option[Heroe] = mejorHeroeSegun((heroe:Heroe) => heroe.job.get.statPrincipal)
  
  def mejorHeroeSegun(cuantificador:Heroe => Double): Option[Heroe] = {
    val maximo = heroes.map(h => cuantificador(h)).max
    val heroe = heroes.filter(h => cuantificador(h) == maximo)
    if (heroe.size > 1) None
    else Some(heroe.head)
  }
  
  def obtenerItem(item: Item) = {
    val maximoHeroe = mejorHeroeSegun((heroe: Heroe) => {
      if(heroe.job.isDefined){
        val otroHeroe = heroe.equipar(item)
        otroHeroe.job.get.statPrincipal - heroe.job.get.statPrincipal
      }
      else 0
    })
        
    if(maximoHeroe.isDefined) reemplazarMiembro(maximoHeroe.get, maximoHeroe.get.equipar(item))
    else pozoComun = pozoComun + item.valor
  }
  

}