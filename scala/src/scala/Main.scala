package scala

object Main {
  def main(args: Array[String]): Unit = {
   
    
   // def pepe(equipo: Equipo): Option[Heroe => Double] = {
      
//      for {lider <- equipo.lider 
//       if(lider.job.isDefined) {
//         trabajo <- lider.job
//       }
//       else lider
//      }
//      yield {
//        if(trabajo == Guerrero) h => 20
//        else h =>10
//      }
//    }
    
    
    val a:  Option[Int] = Some(4)
    val b: Option[Option[Int]] = Some(a)
    
    println(for {x <- b
         y <- x
    if(x != None)
    }
    yield y)
   
    
    

    
//    val spiderman = new Heroe(10, 35, 60, 40)
//
//    val wolverine = new Heroe(1000, 60, 50, 20)
//    
//    val unEquipo = new Equipo("equipo", List(spiderman, wolverine))
//    println(pepe(unEquipo))
   // println(PelearContraMonstruo.facilidadPara(unEquipo).get(spiderman))




  }
  
}
    
    
//    if(equipo.lider.isDefined)
//      equipo.lider.get.job match {
//        case Some(Guerrero) => Some(h => 20)
//        case _ => Some(h => 10)
//    }
//    else Some(h => 10)*/
 