package scala

object Main {
  def main(args: Array[String]): Unit = {
   
    
    val atila = new Heroe(1,2,10,4)
    val aquiles = new Heroe(9,2,3,4)
    
    println(aquiles.stats())
    
    var lista = List(aquiles, atila)
    lista.map(h => h.agregarItem(CascoVikingo))
    
    println(aquiles.stats())
    
    
    
  }
}