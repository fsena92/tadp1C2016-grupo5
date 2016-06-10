package scala

class Equipo(val nombre: String, var pozoComun: Double, var heroes: List[Heroe] = Nil, val misiones: List[Mision]) {
  
  def obtenerMiembro(unMiembro: Heroe) {
    heroes = unMiembro :: heroes 
  }

  def reemplazarMiembro(unMiembro: Heroe, nuevoMiembro: Heroe) {
    heroes = heroes.filter(h => h != unMiembro)
    obtenerMiembro(nuevoMiembro)
  }
  
  def lider = {
    val lider =  mejorHeroeSegun((h:Heroe) => h.job.statPrincipal)
    if (lider.size == 1) lider.head 
    else null
  }
  
  def mejorHeroeSegun(cuantificador:Heroe => Double) = {
    val maximo = heroes.map(h => cuantificador(h)).max
    heroes.filter(h => cuantificador(h) == maximo) 
  }
  
  def obtenerItem(item: Item) = {
    val maximoHeroe = mejorHeroeSegun((h:Heroe) => {
      val otroHeroe = h.agregarItem(item)
      otroHeroe.job.statPrincipal - h.job.statPrincipal
    })
    
    if(maximoHeroe.head != null && maximoHeroe.head.agregarItem(item).job.statPrincipal - maximoHeroe.head.job.statPrincipal > 0)
      reemplazarMiembro(maximoHeroe.head, maximoHeroe.head.agregarItem(item))
    else 
      pozoComun = pozoComun + item.valor
  }
  

  
  
  
}