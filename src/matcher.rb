class Object

  attr_accessor :un_objeto, :bloqueWith

  def val(un_valor)
    MatcherVal.new(un_valor)
  end

  def type(un_tipo)
    MatcherType.new(un_tipo)
  end

  def duck(*metodos)
    MatcherDuckTyping.new(*metodos)
  end

  def list(una_lista, *condicion)
    MatcherList.new(una_lista, *condicion)
  end

  def matches?(un_objeto,&bloque)
    self.un_objeto = un_objeto
    self.bloqueWith = bloque
    self.instance_eval(&bloque)
  end

  def method_missing(sym,*arg)
    if sym.to_s == 'with'
      p = Pattern.new
      p.objeto_matcheable = un_objeto
      un_proc = proc {a+b}
      arg.pop
      puts p.with(arg) &un_proc
    end
 end

end


module Combinator

  def and(*matchers)
    MatcherAndCombinator.new(self, *matchers)
  end

  def or(*matchers)
    MatcherOrCombinator.new(self, *matchers)
  end

  def not
    MatcherNotCombinator.new(self)
  end
end

class Matcher
  include Combinator
end


class Symbol
  include Combinator

  def call(valor)
    true
  end


  def bindear(un_objeto, diccionario)
    diccionario[self] = un_objeto
  end


end

class MatcherAndCombinator < Matcher
  attr_accessor :un_matcher, :matchers

  def initialize(un_matcher, *matchers)
    self.un_matcher = un_matcher
    self.matchers = matchers
  end

  def call(un_objeto)
    self.un_matcher.call(un_objeto) && self.matchers.all? {|otro_matcher| otro_matcher.call(un_objeto)}
  end


  def bindear(un_objeto, diccionario)
    un_matcher.bindear(un_objeto, diccionario)
    matchers.each do |matcher|
      matcher.bindear(un_objeto, diccionario)
    end
  end

end


class MatcherOrCombinator < Matcher
  attr_accessor :un_matcher, :matchers

  def initialize(un_matcher, *matchers)
    self.un_matcher = un_matcher
    self.matchers = matchers
  end

  def call(un_objeto)
    self.un_matcher.call(un_objeto) || self.matchers.any? {|otro_matcher| otro_matcher.call(un_objeto)}
  end


  def bindear(un_objeto, diccionario)
    un_matcher.bindear(un_objeto, diccionario)
    matchers.each do |matcher|
      matcher.bindear(un_objeto, diccionario)
    end
  end

end


class MatcherNotCombinator < Matcher
  attr_accessor :matcher

  def initialize(matcher)
    self.matcher = matcher
  end

  def call(un_objeto)
    !self.matcher.call(un_objeto)
  end


  def bindear(un_objeto, diccionario)
    matcher.bindear(un_objeto, diccionario)
  end

end


class MatcherVal < Matcher

  def initialize(un_valor)
    @valor = un_valor
  end

  def call(un_valor)
    @valor == un_valor
  end


  def bindear(un_objeto, diccionario)
    #nada
  end

end


class MatcherType < Matcher

  attr_accessor :tipo

  def initialize(un_tipo)
    self.tipo = un_tipo
  end

  def call(un_objeto)
    un_objeto.is_a? self.tipo
  end


  def bindear(un_objeto, diccionario)
    #nada
  end

end


class MatcherDuckTyping < Matcher

  attr_accessor :metodos

  def initialize(*metodos)
    self.metodos = metodos
  end

  def call(un_objeto)
    self.metodos.all? {|un_metodo| un_objeto.methods.include?(un_metodo)}
  end


  def bindear(un_objeto, diccionario)
    #nada
  end

end


class MatcherList < Matcher

  def initialize(una_lista, *condicion)
    @una_lista = una_lista
    @condicion = condicion
  end

  private def es_matcher(un_objeto)
    un_objeto.class.ancestors.include? Matcher
  end

  private def comparar_listas(hash)
    hash.all? do |valor, otro_valor|
      valor.is_a?(Symbol) || es_matcher(valor) ? valor.call(otro_valor) : val(valor).call(otro_valor)
    end
  end

  def call(otra_lista)
    if @condicion == [true] || @condicion == []
      hash = Hash[@una_lista.zip(otra_lista)]
      @una_lista.size == otra_lista.size ?  comparar_listas(hash) : false
    else
      comparar_listas(Hash[@una_lista.zip(otra_lista)])
    end
  end


  def bindear(un_objeto, diccionario)
    i = 0
    @una_lista.each do |elem|
      if elem.methods.include?(:bindear)
        elem.bindear(un_objeto[i], diccionario)
      end
        i+= 1
      end
    end

end

class Pattern
  attr_accessor :diccionario, :objeto_matcheable

  def initialize
    self.diccionario = {}
  end


  def with(*matchers, &bloque)
    if match(matchers)
      bindear(matchers)
      self.instance_eval &bloque
    end
  end

  def other_wise(&bloque)
    self.instance_eval &bloque
  end

  def bindear(matchers)
    matchers.each do |matcher|
      matcher.bindear(objeto_matcheable,diccionario)
    end


  end

  def method_missing(sym, *args)
    self.diccionario[sym]
  end

  def match(matchers)
    matchers.all? {|un_matcher| un_matcher.call(self.objeto_matcheable)}
  end
end




#p = Pattern.new

#p.objeto_matcheable = 'hola'
#p.with(type(String), :a_string) { a_string.length }
#puts p.list_bindeables
#puts p.diccionario


#p.objeto_matcheable = [1,2, Object.new]
#p.with(list([duck(:+).and(type(Fixnum), :x), :y.or(val(4)), duck(:+).not])) { x + y }
#puts p.list_bindeables
#puts p.diccionario
#p.objeto_matcheable = [1,2]
#puts p.with(:a, :b) {a + b}
#puts p.diccionario

#p.objeto_matcheable = [2,4,Object.new]
#p.with(type(Integer), :size, :a) { size }
#puts p.list_bindeables


#puts (val(2).and(duck(:+),:b)).obtener_simbolos
#puts (type(String).and(:a_string)).obtener_simbolos
#puts (list([duck(:+).and(type(Fixnum), :x), :y.or(val(4)), duck(:+).not])).obtener_simbolos
#puts (duck(:hola)).obtener_simbolos

#puts p.with(list([duck(:+).and(type(Fixnum), :x), :y.or(val(4)), duck(:+).not])) { x + y }

#puts p.with(:y.and(list(val(2),:b))) {y }

x = [1, 2, 3]
matches?(x) do
   puts with(list([:a, val(2), duck(:+)]) ) {a+2}
   #with(list([1, 2, 3])) { 'acá no llego' }
  #otherwise { 'acá no llego' }
end

#puts p.diccionario

