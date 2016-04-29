class Object

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

  def obtener_simbolos
    [self]
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

  def obtener_simbolos
    otros_matchers = self.matchers.map {|m| m.obtener_simbolos}
    self.un_matcher.obtener_simbolos + otros_matchers
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

  def obtener_simbolos
    otros_matchers = self.matchers.map {|m| m.obtener_simbolos}
    self.un_matcher.obtener_simbolos + otros_matchers
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

  def obtener_simbolos
    self.matcher.obtener_simbolos
  end

end


class MatcherVal < Matcher

  def initialize(un_valor)
    @valor = un_valor
  end

  def call(un_valor)
    @valor == un_valor
  end

  def obtener_simbolos
    @valor.class == Symbol ? [@valor] : []
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

  def obtener_simbolos
    self.tipo.class == Symbol ? [self.tipo] : []
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

  def obtener_simbolos
    self.metodos.select {|metodo| metodo.class == Symbol}
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
      valor.class == Symbol || es_matcher(valor) ? valor.call(otro_valor) : val(valor).call(otro_valor)
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

  def obtener_simbolos
    @una_lista.map {|matcher| matcher.obtener_simbolos}
  end

end

class Pattern
  attr_accessor :diccionario, :objeto_matcheable, :list_bindeables

  def initialize
    self.diccionario = {}
    self.list_bindeables = []
  end

  def with(*matchers, &bloque)
    if match(matchers)
      self.instance_eval &bloque
      bindear(matchers)
    end
  end

  def other_wise(&bloque)
    self.instance_eval &bloque
  end

  def bindear(matchers)
    matchers.each do |matcher|
      self.list_bindeables += matcher.obtener_simbolos
    end


  end

  def method_missing(sym, *args)
    self.diccionario[sym] = ''
  end

  def match(matchers)
    matchers.all? {|un_matcher| un_matcher.call(self.objeto_matcheable)}
  end
end

p = Pattern.new

p.objeto_matcheable = 'hola'
p.with(type(String), :a_string) { a_string.length }
puts p.list_bindeables
puts p.diccionario


#p.objeto_matcheable = [1,2, Object.new]
#p.with(list([duck(:+).and(type(Fixnum), :x), :y.or(val(4)), duck(:+).not])) { x + y }
#puts p.list_bindeables
#puts p.diccionario
#p.objeto_matcheable = [1,2]
#puts p.with(:a, :b) {a + b}
#puts p.diccionario

#p.objeto_matcheable = 25
#p.with(type(Integer), :size) { size }
#puts p.list_bindeables
#puts p.diccionario