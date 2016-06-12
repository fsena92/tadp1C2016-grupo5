package scala

object Main {
  def main(args: Array[String]): Unit = {
   
    
    val atila = new Heroe(1,34,33,33)
    val aquiles = new Heroe(9,2,3,4)
    val spiderman = new Heroe(10,35,60,40)
    println(spiderman.equipar(CascoVikingo).stats)
    println(spiderman.stats)
    
   // println(atila.stats)
   
    //println(aquiles.equipar(CascoVikingo).stats)
    

    
       
  }   
  
}