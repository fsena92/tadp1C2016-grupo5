package scala

object Main {
  def main(args: Array[String]): Unit = {
   
    val lista = List(1,2,3,4,5)
    val otra = lista.takeWhile { n => n < 3 }
    val nueva = lista.filterNot { n => otra.contains(n) }
    
    println(lista)
    println(otra) 
    println(nueva)
    
    
    
  }   
  
}